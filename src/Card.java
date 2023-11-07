/**
 * @author: Salama Noureldean 101154365
 * @editor: Pietro Adamvoski 101238885
 * @date: 10/22/2023
 * @version: 1.00
 */

public class Card {
     enum Value{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, WILD, WILD_DRAW_TWO};
    public enum Color{RED, GREEN, BLUE, YELLOW, BLANK};

    private final Value VALUE;
    private Color color;

    public Card(Value value, Color color) {
        this.VALUE = value;
        this.color = color;
    }

    //Returns the randomized color of the card.
    public Color getColor(){
        return color;
    }

    //Returns the randomized value of the card.
    public Value getVALUE(){
        return VALUE;
    }

    //Sets color variable
    public void setColor(Color color) {
        this.color = color;
    }

    //Returns a string representation of the card. Returns value and color if the value of the card is not a wildcard.
    //if value of the card is a wildcard then it returns only the value of the card since color is irrelevant.
    public String stringCard(){
        if(VALUE == Value.WILD || VALUE == Value.WILD_DRAW_TWO){
            return "A " + VALUE;
        }
        else{
            return "A " + color + " " + VALUE;
        }
    }
}