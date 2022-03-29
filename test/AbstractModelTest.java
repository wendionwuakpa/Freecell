import static org.junit.Assert.assertEquals;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiCardMoveModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the abstracted model: simple free cell and multi card moves model.
 */
public abstract class AbstractModelTest {

  FreecellModel<Card> gameMulti;
  FreecellModel<Card> gameSimple;
  FreecellView view;
  FreecellView viewMulti;

  @Before
  public void initConditions() {
    gameSimple = FreecellModelCreator.create(GameType.SINGLEMOVE);
    gameMulti = FreecellModelCreator.create(GameType.MULTIMOVE);
    view = new FreecellTextView(gameSimple);
    viewMulti = new FreecellTextView(gameMulti);
  }

  /**
   * Constructs an instance of the class under test representing the Simple free cell model.
   */
  protected abstract FreecellModel<Card> simple();

  /**
   * Constructs an instance of the class under test representing the Multicard move model.
   */
  protected abstract FreecellModel<Card> multicard();

  /**
   * Concrete class for testing SimpleFreecellModel.
   */
  public static final class SimpleFreeCellModelTest extends AbstractModelTest {

    @Override
    protected FreecellModel simple() {
      return new SimpleFreecellModel();
    }

    @Override
    protected FreecellModel multicard() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * Concrete class for testing Multicardmodel.
   */
  public static final class MultiCardModelTest extends AbstractModelTest {

    @Override
    protected FreecellModel simple() {
      return new MultiCardMoveModel();
    }

    @Override
    protected FreecellModel multicard() {
      return new MultiCardMoveModel();
    }
  }

  @Test
  public void testValid2MultiMoveOneOpenController() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C1 11 O1 C1 10 O2 C1 9 O3 C3 10 C1 C1 8 C5 q");
    FreecellController controller = new SimpleFreecellController(multicard(), in, out);
    controller.playGame(gameMulti.getDeck(), 5, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 6♣, J♣, 3♥, 8♥, K♥, 5♦, 10♦, 2♠, 7♠, Q♠\n"
        + "C2: 2♣, 7♣, Q♣, 4♥, 9♥, A♦, 6♦, J♦, 3♠, 8♠, K♠\n"
        + "C3: 3♣, 8♣, K♣, 5♥, 10♥, 2♦, 7♦, Q♦, 4♠, 9♠\n"
        + "C4: 4♣, 9♣, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♠, 10♠\n"
        + "C5: 5♣, 10♣, 2♥, 7♥, Q♥, 4♦, 9♦, A♠, 6♠, J♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: Q♠\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 6♣, J♣, 3♥, 8♥, K♥, 5♦, 10♦, 2♠, 7♠\n"
        + "C2: 2♣, 7♣, Q♣, 4♥, 9♥, A♦, 6♦, J♦, 3♠, 8♠, K♠\n"
        + "C3: 3♣, 8♣, K♣, 5♥, 10♥, 2♦, 7♦, Q♦, 4♠, 9♠\n"
        + "C4: 4♣, 9♣, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♠, 10♠\n"
        + "C5: 5♣, 10♣, 2♥, 7♥, Q♥, 4♦, 9♦, A♠, 6♠, J♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: Q♠\n"
        + "O2: 7♠\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 6♣, J♣, 3♥, 8♥, K♥, 5♦, 10♦, 2♠\n"
        + "C2: 2♣, 7♣, Q♣, 4♥, 9♥, A♦, 6♦, J♦, 3♠, 8♠, K♠\n"
        + "C3: 3♣, 8♣, K♣, 5♥, 10♥, 2♦, 7♦, Q♦, 4♠, 9♠\n"
        + "C4: 4♣, 9♣, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♠, 10♠\n"
        + "C5: 5♣, 10♣, 2♥, 7♥, Q♥, 4♦, 9♦, A♠, 6♠, J♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: Q♠\n"
        + "O2: 7♠\n"
        + "O3: 2♠\n"
        + "O4:\n"
        + "C1: A♣, 6♣, J♣, 3♥, 8♥, K♥, 5♦, 10♦\n"
        + "C2: 2♣, 7♣, Q♣, 4♥, 9♥, A♦, 6♦, J♦, 3♠, 8♠, K♠\n"
        + "C3: 3♣, 8♣, K♣, 5♥, 10♥, 2♦, 7♦, Q♦, 4♠, 9♠\n"
        + "C4: 4♣, 9♣, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♠, 10♠\n"
        + "C5: 5♣, 10♣, 2♥, 7♥, Q♥, 4♦, 9♦, A♠, 6♠, J♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: Q♠\n"
        + "O2: 7♠\n"
        + "O3: 2♠\n"
        + "O4:\n"
        + "C1: A♣, 6♣, J♣, 3♥, 8♥, K♥, 5♦, 10♦, 9♠\n"
        + "C2: 2♣, 7♣, Q♣, 4♥, 9♥, A♦, 6♦, J♦, 3♠, 8♠, K♠\n"
        + "C3: 3♣, 8♣, K♣, 5♥, 10♥, 2♦, 7♦, Q♦, 4♠\n"
        + "C4: 4♣, 9♣, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♠, 10♠\n"
        + "C5: 5♣, 10♣, 2♥, 7♥, Q♥, 4♦, 9♦, A♠, 6♠, J♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: Q♠\n"
        + "O2: 7♠\n"
        + "O3: 2♠\n"
        + "O4:\n"
        + "C1: A♣, 6♣, J♣, 3♥, 8♥, K♥, 5♦\n"
        + "C2: 2♣, 7♣, Q♣, 4♥, 9♥, A♦, 6♦, J♦, 3♠, 8♠, K♠\n"
        + "C3: 3♣, 8♣, K♣, 5♥, 10♥, 2♦, 7♦, Q♦, 4♠\n"
        + "C4: 4♣, 9♣, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♠, 10♠\n"
        + "C5: 5♣, 10♣, 2♥, 7♥, Q♥, 4♦, 9♦, A♠, 6♠, J♠, 10♦, 9♠\n"
        + "\n"
        + "Game quit prematurely.", out.toString());

  }

