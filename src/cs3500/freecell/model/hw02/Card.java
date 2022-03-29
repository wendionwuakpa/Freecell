package cs3500.freecell.model.hw02;

/**
 * Represents a Card in a deck.
 */
public class Card {

  private final VALUE value;
  private final SUIT suit;

  /**
   * Constucts a Card.
   *
   * @param value represents the value of a Card.
   * @param suit  represents the suit of a Card.
   */
  public Card(VALUE value, SUIT suit) {
    this.value = value;
    this.suit = suit;
    if (value == null || suit == null) {
      throw new IllegalArgumentException("The Card's value and suit cannot be null.");
    }
  }

  /**
   * Represents a card's Suit.
   */
  public enum SUIT {
    CLUB,
    HEART,
    DIAMOND,
    SPADE;

    /**
     * Determines the color of a Suit.
     *
     * @param suit represents the Suit of a Card.
     * @return String of the color.
     */
    public static String getColorSuit(SUIT suit) {
      switch (suit) {
        case CLUB:
        case SPADE:
          return "black";
        case HEART:
        case DIAMOND:
          return "red";
        default:
          throw new IllegalArgumentException("Invalid Suit.");
      }
    }
  }

  /**
   * Represents a card's Value.
   */
  public enum VALUE {
    A(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    J(11),
    Q(12),
    K(13);

    int num;

    /**
     * Constructs a Card's Value.
     *
     * @param n represents the num of a Value
     */
    VALUE(int n) {
      num = n;
    }
  }

  @Override
  //returns the formatted string like: "A♦"
  public String toString() {
    return getValueChar(value) + getSuitChar(suit);
  }

  private static String getSuitChar(SUIT suit) {
    switch (suit) {
      case DIAMOND:
        return "♦";
      case HEART:
        return "♥";
      case CLUB:
        return "♣";
      case SPADE:
        return "♠";
      default:
        throw new IllegalArgumentException("Suit is null.");
    }
  }

  /**
   * Gets the suit of a Card.
   *
   * @return The Suit of a Card.
   */
  public SUIT getSuit() {
    return this.suit;
  }

  private static String getValueChar(VALUE value) {
    switch (value) {
      case A:
        return "A";
      case TWO:
        return "2";
      case THREE:
        return "3";
      case FOUR:
        return "4";
      case FIVE:
        return "5";
      case SIX:
        return "6";
      case SEVEN:
        return "7";
      case EIGHT:
        return "8";
      case NINE:
        return "9";
      case TEN:
        return "10";
      case J:
        return "J";
      case Q:
        return "Q";
      case K:
        return "K";
      default:
        throw new IllegalArgumentException("Value is null.");
    }
  }

  /**
   * Gets the value of a card.
   *
   * @return The Value of a Card.
   */
  public int getValue() {
    return this.value.num;
  }


}
