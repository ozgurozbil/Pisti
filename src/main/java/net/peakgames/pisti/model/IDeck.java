package net.peakgames.pisti.model;

import net.peakgames.pisti.model.impl.Card;

public interface IDeck {

	/**
	 * Put all the used cards back into the deck, and shuffle it into a random
	 * order.
	 */
	public void shuffle();

	/**
	 * Returns size of deck
	 * 
	 */
	public int cardLeft();
	
	/**
	 * Deals one card from the deck and returns it.
	 * 
	 */
	public Card dealCard();
}