  @Test(expected = IllegalStateException.class)
  public void movingBeforeGameStarts() {
    gameSimple.move(PileType.CASCADE, 4, 0, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMoveThreeCards1Open1CascadeMaxMoves4() {
    gameMulti.startGame(gameMulti.getDeck(), 10, 4, false);
    gameMulti.move(PileType.CASCADE, 9, 3, PileType.CASCADE, 2);

  }

  @Test
  public void validMoveTwoCardsMoves1Open() {
    gameMulti.startGame(gameMulti.getDeck(), 8, 4, false);
    gameMulti.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    gameMulti.move(PileType.CASCADE, 3, 5, PileType.OPEN, 1);
    gameMulti.move(PileType.CASCADE, 3, 4, PileType.CASCADE, 1);
    gameMulti.move(PileType.CASCADE, 5, 5, PileType.OPEN, 2);
    gameMulti.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 5);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: K♠\n"
        + "O2: 5♠\n"
        + "O3: 7♠\n"
        + "O4:\n"
        + "C1: A♣, 9♣, 4♥, Q♥, 7♦, 2♠, 10♠\n"
        + "C2: 2♣, 10♣, 5♥, K♥, 8♦, 3♠\n"
        + "C3: 3♣, J♣, 6♥, A♦, 9♦, 4♠, Q♠\n"
        + "C4: 4♣, Q♣, 7♥, 2♦\n"
        + "C5: 5♣, K♣, 8♥, 3♦, J♦, 6♠\n"
        + "C6: 6♣, A♥, 9♥, 4♦, Q♦, J♠, 10♦\n"
        + "C7: 7♣, 2♥, 10♥, 5♦, K♦, 8♠\n"
        + "C8: 8♣, 3♥, J♥, 6♦, A♠, 9♠", viewMulti.toString());

  }

