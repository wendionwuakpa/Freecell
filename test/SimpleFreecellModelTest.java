import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.Card.SUIT;
import cs3500.freecell.model.hw02.Card.VALUE;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the Freecellmodel.
 */
public class SimpleFreecellModelTest {

  SimpleFreecellModel game;
  SimpleFreecellModel game2;
  ArrayList<Card> emptyDeck;
  ArrayList<Card> dupDeck;
  FreecellView view;
  Card twoDiamond;

  @Before
  public void initFreeCellModel() {
    this.twoDiamond = new Card(VALUE.TWO, SUIT.DIAMOND);
    game = new SimpleFreecellModel();
    game2 = new SimpleFreecellModel();
    game2.createDeck();
    view = new FreecellTextView(game);

    emptyDeck = new ArrayList<Card>();
    dupDeck = new ArrayList<Card>();
    for (int i = 0; i < 51; ) {
      for (SUIT suit : SUIT.values()) {
        for (VALUE value : VALUE.values()) {
          dupDeck.add(new Card(value, suit));
          i++;
        }
      }
      dupDeck.add(51, dupDeck.get(2));
    }
  }

  @Test(expected = IllegalStateException.class)
  public void tesGameHasNotStarted() {
    game.move(PileType.OPEN, 4, 4, PileType.CASCADE, 3);
  }

  //one test per method
  @Test
  public void openToOpenTest() {
    game.startGame(game.getDeck(), 8, 4, false);
    game.move(PileType.CASCADE, 7, 5, PileType.OPEN, 1);
    game.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    game.move(PileType.OPEN, 1, 0, PileType.OPEN, 3);
    assertEquals(" F1:A♠\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:9♠\n"
        + " C1:A♣, 9♣, 4♥, Q♥, 7♦, 2♠, 10♠\n"
        + " C2:2♣, 10♣, 5♥, K♥, 8♦, 3♠, J♠\n"
        + " C3:3♣, J♣, 6♥, A♦, 9♦, 4♠, Q♠\n"
        + " C4:4♣, Q♣, 7♥, 2♦, 10♦, 5♠, K♠\n"
        + " C5:5♣, K♣, 8♥, 3♦, J♦, 6♠\n"
        + " C6:6♣, A♥, 9♥, 4♦, Q♦, 7♠\n"
        + " C7:7♣, 2♥, 10♥, 5♦, K♦, 8♠\n"
        + " C8:8♣, 3♥, J♥, 6♦", view.toString());
  }

  @Test
  public void cascToFound() {
    game.startGame(game.getDeck(), 8, 4, false);
    game.move(PileType.CASCADE, 7, 5, PileType.OPEN, 1);
    game.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(" F1:A♠\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:9♠\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 9♣, 4♥, Q♥, 7♦, 2♠, 10♠\n"
        + " C2:2♣, 10♣, 5♥, K♥, 8♦, 3♠, J♠\n"
        + " C3:3♣, J♣, 6♥, A♦, 9♦, 4♠, Q♠\n"
        + " C4:4♣, Q♣, 7♥, 2♦, 10♦, 5♠, K♠\n"
        + " C5:5♣, K♣, 8♥, 3♦, J♦, 6♠\n"
        + " C6:6♣, A♥, 9♥, 4♦, Q♦, 7♠\n"
        + " C7:7♣, 2♥, 10♥, 5♦, K♦, 8♠\n"
        + " C8:8♣, 3♥, J♥, 6♦", view.toString());
  }

