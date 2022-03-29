package cs3500.freecell.model;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiCardMoveModel;

/**
 * Constructs either a single move or multi move variation free cell.
 */
public class FreecellModelCreator {

  /**
   * Represents the type of a game.
   */
  public enum GameType {
    MULTIMOVE, SINGLEMOVE;
  }

  /**
   * Creates a new FreecellModel or MultiCardModel based on the GameType.
   */
  public static FreecellModel<Card> create(GameType type) {
    if (type == GameType.MULTIMOVE) {
      return new MultiCardMoveModel();
    }
    if (type == GameType.SINGLEMOVE) {
      return new SimpleFreecellModel();
    }

    //if not any of the gametypes provided
    return null;
  }

}
