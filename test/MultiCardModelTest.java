import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the multi card move model feature of a freecell game.
 */
public class MultiCardModelTest {

  FreecellModel<Card> game;
  FreecellView view;

  @Before
  public void initMultiCardModel() {
    game = FreecellModelCreator.create(GameType.MULTIMOVE);
    view = new FreecellTextView(game);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameHasNotStarted() {
    game.move(PileType.OPEN, 4, 0, PileType.CASCADE, 4);
  }

  @Test
  public void testMove2To6Cards6CascadesNoneEmptyShouldWork_MultimoveModel() {
    game.startGame(game.getDeck(), 12, 4, false);
    game.move(PileType.CASCADE, 10, 2, PileType.CASCADE, 0);
    game.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 4, 3, PileType.FOUNDATION, 0);
    assertEquals("F1: A♠, 2♠\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: K♠\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, K♣, Q♥, J♦, 10♠, 9♦, 8♠\n"
        + "C2: 2♣, A♥, K♥, Q♦, J♠\n"
        + "C3: 3♣, 2♥, A♦, K♦, Q♠\n"
        + "C4: 4♣, 3♥, 2♦\n"
        + "C5: 5♣, 4♥, 3♦\n"
        + "C6: 6♣, 5♥, 4♦, 3♠\n"
        + "C7: 7♣, 6♥, 5♦, 4♠\n"
        + "C8: 8♣, 7♥, 6♦, 5♠\n"
        + "C9: 9♣, 8♥, 7♦, 6♠\n"
        + "C10: 10♣, 9♥, 8♦, 7♠\n"
        + "C11: J♣, 10♥\n"
        + "C12: Q♣, J♥, 10♦, 9♠", view.toString());
  }


}
