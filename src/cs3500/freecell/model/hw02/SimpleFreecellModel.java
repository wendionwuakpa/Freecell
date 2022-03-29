package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card.SUIT;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a simple freecell game model.
 */
public class SimpleFreecellModel implements FreecellModel<Card> {

  private ArrayList<ArrayList<Card>> foundationPile;
  private int numFoundationPiles;
  private ArrayList<Card> openPile;
  private int numOpenPiles;
  private List<Card> deck;
  private List<List<Card>> cascadePile;
  private int numCascadePiles;
  private boolean hasGameStarted;
  private boolean isShuffled;

  /**
   * Constructs a simple freecell game model.
   */
  public SimpleFreecellModel() {
    this.foundationPile = new ArrayList<ArrayList<Card>>(4);
    this.deck = new ArrayList<Card>(52);

    //Constructs foundation piles with four empty slots
    numFoundationPiles = 4;
    for (int i = 0; i < numFoundationPiles; i++) {
      foundationPile.add(new ArrayList<>());
    }

    //Creates a deck
    createDeck();
  }

  /**
   * Creates a deck of Cards.
   */
  public void createDeck() {
    for (int i = 0; i < 52; ) {
      for (SUIT suit : SUIT.values()) {
        for (Card.VALUE value : Card.VALUE.values()) {
          deck.add(new Card(value, suit));
          i++;
        }
      }
    }
  }

  /**
   * Returns if the game has started.
   *
   * @return boolean hasGameStarted.
   */
  private boolean getHasGameStarted() {
    return hasGameStarted;
  }

  private boolean hasDuplicates(List<Card> deck) {
    Set<Card> deckSet = new HashSet<>(deck);
    return deckSet.size() < deck.size();
  }

