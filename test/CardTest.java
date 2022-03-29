import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.Card.SUIT;
import cs3500.freecell.model.hw02.Card.VALUE;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the toString method of the Card class.
 */
public class CardTest {

  Card twoDiamond;
  Card threeDiamond;
  Card tenHeart;
  Card jSpade;
  Card kHeart;

  @Test
  public void printJustValueSuit() {
    this.initData();
    assertEquals("2♦", twoDiamond.toString());
    assertEquals("3♦", threeDiamond.toString());
  }


  @Test
  public void testGetSuit() {
    assertEquals(SUIT.DIAMOND, twoDiamond.getSuit());
    assertEquals(SUIT.DIAMOND, threeDiamond.getSuit());
    assertEquals(SUIT.HEART, tenHeart.getSuit());
    assertEquals(SUIT.SPADE, jSpade.getSuit());
    assertEquals(SUIT.HEART, kHeart.getSuit());
  }

  @Before
  public void initData() {
    this.twoDiamond = new Card(VALUE.TWO, SUIT.DIAMOND);
    this.threeDiamond = new Card(VALUE.THREE, SUIT.DIAMOND);
    this.tenHeart = new Card(VALUE.TEN, SUIT.HEART);
    this.jSpade = new Card(VALUE.J, SUIT.SPADE);
    this.kHeart = new Card(VALUE.K, SUIT.HEART);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullCardValues() {
    new Card(null, null);
  }

}
