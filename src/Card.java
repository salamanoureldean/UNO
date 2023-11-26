import javax.swing.*;

/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/14/2023
 * @version: 1.00
 */
public class Card {
    public enum Value { ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAWONE, REVERSE, SKIP, FLIP, DRAWFIVE, SKIPALL, WILD, WILDDRAWTWO, WILDDRAWCOLOR, WILDDARK; }
    public enum Color{RED, GREEN, BLUE, YELLOW, BLANK, PINK, PURPLE, TEAL, ORANGE};

    private Value value;
    private Color color;
    private JButton cardButton;
    private boolean isLightSide;
    private boolean gameIsLightSide = true;


    /**
     * Constructs a new Card with the specified value and color.
     *
     * @param value The value of the card.
     * @param color The color of the card.
     */
    public Card(Value value, Color color) {
        this.value = value;
        this.color = color;

        this.cardButton = new JButton(getFileNameForCard(value, color));
        String filePath = "small\\" + getFileNameForCard(value, color) + ".png";
        ImageIcon icon = new ImageIcon(filePath);
        this.cardButton.setIcon(icon);
        this.cardButton.setEnabled(false);
        this.cardButton.setVisible(false);
        isLightSide = false;
    }

    public void flipCard(){
        if(this.isLightSide != true){


            switchDarkSide();
            String filePath = "dark\\" + getFileNameForCard(this.value, this.color) + ".png";
            ImageIcon icon = new ImageIcon(filePath);
            this.cardButton.setIcon(icon);

            this.cardButton.setText(stringCard());
            this.cardButton.setEnabled(true);
            this.cardButton.setVisible(true);
            this.isLightSide = !this.isLightSide;

        }
        else{
            switchLightSide();
            String filePath = "small\\" + getFileNameForCard(this.value, this.color) + ".png";
            ImageIcon icon = new ImageIcon(filePath);
            this.cardButton.setIcon(icon);

            this.cardButton.setText(stringCard());
            this.cardButton.setEnabled(true);
            this.cardButton.setVisible(true);
            this.isLightSide = !this.isLightSide;
        }
    }

    public void switchDarkSide( ){
        if(value == Value.WILD){
            value = Value.WILDDARK;
        }
        else if(value == Value.WILDDRAWTWO){
            value = Value.WILDDRAWCOLOR;
        }
        else if (value == Value.SKIP){
            value = Value.SKIPALL;
            darkLightColorSwitch();
        }
        else if(value == Value.DRAWONE){
            value = Value.DRAWFIVE;
            darkLightColorSwitch();
        }
        else {
            darkLightColorSwitch();
        }
    }
    public void switchLightSide(){
        if(value.equals(Value.WILDDARK)){
            value = Value.WILD;
        }
        else if(value.equals(Value.WILDDRAWCOLOR)){
            value = Value.WILDDRAWTWO;
        }
        else if (value.equals(Value.SKIPALL)){
            value = Value.SKIP;
            darkLightColorSwitch();
        }
        else if(value.equals(Value.DRAWFIVE)){
            value = Value.DRAWONE;
            darkLightColorSwitch();
        }
        else {
            darkLightColorSwitch();
        }
    }

    public void darkLightColorSwitch(){
        if(this.isLightSide == false){
            if(color.equals(Color.RED)){
                color = Color.ORANGE;
            }
            else if(color.equals(Color.YELLOW)){
                color = Color.TEAL;
            }
            else if(color.equals(Color.GREEN)){
                color = Color.PINK;
            }
            else if(color.equals(Color.BLUE)){
                color = Color.PURPLE;
            }
        }
        else{
            if(color.equals(Color.ORANGE)){
                color = Color.RED;
            }
            else if(color.equals(Color.TEAL)){
                color = Color.YELLOW;
            }
            else if(color.equals(Color.PINK)){
                color = Color.GREEN;
            }
            else if(color.equals(Color.PURPLE)){
                color = Color.BLUE;
            }

        }
    }

    /**
     * Returns the color of the card.
     *
     * @return The color of the card.
     */
    public Color getColor(){
        return color;
    }

    /**
     * Returns the value of the card.
     *
     * @return The value of the card.
     */
    public Value getValue(){
        return value;
    }

    /**
     * Checks if the card is a Wild Draw Two card.
     *
     * @return True if the card is a Wild Draw Two card, false otherwise.
     */
    public boolean isWildDrawTwo() {
        return this.value == Value.WILDDRAWTWO;
    }

    /**
     * Sets the color of the card.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Generates the file name for the card's image based on its value and color.
     * The file name is constructed by concatenating the lowercase string representation
     * of the color, an underscore '_', and the lowercase string representation of the value.
     *
     * @param value The value of the card.
     * @param color The color of the card.
     * @return The file name for the card's image.
     */
    public String getFileNameForCard(Value value, Color color) {
        return color.toString().toLowerCase() + "_" + value.toString().toLowerCase();
    }

    /**
     * Returns a string representation of the card.
     * If the card's value is a wildcard (WILD or WILDDRAWTWO), it returns only the value.
     * If the card's value is not a wildcard, it returns a string containing the card's color and value.
     *
     * @return The string representation of the card.
     */
    public String stringCard(){
        if(value == Value.WILD || value == Value.WILDDRAWTWO){
            return "A " + value;
        }
        else{
            return "A " + color + " " + value;
        }
    }

    /**
     * Returns the JButton associated with the card.
     *
     * @return The JButton associated with the card.
     */
    public JButton getCardButton() {
        return cardButton;
    }

    public boolean isWild() {
        return this.value == Value.WILD || this.value == Value.WILDDRAWTWO || this.value == Value.WILDDARK || this.value == Value.WILDDRAWCOLOR;
    }

}