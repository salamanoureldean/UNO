import javax.swing.*;

/**
 * @author: Salama Noureldean 101154365
 * @editor: Pietro Adamvoski 101238885
 * @date: 10/22/2023
 * @version: 1.00
 */
public class Card {
    enum Value{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, WILD, WILDDRAWTWO};
    public enum Color{RED, GREEN, BLUE, YELLOW, BLANK};

    private final Value VALUE;
    private Color color;
    private JButton cardButton;


    public Card(Value value, Color color) {
        this.VALUE = value;
        this.color = color;

        this.cardButton = new JButton(getFileNameForCard(value, color));
        String filePath = "C:\\Users\\salam\\OneDrive\\Pictures\\PNGs\\small\\" + getFileNameForCard(value, color) + ".png";
        ImageIcon icon = new ImageIcon(filePath);
        this.cardButton.setIcon(icon);
    }

    //Returns the color of the card.
    public Color getColor(){
        return color;
    }

    //Returns the value of the card.
    public Value getVALUE(){
        return VALUE;
    }

    //Sets color variable
    public void setColor(Color color) {
        this.color = color;
    }

    private String getFileNameForCard(Value value, Color color) {
        // Logic to map card color and value to a corresponding PNG file name
        // This can be customized based on your actual file naming convention
        return color.toString().toLowerCase() + "_" + value.toString().toLowerCase();
    }

    //Returns a string representation of the card. Returns value and color if the value of the card is not a wildcard.
    //if value of the card is a wildcard then it returns only the value of the card since color is irrelevant.
    public String stringCard(){
        if(VALUE == Value.WILD || VALUE == Value.WILDDRAWTWO){
            return "A " + VALUE;
        }
        else{
            return "A " + color + " " + VALUE;
        }
    }

    public JButton getCardButton() {
        return cardButton;
    }
}