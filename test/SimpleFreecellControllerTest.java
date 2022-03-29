import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.StringReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Represents a simple free cell controller class.
 */
public class SimpleFreecellControllerTest {

  private SimpleFreecellModel model;
  private Appendable dest;
  private List<Card> deck;

  @Before
  public void initData() {
    model = new SimpleFreecellModel();
    dest = new StringBuilder();
    FreecellView view = new FreecellTextView(model, dest);
    deck = model.getDeck();

  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAppendableTransmissionFail() {
    Appendable out = new InvalidAppendable();
    Readable in = new StringReader("C1 2 F2");

    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);

  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidReadableTransmissionFail() {
    Appendable out = new StringBuilder();
    Readable in = new InvalidReadable();

    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);

  }

  @Test
  public void testMockModelAppendable() {
    Readable in = new StringReader("C4 5 F3");
    Appendable out = new StringBuilder();

    Appendable out2 = new StringBuilder();
    MockModel model2 = new MockModel(in);

    FreecellController controller = new SimpleFreecellController(model2, in, out2);
    controller.playGame(deck, 4, 5, false);

    assertEquals(in.toString(), "");

  }

  @Test
  public void testMockModelReader() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C1 2 F2");

    Appendable out2 = new StringBuilder();
    MockModel model2 = new MockModel(out);
    SimpleFreecellModel model = new SimpleFreecellModel();

    FreecellController controller = new SimpleFreecellController(model2, in, out2);
    controller.playGame(deck, 4, 4, false);
    assertEquals("CASCADE01FOUNDATION1", out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testMockModelReader444() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C4 4 F4");

    Appendable out2 = new StringBuilder();
    MockModel model2 = new MockModel(out);
    SimpleFreecellModel model = new SimpleFreecellModel();

    FreecellController controller = new SimpleFreecellController(model2, in, out2);
    controller.playGame(deck, 4, 4, false);
    assertEquals("CASCADE33FOUNDATION3", out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testNoQ() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C");

  }

  @Test
  public void mulitCardController() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("q");

    Appendable out2 = new StringBuilder();
    MockModel model2 = new MockModel(out);
    FreecellModel<Card> model1 = FreecellModelCreator.create(GameType.MULTIMOVE);

    FreecellController controller = new SimpleFreecellController(model1, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + "C2: 2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠\n"
        + "C3: 3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + "C4: 4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n"
        + "\n"
        + "Game quit prematurely.", out.toString());

  }

  @Test
  public void mulitCardControllerValid() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C2 13 O4 q");

    Appendable out2 = new StringBuilder();
    MockModel model2 = new MockModel(out);
    FreecellModel<Card> model1 = FreecellModelCreator.create(GameType.MULTIMOVE);

