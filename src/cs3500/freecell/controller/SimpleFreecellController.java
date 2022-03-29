package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a simple free cell controller.
 */
public class SimpleFreecellController implements FreecellController<Card> {

  private Readable readable;
  private FreecellView boardView;
  private FreecellModel model;

  /**
   * Represents a simple free cell controller.
   *
   * @param model the model.
   * @param rd    the readable.
   * @param ap    the appendable.
   * @throws IllegalArgumentException throws exception.
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Error: the arguments are null.");
    }

    //creates a view object that is connected to appendable
    boardView = new FreecellTextView(model, ap);
    readable = rd;
    this.model = model;
  }


  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("The deck or model is null.");
    }
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      try {
        boardView.renderMessage("Could not start game.");
      } catch (IOException ioException) {
        throw new IllegalStateException("Transmission failed.");
      }
      return;
    }
    try {
      boardView.renderBoard();
    } catch (IOException e) {
      throw new IllegalStateException("Transmission failed.");
    }
    if (!model.isGameOver()) {
      Scanner sc;
      try {
        sc = new Scanner(readable);
      } catch (IllegalStateException e) {
        throw new IllegalStateException("The readable transmission failed.");
      }

      while (sc.hasNext()) {

        PileType source = null;
        int sourcePileNumber = -1;
        int cardIndex = -1;
        PileType destination = null;
        int destPileNumber = -1;
        String input;
        char firstChar;

        boolean flag = true;
        while (flag) {
          input = sc.next();
          firstChar = input.charAt(0);

          //If the game quits
          if (firstChar == 'Q' || firstChar == 'q') {
            try {
              boardView.renderMessage("\n" + "Game quit prematurely.");
            } catch (IOException e) {
              throw new IllegalStateException("Transmission failed.");
            }
            return;
          }

          String secondString = input.substring(1);

          if (firstChar == 'C' || firstChar == 'F' || firstChar == 'O') {
            sourcePileNumber = convertStringToInt(secondString);
            if (sourcePileNumber != -1) {
              source = getPileTypeFromChar(firstChar);
            }
          }
          if (sourcePileNumber == -1 || source == null) {
            try {
              boardView.renderMessage("Invalid source pile: re-enter");
            } catch (IOException e) {
              throw new IllegalStateException("Transmission failed.");
            }
          } else {
            break;
          }
        }
        //SECOND STRING
        while (flag) {
          input = sc.next();
          firstChar = input.charAt(0);
          if (firstChar == 'Q' || firstChar == 'q') {
            try {
              boardView.renderMessage("\n" + "Game quit prematurely.");
            } catch (IOException e) {
              throw new IllegalStateException("Transmission failed.");
            }
            return;
          }

          cardIndex = convertStringToInt(input);
          if (cardIndex == -1) {
            try {
              boardView.renderMessage("Invalid card index: re-enter");
            } catch (IOException e) {
              throw new IllegalStateException("Transmission failed.");
            }
          } else {
            break;
          }
        }

        //dest pile
        while (flag) {
          input = sc.next();
          firstChar = input.charAt(0);

          //If the game quits
          if (firstChar == 'Q' || firstChar == 'q') {
            try {
              boardView.renderMessage("\n" + "Game quit prematurely.");
            } catch (IOException e) {
              throw new IllegalStateException("Transmission failed.");
            }
            return;
          }

          String secondString = input.substring(1);

          if (firstChar == 'C' || firstChar == 'F' || firstChar == 'O') {
            destPileNumber = convertStringToInt(secondString);
            if (destPileNumber != -1) {
              destination = getPileTypeFromChar(firstChar);
            }
          }
          if (destPileNumber == -1 || destination == null) {
            try {
              boardView.renderMessage("Invalid destination pile.");
            } catch (IOException e) {
              throw new IllegalStateException("Transmission failed.");
            }
          } else {
            break;
          }
        }
        try {
          model.move(source, sourcePileNumber - 1, cardIndex - 1, destination,
              destPileNumber - 1);
        } catch (IllegalArgumentException e) {
          try {
            boardView.renderMessage("Invalid move. Try again");
          } catch (IOException ioException) {
            throw new IllegalStateException("Transmission failed.");
          }
        } catch (IllegalStateException e) {
          try {
            boardView.renderMessage("Invalid move. Try again");
          } catch (IOException ioException) {
            throw new IllegalStateException("Transmission failed.");
          }
        }

        try {
          boardView.renderBoard();
        } catch (IOException e) {
          throw new IllegalStateException("Transmission failed.");
        }
        if (model.isGameOver()) {
          try {
            boardView.renderMessage("Game over.");
            return;
          } catch (IOException e) {
            throw new IllegalStateException("Transmission failed.");
          }
        }
      }
      //not winning or quitting and run out of inputs
      throw new IllegalStateException("No next");
    }
  }

  //Gets the source pile number
  private int convertStringToInt(String firstStringSecChar) {
    int sourcePileNumber = 0;
    try {
      sourcePileNumber = Integer.parseInt(firstStringSecChar);
    } catch (NumberFormatException e) {
      sourcePileNumber = -1;
    }
    return sourcePileNumber;
  }

  //Returns the PileType from the character
  private PileType getPileTypeFromChar(char charInInput) {
    switch (charInInput) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      default:
        //nothing
    }
    //this will not execute.
    return null;
  }
}