  @Test
  public void validMove3Cards3Open() {
    gameMulti.startGame(gameMulti.getDeck(), 17, 4, false);
    gameMulti.move(PileType.CASCADE, 13, 2, PileType.CASCADE, 1);
    gameMulti.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 15);
    gameMulti.move(PileType.CASCADE, 15, 2, PileType.CASCADE, 3);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 5♥, 9♦, K♠\n"
        + "C2: 2♣, 6♥\n"
        + "C3: 3♣, 7♥, J♦\n"
        + "C4: 4♣, 8♥, Q♦, J♠, 10♦, 9♠\n"
        + "C5: 5♣, 9♥, K♦\n"
        + "C6: 6♣, 10♥, A♠\n"
        + "C7: 7♣, J♥, 2♠\n"
        + "C8: 8♣, Q♥, 3♠\n"
        + "C9: 9♣, K♥, 4♠\n"
        + "C10: 10♣, A♦, 5♠\n"
        + "C11: J♣, 2♦, 6♠\n"
        + "C12: Q♣, 3♦, 7♠\n"
        + "C13: K♣, 4♦, 8♠\n"
        + "C14: A♥, 5♦\n"
        + "C15: 2♥, 6♦, 10♠\n"
        + "C16: 3♥, 7♦\n"
        + "C17: 4♥, 8♦, Q♠", viewMulti.toString());
  }

  @Test
  public void validMove4Cards4Open() {
    gameMulti.startGame(gameMulti.getDeck(), 17, 4, false);
    gameMulti.move(PileType.CASCADE, 13, 2, PileType.CASCADE, 1);
    gameMulti.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 15);
    gameMulti.move(PileType.CASCADE, 15, 2, PileType.CASCADE, 3);
    gameMulti.move(PileType.CASCADE, 3, 2, PileType.CASCADE, 0);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 5♥, 9♦, K♠, Q♦, J♠, 10♦, 9♠\n"
        + "C2: 2♣, 6♥\n"
        + "C3: 3♣, 7♥, J♦\n"
        + "C4: 4♣, 8♥\n"
        + "C5: 5♣, 9♥, K♦\n"
        + "C6: 6♣, 10♥, A♠\n"
        + "C7: 7♣, J♥, 2♠\n"
        + "C8: 8♣, Q♥, 3♠\n"
        + "C9: 9♣, K♥, 4♠\n"
        + "C10: 10♣, A♦, 5♠\n"
        + "C11: J♣, 2♦, 6♠\n"
        + "C12: Q♣, 3♦, 7♠\n"
        + "C13: K♣, 4♦, 8♠\n"
        + "C14: A♥, 5♦\n"
        + "C15: 2♥, 6♦, 10♠\n"
        + "C16: 3♥, 7♦\n"
        + "C17: 4♥, 8♦, Q♠", viewMulti.toString());
  }

  @Test
  public void move3Cards1EmptyOpen1EmptyCascade() {
    gameMulti.startGame(gameMulti.getDeck(), 17, 4, false);
    gameMulti.move(PileType.CASCADE, 13, 2, PileType.CASCADE, 1);
    gameMulti.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 15);
    gameMulti.move(PileType.CASCADE, 16, 2, PileType.OPEN, 0);
    gameMulti.move(PileType.CASCADE, 16, 1, PileType.OPEN, 1);
    gameMulti.move(PileType.CASCADE, 16, 0, PileType.OPEN, 2);
    gameMulti.move(PileType.CASCADE, 15, 2, PileType.CASCADE, 3);
    assertEquals("", viewMulti.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidCascToCascMultiMoverColorsAreTheSame() {
    gameMulti.startGame(gameMulti.getDeck(), 10, 4, false);
    gameMulti.move(PileType.CASCADE, 9, 4, PileType.OPEN, 0);
    gameMulti.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void notEnoughOpenPiles() {
    gameMulti.startGame(gameMulti.getDeck(), 9, 4, false);
    gameMulti.move(PileType.CASCADE, 2, 5, PileType.OPEN, 0);
    gameMulti.move(PileType.CASCADE, 2, 4, PileType.OPEN, 1);
    gameMulti.move(PileType.CASCADE, 1, 5, PileType.OPEN, 2);
    gameMulti.move(PileType.CASCADE, 7, 4, PileType.OPEN, 3);
    gameMulti.move(PileType.CASCADE, 7, 3, PileType.CASCADE, 3);
    gameMulti.move(PileType.CASCADE, 3, 5, PileType.CASCADE, 0);

    System.out.println(viewMulti.toString());
  }
}
