import java.util.Random;

public class Card {
    public enum Value{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, WILD, WILD_DRAW_TWO};
    public enum Color{RED, GREEN, BLUE, YELLOW};

    private final Value value;
    private Color color;

    //Constructs a random card from the initialized Value and Color enums.
    public Card() {
        Random random = new Random();
        this.value = Value.values()[random.nextInt(Value.values().length)];
        // If the card is not a wildcard, give it a color.
        if(value != Value.WILD && value != Value.WILD_DRAW_TWO){
            this.color = Color.values()[random.nextInt(Color.values().length)];
        }

    }

    public Card(Card.Value value, Card.Color color){
        this.value = value;
        // If card is not a wildcard, give it a color.
        if(value != Value.WILD && value != Value.WILD_DRAW_TWO){
            this.color = color;
        }
        else{
            this.color = null;
        }
    }

    //Returns the randomized color of the card.
    public Color getColor(){
        return color;
    }

    //Returns the randomized value of the card.
    public Value getValue(){
        return value;
    }

    //Sets color variable
    public void setColor(Color color) {
        this.color = color;
    }

    //Returns a string representation of the card. Returns value and color if the value of the card is not a wildcard.
    //if value of the card is a wildcard then it returns only the value of the card since color is irrelevant.
    public String stringCard(){
        if(value == Value.WILD){
            return "A" + value;
        }
        else{
            return "A" + color + value;
        }
    }
}
