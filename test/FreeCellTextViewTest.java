import static org.junit.Assert.assertEquals;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests free cell.
 */
public class FreeCellTextViewTest {

  private SimpleFreecellModel model;
  private Appendable dest;
  private FreecellView view;


  @Before
  public void initData() {
    model = new SimpleFreecellModel();
    dest = new StringBuilder();
    view = new FreecellTextView(model, dest);
    List<Card> deck = model.getDeck();

  }

  @Test
  public void testQuitMessage() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("BBBB q");

    FreecellController controller = new SimpleFreecellController(model, in, out);
    try {
      view.renderMessage("Game quit.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(this.dest.toString(), "Game quit.");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMessage() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("BBBB");

    FreecellController controller = new SimpleFreecellController(model, in, out);
    try {
      view.renderMessage("Could not start game.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    controller.playGame(null, 0, 3, false);
    assertEquals(this.dest.toString(), "Could not start game.");
  }

}