  @Test
  public void cascToOpen() {
    game.startGame(game.getDeck(), 8, 4, false);
    game.move(PileType.CASCADE, 7, 5, PileType.OPEN, 1);
    assertEquals(" F1:\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:9♠\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 9♣, 4♥, Q♥, 7♦, 2♠, 10♠\n"
        + " C2:2♣, 10♣, 5♥, K♥, 8♦, 3♠, J♠\n"
        + " C3:3♣, J♣, 6♥, A♦, 9♦, 4♠, Q♠\n"
        + " C4:4♣, Q♣, 7♥, 2♦, 10♦, 5♠, K♠\n"
        + " C5:5♣, K♣, 8♥, 3♦, J♦, 6♠\n"
        + " C6:6♣, A♥, 9♥, 4♦, Q♦, 7♠\n"
        + " C7:7♣, 2♥, 10♥, 5♦, K♦, 8♠\n"
        + " C8:8♣, 3♥, J♥, 6♦, A♠", view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testADuplicateDeck() {
    game2.startGame(dupDeck, 5, 4, true);
  }

  @Test
  public void gameIsNotOver() {
    assertEquals(false, game2.isGameOver());
  }

  @Test
  public void AccurateMoveFromCascadeToOpenToFound() {
    game.startGame(game.getDeck(), 12, 4, false);
    game.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 0);
    System.out.println(game);
    game.move(PileType.CASCADE, 4, 3, PileType.FOUNDATION, 0);
    assertEquals(" F1: A♠, 2♠\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:K♠\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, K♣, Q♥, J♦, 10♠\n"
        + " C2:2♣, A♥, K♥, Q♦, J♠\n"
        + " C3:3♣, 2♥, A♦, K♦, Q♠\n"
        + " C4:4♣, 3♥, 2♦\n"
        + " C5:5♣, 4♥, 3♦\n"
        + " C6:6♣, 5♥, 4♦, 3♠\n"
        + " C7:7♣, 6♥, 5♦, 4♠\n"
        + " C8:8♣, 7♥, 6♦, 5♠\n"
        + " C9:9♣, 8♥, 7♦, 6♠\n"
        + " C10:10♣, 9♥, 8♦, 7♠\n"
        + " C11:J♣, 10♥, 9♦, 8♠\n"
        + " C12:Q♣, J♥, 10♦, 9♠", view.toString());

  }

  @Test
  public void isGameOverTrue() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 5, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 7, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 8, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 9, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 10, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 11, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 12, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 13, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 14, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 15, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 16, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 17, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 18, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 19, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 20, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 21, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 22, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 23, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 24, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 25, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 26, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 27, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 28, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 29, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 30, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 31, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 32, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 33, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 34, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 35, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 36, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 37, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 38, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 39, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 40, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 41, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 42, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 43, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 44, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 45, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 46, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 47, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 48, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 49, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 50, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 51, 0, PileType.FOUNDATION, 3);
    assertEquals(true, game.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void InvalidValueSourceValIsGreaterThanDestVal() {
    game.startGame(game.getDeck(), 6, 4, true);
    game.move(PileType.CASCADE, 0, 8, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 2, 8, PileType.OPEN, 1);
    game.move(PileType.CASCADE, 0, 7, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cascadeToCascadeMoveColorsAreTheSame() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 2, 0, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void multipleMovesThatEndWithOpenToOpen() {
    game.startGame(game.getDeck(), 4, 4, false);
    game.move(PileType.CASCADE, 3, 12, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 3, 11, PileType.OPEN, 1);
    game.move(PileType.CASCADE, 3, 10, PileType.OPEN, 2);
    game.move(PileType.CASCADE, 3, 9, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 3, 8, PileType.CASCADE, 1);
    game.move(PileType.OPEN, 2, 0, PileType.CASCADE, 3);
    game.move(PileType.OPEN, 0, 0, PileType.CASCADE, 3);

    assertEquals(" F1:A♠\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:K♠\n"
        + " O2:9♠\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 5♣, 9♣, K♣, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠\n"
        + " C2:2♣, 6♣, 10♣, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 10♦\n"
        + " C3:3♣, 7♣, J♣, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠\n"
        + " C4:4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 5♠", view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void suitsDontMatchCascToFound() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 13, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void validOpentoFoundMove() {
    game.startGame(game.getDeck(), 10, 4, false);
    game.move(PileType.CASCADE, 9, 4, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 9, 3, PileType.FOUNDATION, 0);
    assertEquals(" F1:A♠\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:J♠\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, J♣, 8♥, 5♦, 2♠, Q♠\n"
        + " C2:2♣, Q♣, 9♥, 6♦, 3♠, K♠\n"
        + " C3:3♣, K♣, 10♥, 7♦, 4♠\n"
        + " C4:4♣, A♥, J♥, 8♦, 5♠\n"
        + " C5:5♣, 2♥, Q♥, 9♦, 6♠\n"
        + " C6:6♣, 3♥, K♥, 10♦, 7♠\n"
        + " C7:7♣, 4♥, A♦, J♦, 8♠\n"
        + " C8:8♣, 5♥, 2♦, Q♦, 9♠\n"
        + " C9:9♣, 6♥, 3♦, K♦, 10♠\n"
        + " C10:10♣, 7♥, 4♦", view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void suitsDontMatchOpenToFound4Open6Cascade() {
    game.startGame(game.getDeck(), 6, 4, false);
    game.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    game.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 3, 5, PileType.OPEN, 2);
    game.move(PileType.OPEN, 0, 2, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sourceValuePlusTwoGreaterOpenToFound() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNonAceFirstMoveOpenToFound() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cascadeToOpenMoveWithAlreadyOccupiedOpenpile() {
    game.startGame(game.getDeck(), 52, 4, true);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 1, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void openToOpenMoveWithAlreadyOccupiedOpenpile() {
    game.startGame(game.getDeck(), 52, 4, true);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 1, 0, PileType.OPEN, 1);
    game.move(PileType.OPEN, 1, 0, PileType.OPEN, 0);
  }


  @Test
  public void numCardsInFoundationPile() {
    game.startGame(game.getDeck(), 8, 4, false);
    game.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(1, game.getNumCardsInFoundationPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumCardsInFoundationPile() {
    game.startGame(game.getDeck(), 8, 4, false);
    game.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    game.getNumCardsInFoundationPile(4);
  }

  @Test
  public void zeroCardsInFoundationPile() {
    game.startGame(game.getDeck(), 8, 4, false);
    game.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(0, game.getNumCardsInFoundationPile(3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroCardsFoundationPileGetFoundCardAt() {
    game.startGame(game.getDeck(), 4, 4, false);
    game.getFoundationCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void foundationPileGameNotStarted() {
    game.getNumCardsInFoundationPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void foundationPileHasInvalidIndex() {
    game.startGame(game.getDeck(), 4, 4, true);
    game.getNumCardsInFoundationPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void foundationPileIndexGreaterThanNumberOfFoundationPiles() {
    game.startGame(game.getDeck(), 4, 4, true);
    game.getNumCardsInFoundationPile(5);
  }
  //========================Tests for number of cards in Cascade Piles====================

  @Test
  public void numCardsInCascadePile() {
    game.startGame(game.getDeck(), 12, 3, false);
    assertEquals(5, game.getNumCardsInCascadePile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumCardsInCascadePile() {
    game.startGame(game.getDeck(), 12, 3, false);
    game.getNumCardsInCascadePile(13);
  }

  @Test(expected = IllegalArgumentException.class)
  public void foundCardAtPileIndexIsTooHigh() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    game.getFoundationCardAt(5, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void foundCardAtCardIndexIsTooHigh() {
    game.startGame(game.getDeck(), 52, 4, false);
    System.out.println(game);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    game.getFoundationCardAt(0, 2);
  }

  @Test
  public void zeroCardsInCascadePile() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(0, game.getNumCardsInCascadePile(0));
  }

  @Test(expected = IllegalStateException.class)
  public void cascadePileGameNotStarted() {
    game.getNumCardsInCascadePile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cascadePileHasInvalidIndex() {
    game.startGame(game.getDeck(), 4, 4, true);
    game.getNumCardsInFoundationPile(-1);
  }

  //====================The number of cards in a Cascade pile
  @Test
  public void gameHasNotStarted() {
    assertEquals(-1, game.getNumCascadePiles());
  }

  @Test
  public void fourCascadePiles() {
    game.startGame(game.getDeck(), 4, 4, true);
    assertEquals(4, game.getNumCascadePiles());
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroOrInvalidCascadePiles() {
    game.startGame(game.getDeck(), 0, 4, true);
    assertEquals(0, game.getNumCascadePiles());
  }

  //====================The number of cards in a Open pile

  @Test(expected = IllegalArgumentException.class)
  public void negativeOpenPileIndex() {
    game.startGame(game.getDeck(), 4, 5, false);
    game.getNumCardsInOpenPile(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void openPileGameHasNotStarted() {
    game.getNumCardsInOpenPile(0);
  }

  @Test
  public void zeroNumCardsInOpenPile() {
    game.startGame(game.getDeck(), 4, 5, false);
    assertEquals(0, game.getNumCardsInOpenPile(0));
  }

  @Test
  public void OneCardInOpenPile() {
    game.startGame(game.getDeck(), 12, 5, false);
    game.move(PileType.CASCADE, 0, 4, PileType.OPEN, 0);
    assertEquals(1, game.getNumCardsInOpenPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNotTheLastCard() {
    game.startGame(game.getDeck(), 12, 5, false);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
  }

  //------The number of Open Piles
  @Test
  public void numOpenPilesGameNotStarted() {
    assertEquals(-1, game.getNumOpenPiles());
  }

  @Test
  public void validNumberOfOpenPiles() {
    game.startGame(game.getDeck(), 4, 4, false);
    assertEquals(4, game.getNumOpenPiles());
  }

  //=====Test getting the card at an index in a pile=====
  /*
    Pile index is -1,
    pile index > size-1,
    card index is  -1,
    DONOT NEED card index is not the last
    game not started,
    both indices are valid
    if the card is null
   */

  @Test
  public void validFoundationCard() {
    game.startGame(game.getDeck(), 12, 4, false);
    game.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 0);
    assertEquals("A♠", game.getFoundationCardAt(0, 0).toString());
  }

  @Test
  public void validCascadeCard() {
    game.startGame(game.getDeck(), 8, 4, false);
    assertEquals("10♠", game.getCascadeCardAt(0, 6).toString());
  }

  @Test
  public void validOpenCard() {
    game.startGame(game.getDeck(), 8, 4, false);
    game.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals("10♠", game.getOpenCardAt(0).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void getFoundationCardAtGameHasNotStarted() {
    game.getFoundationCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void getCascadeCardAtGameHasNotStarted() {
    game.getCascadeCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pileIndexTooHigh() {
    game.startGame(game.getDeck(), 5, 4, false);
    game.getCascadeCardAt(5, -1);
  }

  @Test(expected = IllegalStateException.class)
  public void getOpenCardAtGameHasNotStarted() {
    game.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void openPileIndexTooHigh() {
    game.startGame(game.getDeck(), 5, 4, false);
    game.getOpenCardAt(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidPileAndCardOpenPileIndex() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.getOpenCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidPileAndCardIndex() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.getFoundationCardAt(-1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidFoundationPileIndex() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.getFoundationCardAt(4, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void notLastCardIndex() {
    game.startGame(game.getDeck(), 4, 4, true);
    game.getFoundationCardAt(3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullFoundationCard() {
    game.startGame(game.getDeck(), 12, 4, false);
    game.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 0);
    assertEquals("", game.getFoundationCardAt(1, 0).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void cardIndexTooHigh() {
    game.startGame(game.getDeck(), 4, 4, true);
    game.getFoundationCardAt(3, 22);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFromOpenToFoundSuitsAreDiiferent() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 13, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void nullOpenCard() {
    game.startGame(game.getDeck(), 12, 4, false);
    game.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    assertEquals(" F1:\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:K♠\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, K♣, Q♥, J♦, 10♠\n"
        + " C2:2♣, A♥, K♥, Q♦, J♠\n"
        + " C3:3♣, 2♥, A♦, K♦, Q♠\n"
        + " C4:4♣, 3♥, 2♦, A♠\n"
        + " C5:5♣, 4♥, 3♦, 2♠\n"
        + " C6:6♣, 5♥, 4♦, 3♠\n"
        + " C7:7♣, 6♥, 5♦, 4♠\n"
        + " C8:8♣, 7♥, 6♦, 5♠\n"
        + " C9:9♣, 8♥, 7♦, 6♠\n"
        + " C10:10♣, 9♥, 8♦, 7♠\n"
        + " C11:J♣, 10♥, 9♦, 8♠\n"
        + " C12:Q♣, J♥, 10♦, 9♠", view.toString());
  }

  @Test
  public void hasGameStartedCalledTwice() {
    game.startGame(game.getDeck(), 4, 4, false);
    game.startGame(game.getDeck(), 5, 4, false);
    assertEquals(" F1:\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:A♣, 6♣, J♣, 3♥, 8♥, K♥, 5♦, 10♦, 2♠, 7♠, Q♠\n"
        + " C2:2♣, 7♣, Q♣, 4♥, 9♥, A♦, 6♦, J♦, 3♠, 8♠, K♠\n"
        + " C3:3♣, 8♣, K♣, 5♥, 10♥, 2♦, 7♦, Q♦, 4♠, 9♠\n"
        + " C4:4♣, 9♣, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♠, 10♠\n"
        + " C5:5♣, 10♣, 2♥, 7♥, Q♥, 4♦, 9♦, A♠, 6♠, J♠", view.toString());
  }

  //=====================================================================================
  //              Modified Tests after the Self-eval
  //=====================================================================================


  @Test(expected = IllegalArgumentException.class)
  public void cascadePilesCountIsLessThanFour() {
    game.startGame(game.getDeck(), 3, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void openPileCountIsLessThanOne() {
    game.startGame(game.getDeck(), 5, 0, true);
  }

  @Test
  public void startGameShufflesGameWhenTrue() {
    game.startGame(game.getDeck(), 4, 4, false);
    game.startGame(game.getDeck(), 4, 4, true);
    assertNotEquals(" F1:\n"
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
        + " C4:4♣, 8♣, Q♣, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠", view.toString());
  }

  @Test
  public void moveToNonEmptyFoundationPile() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(" F1: A♣, 2♣\n"
        + " F2:\n"
        + " F3:\n"
        + " F4:\n"
        + " O1:\n"
        + " O2:\n"
        + " O3:\n"
        + " O4:\n"
        + " C1:\n"
        + " C2:\n"
        + " C3:3♣\n"
        + " C4:4♣\n"
        + " C5:5♣\n"
        + " C6:6♣\n"
        + " C7:7♣\n"
        + " C8:8♣\n"
        + " C9:9♣\n"
        + " C10:10♣\n"
        + " C11:J♣\n"
        + " C12:Q♣\n"
        + " C13:K♣\n"
        + " C14:A♥\n"
        + " C15:2♥\n"
        + " C16:3♥\n"
        + " C17:4♥\n"
        + " C18:5♥\n"
        + " C19:6♥\n"
        + " C20:7♥\n"
        + " C21:8♥\n"
        + " C22:9♥\n"
        + " C23:10♥\n"
        + " C24:J♥\n"
        + " C25:Q♥\n"
        + " C26:K♥\n"
        + " C27:A♦\n"
        + " C28:2♦\n"
        + " C29:3♦\n"
        + " C30:4♦\n"
        + " C31:5♦\n"
        + " C32:6♦\n"
        + " C33:7♦\n"
        + " C34:8♦\n"
        + " C35:9♦\n"
        + " C36:10♦\n"
        + " C37:J♦\n"
        + " C38:Q♦\n"
        + " C39:K♦\n"
        + " C40:A♠\n"
        + " C41:2♠\n"
        + " C42:3♠\n"
        + " C43:4♠\n"
        + " C44:5♠\n"
        + " C45:6♠\n"
        + " C46:7♠\n"
        + " C47:8♠\n"
        + " C48:9♠\n"
        + " C49:10♠\n"
        + " C50:J♠\n"
        + " C51:Q♠\n"
        + " C52:K♠", view.toString());
  }

  @Test
  public void testNumOpenPilesIsNegativeOneBeforeGameStarts() {
    assertEquals(-1, game.getNumOpenPiles());
  }

  @Test
  public void testNumCascadePilesIsNegativeOneBeforeGameStarts() {
    assertEquals(-1, game.getNumCascadePiles());
  }

  @Test(expected = IllegalStateException.class)
  public void toStringReturnsAnEmptyStringAfterStartGameThrowsAnException() {
    try {
      game.startGame(null, 4, 4, false);
    } catch (IllegalArgumentException e) {
      assertEquals("", view.toString());
    }
  }
}