  @Override
  public List<Card> getDeck() {
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    this.deck = (ArrayList<Card>) deck;
    if (this.deck == null || (this.deck.size() != 52) || (hasDuplicates(this.deck))) {
      throw new IllegalArgumentException("Invalid deck!");
    }

    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Number of cascade piles is less than four!");
    }

    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Invalid input: Number of open piles is less than 1!");
    }

    //we initialize the values of the cascade and open pile to our class variables.

    this.numCascadePiles = numCascadePiles;
    this.numOpenPiles = numOpenPiles;
    this.cascadePile = new ArrayList<List<Card>>(numCascadePiles);
    this.openPile = new ArrayList<Card>(numOpenPiles);

    // Constructs null(empty) open piles.
    for (int i = 0; i < numOpenPiles; i++) {
      openPile.add(null);
    }

    if (shuffle) {
      Collections.shuffle(deck);
    } else {
      //do nothing
    }
    //dealing the cards:
    //create each pile of the cascadePile
    for (int i = 0; i < numCascadePiles; i++) {
      //first create each empty list of cascadePile
      cascadePile.add(new ArrayList());
      //i connotes the pileNumber
      for (int j = i; j < deck.size(); j = j + numCascadePiles) {
        cascadePile.get(i).add(deck.get(j));
        //Can also be: j += numCascadePiles;
      }
    }
    hasGameStarted = true;
  }

  @Override
  public boolean isGameOver() {
    //we want to check if every pile is full
    boolean full = false;
    for (int i = 0; i < numFoundationPiles; i++) {
      full = false;
      if (foundationPile.get(i).size() == 13) {
        full = true;
      }
      if (!full) {
        return false;
      }
    }
    return full;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber)
      throws IllegalArgumentException, IllegalStateException {

    //determines if the game has not started.
    if (!hasGameStarted) {
      throw new IllegalStateException("Move invalid: The game has not started.");
    }
    if (isGameOver()) {
      throw new IllegalArgumentException("Move invalid: The game is over.");
    }
    //Didnt use switch because there are two conditions
    if (source == PileType.CASCADE && pileNumber > numCascadePiles - 1) {
      throw new IllegalArgumentException("Pile number is too high.");
    }

    if (source == PileType.OPEN && pileNumber > numOpenPiles - 1) {
      throw new IllegalArgumentException("Pile number is too high.");
    }

    if (source == PileType.FOUNDATION && pileNumber > numFoundationPiles - 1) {
      throw new IllegalArgumentException("Pile number is too high.");
    }
    if (destination == PileType.CASCADE && destPileNumber > numCascadePiles - 1) {
      throw new IllegalArgumentException("Pile number is too high.");
    }

    if (destination == PileType.OPEN && destPileNumber > numOpenPiles - 1) {
      throw new IllegalArgumentException("Pile number is too high.");
    }

    if (destination == PileType.FOUNDATION && destPileNumber > numFoundationPiles - 1) {
      throw new IllegalArgumentException("Pile number is too high.");
    }

    if (source == PileType.OPEN && getNumCardsInOpenPile(pileNumber) == 0) {
      throw new IllegalArgumentException("The open source pile is empty");
    }

    if (pileNumber < 0) {
      throw new IllegalArgumentException("The pile number cannot be negative.");
    }

    if (destPileNumber < 0) {
      throw new IllegalArgumentException("The pile number cannot be negative.");
    }

    if (cardIndex < 0) {
      throw new IllegalArgumentException("The card index cannot be negative.");
    }

    if (cardIndex != lastCardIndex(source, pileNumber)) {
      throw new IllegalArgumentException("It is not the last card in the pile.");
    }


    if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException("The foundation pile cannot be removed from.");
    }

    //determines if the open pile is full.
    if (destination == PileType.OPEN && openPile.get(destPileNumber) != null) {
      throw new IllegalArgumentException("The destination open pile is full!");
    }

    if (destination == PileType.OPEN && destPileNumber >= numOpenPiles) {
      throw new IllegalArgumentException("Too high destination pile.");
    }
    //ACTUAL MOVE METHODS
    /*
    1 CtoC
    2 Cto0
    3 0toO
    4 0toF
    5 CtoF
    6 OtoC
     */

    if (source == PileType.CASCADE && destination == PileType.CASCADE) {
      cascadeToCascade(source, pileNumber, cardIndex, destination, destPileNumber);
    } else if (source == PileType.CASCADE && destination == PileType.OPEN) {
      cascadeToOpen(source, pileNumber, cardIndex, destination, destPileNumber);
    } else if (source == PileType.OPEN && destination == PileType.OPEN) {
      openToOpen(source, pileNumber, cardIndex, destination, destPileNumber);
    } else if (source == PileType.OPEN && destination == PileType.FOUNDATION) {
      openToFound(source, pileNumber, cardIndex, destination, destPileNumber);
    } else if (source == PileType.CASCADE && destination == PileType.FOUNDATION) {
      casToFound(source, pileNumber, cardIndex, destination, destPileNumber);
    } else if (source == PileType.OPEN && destination == PileType.CASCADE) {
      openToCasc(source, pileNumber, cardIndex, destination, destPileNumber);
    }

  }

  //-----------------------------Abstracted Methods-------------------------------------------------
  //Abstracted method to support cascadeToCascade and openToCasc methods
  private void abstractMoveToCasc(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber) {
    if (SUIT.getColorSuit(lastDestinationCard(destination, destPileNumber).getSuit())
        .equals(SUIT.getColorSuit(lastSourceCard(source, pileNumber).getSuit()))) {
      throw new IllegalArgumentException("Invalid move: colors are the same");
    }
    if (getCardValue(destination, destPileNumber, lastCardIndex(destination, destPileNumber))
        !=
        getCardValue(source, pileNumber, cardIndex) + 1) {
      throw new IllegalArgumentException("Invalid move");
    }
  }

  //--------------------Methods to support moves from One Pile to Another---------------------------
  //Moves from a Cascade pile to another Cascade pile.

  /**
   * Moves from a Cascade pile to another Cascade pile.
   *
   * @param source         pile we remove from.
   * @param pileNumber     the pileNumber counting from 0.
   * @param cardIndex      the cardIndex.
   * @param destination    pile we add to.
   * @param destPileNumber the pileNumber we a new Card add to, counting from 0.
   */
  private void cascadeToCascade(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber) {
    abstractMoveToCasc(source, pileNumber, cardIndex, destination, destPileNumber);

    //add to the destination pile THEN remove from source pile.
    cascadePile.get(destPileNumber).add(getCascadeCardAt(pileNumber, cardIndex));
    cascadePile.get(pileNumber).remove(getCascadeCardAt(pileNumber, cardIndex));
  }

  // Moves from source pile to Open Pile.

  /**
   * Moves from a Cascade Pile to an Open pile.
   *
   * @param source         pile we remove from.
   * @param pileNumber     the pileNumber counting from 0.
   * @param cardIndex      the cardIndex.
   * @param destination    pile we add to.
   * @param destPileNumber the pileNumber we a new Card add to, counting from 0.
   * @throws IllegalArgumentException when the open pile is not empty.
   */
  private void cascadeToOpen(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber) {
    if (destination == PileType.OPEN && getNumCardsInOpenPile(destPileNumber) == 0) {
      openPile.set(destPileNumber, lastSourceCard(source, pileNumber));

      cascadePile.get(pileNumber).remove(getCascadeCardAt(pileNumber, cardIndex));
    } else {
      throw new IllegalArgumentException("The destination open pile is not empty!");
    }
  }

  //Move from Open to Open

  /**
   * Moves from an Open Pile to another Open Pile.
   *
   * @param source         pile we remove from.
   * @param pileNumber     the pileNumber counting from 0.
   * @param cardIndex      the cardIndex.
   * @param destination    pile we add to.
   * @param destPileNumber the pileNumber we a new Card add to, counting from 0.
   */
  private void openToOpen(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber) {
    if (destination == PileType.OPEN && getNumCardsInOpenPile(destPileNumber) == 0) {
      openPile.set(destPileNumber, lastSourceCard(source, pileNumber));
      //we don't use the remove method on the open pile because the size will decrease from 4.
      openPile.set(pileNumber, null);
    }
  }

  /**
   * Moves from Open to Foundation pile.
   *
   * @param source         pile we remove from.
   * @param pileNumber     the pileNumber counting from 0.
   * @param cardIndex      the cardIndex.
   * @param destination    pile we add to.
   * @param destPileNumber the pileNumber we a new Card add to, counting from 0.
   */
  private void openToFound(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber) {
    if (foundationPile.get(destPileNumber).size() == 0) {
      //if it is not Ace
      if (getCardValue(source, pileNumber, cardIndex)
          != 1) {
        throw new IllegalArgumentException(
            "Invalid move: Ace has to be first on a foundation pile.");
      }
      //If it is an Ace; remove from sourcePile, add to destination
      else {
        foundationPile.get(destPileNumber).add(openPile.get(pileNumber));
        openPile.set(pileNumber, null);
      }
    } else if (getSourceSuit(source, pileNumber, cardIndex)
        ==
        getDestinationSuit(destination, pileNumber, cardIndex)) {
      if (getCardValue(source, pileNumber, cardIndex) !=
          1 + getCardValue(destination, destPileNumber,
              lastCardIndex(destination, destPileNumber))) {
        throw new IllegalArgumentException("Source value should be one greater than dest.");
      }
      foundationPile.get(destPileNumber).add(openPile.get(pileNumber));
      openPile.set(pileNumber, null);
    } else {
      throw new IllegalArgumentException("Suits don't match");
    }
  }

  /**
   * Moves from Cascade to Foundation Pile.
   *
   * @param source         pile we remove from.
   * @param pileNumber     the pileNumber counting from 0.
   * @param cardIndex      the cardIndex.
   * @param destination    pile we add to.
   * @param destPileNumber the pileNumber we a new Card add to, counting from 0.
   */
  private void casToFound(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber) {
    if (foundationPile.get(destPileNumber).size() == 0) {
      //if it is not Ace
      if (getCardValue(source, pileNumber, cardIndex)
          != 1) {
        throw new IllegalArgumentException("Invalid move.");
      } else {
        foundationPile.get(destPileNumber).add(cascadePile.get(pileNumber).get(cardIndex));
        cascadePile.get(pileNumber).remove(cardIndex);
      }
    } else if (getSourceSuit(source, pileNumber, cardIndex)
        ==
        getDestinationSuit(destination, destPileNumber, cardIndex)) {
      if (getCardValue(source, pileNumber, cardIndex)
          !=
          1 + getCardValue(destination, destPileNumber,
              lastCardIndex(destination, destPileNumber))) {
        throw new IllegalArgumentException("Source value should be one greater than dest.");
      }
      foundationPile.get(destPileNumber).add(cascadePile.get(pileNumber).get(cardIndex));
      cascadePile.get(pileNumber).remove(cardIndex);
    } else {
      throw new IllegalArgumentException("Suits don't match");
    }

  }

  /**
   * Moves from Open to Cascade Pile.
   *
   * @param source         pile we remove from.
   * @param pileNumber     the pileNumber counting from 0.
   * @param cardIndex      the cardIndex.
   * @param destination    pile we add to.
   * @param destPileNumber the pileNumber we a new Card add to, counting from 0.
   */
  //Moves from a Cascade pile to another Cascade pile.
  private void openToCasc(PileType source, int pileNumber, int cardIndex,
      PileType destination, int destPileNumber)
      throws IllegalArgumentException, IllegalStateException {
    abstractMoveToCasc(source, pileNumber, cardIndex, destination, destPileNumber);

    //add to the destination pile THEN remove from source pile.
    cascadePile.get(destPileNumber).add(getOpenCardAt(pileNumber));
    openPile.set(pileNumber, null);
  }

  //Retrieves the Card of the last index of the source
  private Card lastSourceCard(PileType source, int pileNumber) {
    switch (source) {
      case OPEN:
        return getOpenCardAt(pileNumber);

      case CASCADE:
        return getCascadeCardAt(pileNumber, lastCardIndex(source, pileNumber));

      default:
        throw new IllegalArgumentException("Cannot remove a foundation card.");
    }
  }

  //Retrieves the index of the last card in an PileType
  // abstracted for both lastSourceCardIndex and lastDestinationCardIndex
  private int lastCardIndex(PileType pileTypeSOrD,
      int pileNumber) {
    switch (pileTypeSOrD) {
      case CASCADE:
        return cascadePile.get(pileNumber).size() - 1;

      case FOUNDATION:
        return foundationPile.get(pileNumber).size() - 1;

      case OPEN:
        return getNumCardsInOpenPile(pileNumber) - 1;

      default:
        throw new IllegalArgumentException("Invalid destination or source pile number.");
    }
  }

  //Retrieves the last destination card in a PileType
  private Card lastDestinationCard(PileType destination,
      int destPileNumber) {
    switch (destination) {
      case CASCADE:
        return getCascadeCardAt(destPileNumber, lastCardIndex(destination, destPileNumber));

      case OPEN:
        return getOpenCardAt(destPileNumber);

      case FOUNDATION:
        return getFoundationCardAt(destPileNumber, lastCardIndex(destination, destPileNumber));

      default:
        throw new IllegalArgumentException("Invalid destination pile number.");
    }
  }

  //determine the Suit of the source card
  private SUIT getSourceSuit(PileType source, int pileNumber, int cardIndex) {
    switch (source) {
      case OPEN:
        return openPile.get(pileNumber).getSuit();

      case CASCADE:
        return cascadePile.get(pileNumber).get(cardIndex).getSuit();
      default:
        throw new IllegalArgumentException("The source can only be Open or Cascade");
    }
  }

  //determine the Value of the card which could be from the source or destination.
  private int getCardValue(PileType sorD, int pileNumber, int cardIndex) {
    switch (sorD) {
      case OPEN:
        return openPile.get(pileNumber).getValue();

      case CASCADE:
        return cascadePile.get(pileNumber).get(lastCardIndex(PileType.CASCADE, pileNumber))
            .getValue();

      case FOUNDATION:
        return foundationPile.get(pileNumber).get(lastCardIndex(PileType.FOUNDATION, pileNumber))
            .getValue();
      default:
        throw new IllegalArgumentException("The source can only be Open or Cascade");
    }
  }

  //gets the destination vale
  private SUIT getDestinationSuit(PileType destination,
      int destPileNumber, int cardIndex) {
    switch (destination) {
      case OPEN:
        return getOpenCardAt(destPileNumber).getSuit();

      case CASCADE:
        return getCascadeCardAt(destPileNumber, lastCardIndex(PileType.CASCADE, destPileNumber))
            .getSuit();

      case FOUNDATION:
        return getFoundationCardAt(destPileNumber,
            lastCardIndex(PileType.FOUNDATION, destPileNumber)).getSuit();

      default:
        throw new IllegalArgumentException("Invalid destination type.");
    }
  }


  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasGameStarted) {
      throw new IllegalStateException("The game has not started.");
    }
    if (index < 0 || index > numFoundationPiles - 1) {
      throw new IllegalArgumentException("The index is invalid.");
    }
    return foundationPile.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (!hasGameStarted) {
      return -1;
    }
    return numCascadePiles;
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasGameStarted) {
      throw new IllegalStateException("The game has not started.");
    }
    if (index < 0 || index > numCascadePiles - 1) {
      throw new IllegalArgumentException("The index if invalid.");
    }
    return cascadePile.get(index).size();
  }

  @Override
  //each pile in openPiles can either have one card or none.
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (index < 0) {
      throw new IllegalArgumentException("The index is less than zero!");
    }
    if (!hasGameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (index > numOpenPiles - 1) {
      throw new IllegalArgumentException("Index is too high.");
    }
    if (openPile.get(index) != null) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public int getNumOpenPiles() {
    if (!hasGameStarted) {
      return -1;
    } else {
      return openPile.size();
    }
  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasGameStarted) {
      throw new IllegalStateException("The game has not started.");
    }
    if (pileIndex < 0 || cardIndex < 0) {
      throw new IllegalArgumentException("The pile or card index cannot be negative.");
    }
    if (cardIndex > getNumCardsInFoundationPile(pileIndex) - 1) {
      throw new IllegalArgumentException("Card index is too high.");
    }
    if (pileIndex > numFoundationPiles - 1) {
      throw new IllegalArgumentException("The pile index is too high");
    }
    if (getNumCardsInFoundationPile(pileIndex) == 0) {
      throw new IllegalArgumentException("Foundation pile is empty.");
    } else {
      return foundationPile.get(pileIndex).get(cardIndex);
    }
  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasGameStarted) {
      throw new IllegalStateException("The game has not started.");
    }
    if (pileIndex < 0 || cardIndex < 0) {
      throw new IllegalArgumentException("The pile or card index cannot be negative.");
    }

    if (cardIndex > getNumCardsInCascadePile(pileIndex) - 1) {
      throw new IllegalArgumentException("Card index is too high.");
    }
    if (getNumCardsInCascadePile(pileIndex) == 0) {
      throw new IllegalArgumentException("Cascade pile is empty.");
    }
    if (pileIndex > numCascadePiles - 1) {
      throw new IllegalArgumentException("The pile index is invalid.");
    } else {
      return cascadePile.get(pileIndex).get(cardIndex);
    }
  }

  @Override
  public Card getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    if (pileIndex < 0) {
      throw new IllegalArgumentException("Pile index cannot be less than 0");
    }
    if (getNumCardsInOpenPile(pileIndex) == 0) {
      return null;
    }
    if (!hasGameStarted) {
      throw new IllegalStateException("The game has not started.");
    }
    if (pileIndex > numOpenPiles - 1) {
      throw new IllegalArgumentException("The pile index is invalid.");
    } else {
      return openPile.get(pileIndex);
    }
  }

  //if the deck is shuffled
  private void isDeckShuffled() {
    if (isShuffled) {
      Collections.shuffle(deck);
    } else {
      //do nothing
    }
  }
}
