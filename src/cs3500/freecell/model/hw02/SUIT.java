package cs3500.freecell.model.hw02;

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

