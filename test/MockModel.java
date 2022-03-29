import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import java.io.IOException;
import java.util.List;

/**
 * Represents a mock model class.
 */
public class MockModel implements FreecellModel {

  Appendable ap;
  Readable rd;

  public MockModel(Appendable ap) {
    this.ap = ap;
  }

  public MockModel(Readable rd) {
    this.rd = rd;
  }

  @Override
  public List getDeck() {
    return null;
  }

  @Override
  public void startGame(List deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    ///empty

  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    try {
      ap.append(source.toString()).append(Integer.toString(pileNumber))
          .append(Integer.toString(cardIndex))
          .append(destination.toString()).append(Integer.toString(destPileNumber));
    } catch (IOException e) {
      throw new IllegalStateException("Not able to append.");
    }
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCascadePiles() {
    return 0;
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumOpenPiles() {
    return 0;
  }

  @Override
  public Object getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public Object getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public Object getOpenCardAt(int pileIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }
}
