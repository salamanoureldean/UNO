import javax.swing.*;

/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/14/2023
 * @version: 1.00
 */
public class Card {
    enum Value{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, WILD, WILDDRAWTWO};
    public enum Color{RED, GREEN, BLUE, YELLOW, BLANK};

    private final Value VALUE;
    private Color color;
    private JButton cardButton;


    /**
     * Constructs a new Card with the specified value and color.
     *
     * @param value The value of the card.
     * @param color The color of the card.
     */
    public Card(Value value, Color color) {
        this.VALUE = value;
        this.color = color;

        this.cardButton = new JButton(getFileNameForCard(value, color));
        String filePath = "small\\" + getFileNameForCard(value, color) + ".png";
        ImageIcon icon = new ImageIcon(filePath);
        this.cardButton.setIcon(icon);
        this.cardButton.setEnabled(false);
        this.cardButton.setVisible(false);
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
    public Value getVALUE(){
        return VALUE;
    }

    /**
     * Checks if the card is a Wild Draw Two card.
     *
     * @return True if the card is a Wild Draw Two card, false otherwise.
     */
    public boolean isWildDrawTwo() {
        return this.VALUE == Value.WILDDRAWTWO;
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
    private String getFileNameForCard(Value value, Color color) {
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
        if(VALUE == Value.WILD || VALUE == Value.WILDDRAWTWO){
            return "A " + VALUE;
        }
        else{
            return "A " + color + " " + VALUE;
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