    FreecellController controller = new SimpleFreecellController(model1, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + "C2: 2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠\n"
        + "C3: 3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + "C4: 4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O3:\n"
        + "O4: J♠\n"
        + "C1: A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + "C2: 2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠\n"
        + "C3: 3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + "C4: 4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n"
        + "\n"
        + "Game quit prematurely.", out.toString());

  }


  @Test
  public void testMockModelReader241() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C2 4 F1 q");

    Appendable out2 = new StringBuilder();
    MockModel model2 = new MockModel(out);
    SimpleFreecellModel model = new SimpleFreecellModel();

    FreecellController controller = new SimpleFreecellController(model2, in, out2);
    controller.playGame(deck, 4, 4, false);
    assertEquals("CASCADE13FOUNDATION0", out.toString());
  }

  @Test
  public void gameQuit() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C2 Q 4 F1");

    Appendable out2 = new StringBuilder();
    MockModel model2 = new MockModel(out);
    SimpleFreecellModel model = new SimpleFreecellModel();

    FreecellController controller = new SimpleFreecellController(model2, in, out2);
    controller.playGame(deck, 4, 4, false);
    assertEquals(" ", this.dest.toString());
  }




  /*
  TODO: test more cases for StringReader
        test more playGames  for controller with different StringReader
   */


  @Test
  public void testControllerInvalidMove() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("A1 C1 1 F1");

    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals(out.toString(), " F1:\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + " C2:2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠\n"
        + " C3:3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + " C4:4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n"
        + "Invalid source pile: re-enterInvalid move. Try again F1:\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + " C2:2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠\n"
        + " C3:3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + " C4:4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n");
  }

  @Test
  public void testControllerQuitMoveSourcePile() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("Q1 C1 1 F1");

    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals(out.toString(), " F1:\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + " C2:2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠\n"
        + " C3:3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + " C4:4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n"
        + "\n"
        + "Game quit prematurely.");
  }

  @Test
  public void controllerInvalidCascade() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("Cc1 C3 14 01");

    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals(out.toString(), " F1:\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + " C2:2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠\n"
        + " C3:3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + " C4:4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n"
        + "\n"
        + "Game quit prematurely.");
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerNullDeck() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("c1c1q");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(null, 4, 4, false);

  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerNullAppendable() {
    Appendable out = null;
    Readable in = new StringReader("ppppppppppQ");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);

  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerNullReadable() {
    Appendable out = new StringBuilder();
    Readable in = null;
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);

  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerNullModelObject() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("c1c1q");
    FreecellController controller = new SimpleFreecellController(null, in, out);
    controller.playGame(deck, 4, 4, false);

  }

  @Test
  public void testErrorMessageInvalidParameters() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C1 0 01");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 2, 4, false);
    assertEquals("Could not start game.", out.toString());

  }

  @Test
  public void testOnlyInvalidCardIndex() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C1 50000 01q");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals("Invalid card index: re-enter", out.toString());

  }

  @Test
  public void testOnlyInvalidSourcePile() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C34 5 O1");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals("Invalid source pile: re-enter", out.toString());

  }

  @Test
  public void testQDestinationPile() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C3 5 QO1q");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 4, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + "C2: 2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠\n"
        + "C3: 3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + "C4: 4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠\n"
        + "\n"
        + "Game quit prematurely.", out.toString());
  }

  @Test
  public void testGameCompletedSuccessfully() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C1 1 F1 "
        + "C2 1 F1 "
        + "C3 1 F1 "
        + "C4 1 F1 "
        + "C5 1 F1 "
        + "C6 1 F1 "
        + "C7 1 F1 "
        + "C8 1 F1 "
        + "C9 1 F1 "
        + "C10 1 F1 "
        + "C11 1 F1 "
        + "C12 1 F1 "
        + "C13 1 F1 "
        + "C14 1 F2 "
        + "C15 1 F2 "
        + "C16 1 F2 "
        + "C17 1 F2 "
        + "C18 1 F2 "
        + "C19 1 F2 "
        + "C20 1 F2 "
        + "C21 1 F2 "
        + "C22 1 F2 "
        + "C23 1 F2 "
        + "C24 1 F2 "
        + "C25 1 F2 "
        + "C26 1 F2 "
        + "C27 1 F3 "
        + "C28 1 F3 "
        + "C29 1 F3 "
        + "C30 1 F3 "
        + "C31 1 F3 "
        + "C32 1 F3 "
        + "C33 1 F3 "
        + "C34 1 F3 "
        + "C35 1 F3 "
        + "C36 1 F3 "
        + "C37 1 F3 "
        + "C38 1 F3 "
        + "C39 1 F3 "
        + "C40 1 F4 "
        + "C41 1 F4 "
        + "C42 1 F4 "
        + "C43 1 F4 "
        + "C44 1 F4 "
        + "C45 1 F4 "
        + "C46 1 F4 "
        + "C47 1 F4 "
        + "C48 1 F4 "
        + "C49 1 F4 "
        + "C50 1 F4 "
        + "C51 1 F4 "
        + "C52 1 F4  q");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 52, 4, false);
    assertEquals("", out.toString());

  }

  @Test
  public void testValidMoveCompletedSuccessfully() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("C1 6 O1 q");
    FreecellController controller = new SimpleFreecellController(model, in, out);
    controller.playGame(deck, 10, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, J♣, 8♥, 5♦, 2♠, Q♠\n"
        + "C2: 2♣, Q♣, 9♥, 6♦, 3♠, K♠\n"
        + "C3: 3♣, K♣, 10♥, 7♦, 4♠\n"
        + "C4: 4♣, A♥, J♥, 8♦, 5♠\n"
        + "C5: 5♣, 2♥, Q♥, 9♦, 6♠\n"
        + "C6: 6♣, 3♥, K♥, 10♦, 7♠\n"
        + "C7: 7♣, 4♥, A♦, J♦, 8♠\n"
        + "C8: 8♣, 5♥, 2♦, Q♦, 9♠\n"
        + "C9: 9♣, 6♥, 3♦, K♦, 10♠\n"
        + "C10: 10♣, 7♥, 4♦, A♠, J♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: Q♠\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, J♣, 8♥, 5♦, 2♠\n"
        + "C2: 2♣, Q♣, 9♥, 6♦, 3♠, K♠\n"
        + "C3: 3♣, K♣, 10♥, 7♦, 4♠\n"
        + "C4: 4♣, A♥, J♥, 8♦, 5♠\n"
        + "C5: 5♣, 2♥, Q♥, 9♦, 6♠\n"
        + "C6: 6♣, 3♥, K♥, 10♦, 7♠\n"
        + "C7: 7♣, 4♥, A♦, J♦, 8♠\n"
        + "C8: 8♣, 5♥, 2♦, Q♦, 9♠\n"
        + "C9: 9♣, 6♥, 3♦, K♦, 10♠\n"
        + "C10: 10♣, 7♥, 4♦, A♠, J♠\n"
        + "\n"
        + "Game quit prematurely.", out.toString());

  }


}
