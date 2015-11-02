package net.peakgames.pisti.model.impl;

import org.junit.Assert;
import org.junit.Test;


public class DeckTest {

	@Test
	public void shuffleTest() {
		Deck deck = new Deck();
		deck.shuffle();
		Assert.assertTrue(deck.cardLeft() != 0);
	};
	
	@Test
	public void dealCardTest() {
		Deck deck = new Deck();
		deck.shuffle();
		int size = deck.cardLeft();
		deck.dealCard();
		Assert.assertEquals(size - 1, deck.cardLeft());
	};

}
