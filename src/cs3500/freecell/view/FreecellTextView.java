package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import java.io.IOException;


/**
 * Represents the view of a Freecell game.
 */
public class FreecellTextView implements FreecellView {

  private FreecellModel<?> model;
  private Appendable destination;


  /**
   * Constructs the object view of a freeCell game.
   *
   * @param model the model.
   */
  public FreecellTextView(FreecellModel<?> model) {

    this.model = model;

  }

  /**
   * Constructs object with appendable as its destination.
   *
   * @param model       the model.
   * @param destination the destination.
   */
  public FreecellTextView(FreecellModel<?> model, Appendable destination) {
    this.model = model;
    this.destination = destination;
  }

  @Override
  public String toString() {
    // The game has not started if the number of cascadePiles = 0.
    if (model.getNumCascadePiles() == -1) {
      return "";
    }
    StringBuilder resultString;
    resultString = new StringBuilder();
    int j = 0;
    for (int i = 0; i < 4; i++) {
      if (resultString.length() == 0) {
        resultString.append("F" + Integer.toString(i + 1) + ":");
      } else {
        resultString.append("F" + Integer.toString(i + 1) + ":");
      }

      if (model.getNumCardsInFoundationPile(i) == 0) {
        resultString.append("\n");
      }
      //this parts executes when we have made moves to the Foundation pile.
      else {
        for (j = 0; j < model.getNumCardsInFoundationPile(i) - 1; j++) {
          resultString.append(" ").append(model.getFoundationCardAt(i, j).toString()).append(", ");
        }
        resultString.append(model.getFoundationCardAt(i, j).toString()).append("\n");
      }
    }
    j = 0;
    for (int i = 0; i < model.getNumOpenPiles(); i++) {
      resultString.append("O" + Integer.toString(i + 1) + ":");
      if (model.getNumCardsInOpenPile(i) == 0) {
        resultString.append("\n");
      } else {
        if (model.getOpenCardAt(i) != null) {
          resultString.append(" ").append(model.getOpenCardAt(i)).append("\n");
        } else {
          //if openPile is null/empty
          resultString.append("\n");
        }
      }
    }

    j = 0;
    for (int i = 0; i < model.getNumCascadePiles(); i++) {
      resultString.append("C" + Integer.toString(i + 1) + ":");
      if (model.getNumCardsInCascadePile(i) == 0) {
        resultString.append("\n");
      } else {
        for (j = 0; j < model.getNumCardsInCascadePile(i) - 1; j++) {
          resultString.append(" ").append(model.getCascadeCardAt(i, j)).append(",");
        }
        if (i == model.getNumCascadePiles() - 1) {
          resultString.append(" ").append(model.getCascadeCardAt(i, j));
        } else {
          resultString.append(" ").append(model.getCascadeCardAt(i, j)).append("\n");
        }
      }
    }
    return resultString.toString();
  }

  @Override
  //We don't need a try-catch block because we specify with "throws IOException".
  public void renderBoard() throws IOException {
    destination.append(toString()).append("\n");
  }

  @Override
  //We don't need a try-catch block because we specify with "throws IOException".
  public void renderMessage(String message) throws IOException {
    destination.append(message);
  }
}
