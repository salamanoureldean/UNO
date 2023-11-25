import javax.swing.*;

/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/14/2023
 * @version: 1.00
 */
public class Card {
    enum Value{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, DRAWONE, WILD, WILDDRAWTWO, FLIP, DRAWFIVE, SKIPALL, WILDDRAWCOLOR, WILDDARK};
    public enum Color{RED, GREEN, BLUE, YELLOW, BLANK, PINK, PURPLE, TEAL, ORANGE};

    private Value value;
    private Color color;
    private JButton cardButton;
    private boolean isLightSide;


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
    }

    public void flipCard(boolean gameIsLightSide){
        if(this.isLightSide != gameIsLightSide){
            this.isLightSide = !this.isLightSide;

            //update card appearance here.
            switchDarkSide();
            String filePath = "dark\\" + getFileNameForCard(this.value, this.color) + ".png";
            ImageIcon icon = new ImageIcon(filePath);
            this.cardButton.setIcon(icon);
            //my changes-----------------------------------------
            this.cardButton.setText(stringCard());
            this.cardButton.revalidate();
            this.cardButton.repaint();

            this.cardButton.setEnabled(false);
            this.cardButton.setVisible(false);


        }
        else{
            switchLightSide();
            String filePath = "small\\" + getFileNameForCard(this.value, this.color) + ".png";
            ImageIcon icon = new ImageIcon(filePath);
            this.cardButton.setIcon(icon);
            //my changes----------------------------------------------------
            this.cardButton.setText(stringCard());
            this.cardButton.revalidate();
            this.cardButton.repaint();

            this.cardButton.setEnabled(false);
            this.cardButton.setVisible(false);
        }
    }

    public void switchDarkSide(){
        if(value == Value.WILD){
            this.value = Value.WILDDARK;
        }
        else if(value == Value.WILDDRAWTWO){
            this.value = Value.WILDDRAWCOLOR;
        }
        else if (value == Value.SKIP){
            this.value = Value.SKIPALL;
            darkLightColorSwitch();
        }
        else if(value == Value.DRAWONE){
            this.value = Value.DRAWFIVE;
            darkLightColorSwitch();
        }
        else {
            darkLightColorSwitch();
        }
    }
    public void switchLightSide(){
        if(value == Value.WILDDARK){
            this.value = Value.WILD;
        }
        else if(value == Value.WILDDRAWCOLOR){
            this.value = Value.WILDDRAWTWO;
        }
        else if (value == Value.SKIPALL){
            this.value = Value.SKIP;
            darkLightColorSwitch();
        }
        else if(value == Value.DRAWFIVE){
            this.value = Value.DRAWONE;
            darkLightColorSwitch();
        }
        else {
            darkLightColorSwitch();
        }
    }

    public void darkLightColorSwitch(){
        if(this.isLightSide == false){
            if(this.color == Color.RED){
                this.color = Color.ORANGE;
            }
            else if(this.color == Color.YELLOW){
                this.color = Color.TEAL;
            }
            else if(this.color == Color.GREEN){
                this.color = Color.PINK;
            }
            else if(this.color == Color.BLUE){
                this.color = Color.PURPLE;
            }
        }
        else{
            if(this.color == Color.ORANGE){
                this.color = Color.RED;
            }
            else if(this.color == Color.TEAL){
                this.color = Color.YELLOW;
            }
            else if(this.color == Color.PINK){
                this.color = Color.GREEN;
            }
            else if(this.color == Color.PURPLE){
                this.color = Color.BLUE;
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
}