package net.peakgames.pisti.bot;

import java.util.List;
import java.util.Stack;

import net.peakgames.pisti.model.impl.Card;

public interface IBot {
	/**
	 * Player bot plays its turn with respect to the pile on the table given as
	 * parameter.
	 * 
	 * @author ozgur.ozbil
	 * @param pileCards
	 */
	public Card play(Stack<Card> pileCards);

	/**
	 * Player bot gets a card given as parameter and adds it to its hand.
	 * 
	 * @author ozgur.ozbil
	 * @param card
	 */
	public void getCard(Card card);
	
	/**
	 * Returns the number of cards player bot has on hand.
	 * 
	 * @author ozgur.ozbil
	 * @return
	 */
	public int getHandedCardCount();

	/**
	 * Returns cards gained from rank matching and pistis.
	 * 
	 * @author ozgur.ozbil
	 * @return
	 */
	public List<Card> getCollectedCards();

	/**
	 * Adds given cards to cards gained from rank matching and pistis.
	 * 
	 * @author ozgur.ozbil
	 * @param cards
	 */
	public void addAllCollectedCards(List<Card> cards);

	/**
	 * Returns cards gained by pistis.
	 * 
	 * @author ozgur.ozbil
	 * @return
	 */
	public List<Card> getPistiCards();

	/**
	 * Returns total score player bot gained at the end of a single game.
	 * 
	 * @author ozgur.ozbil
	 * @return
	 */
	public int getScore();

	/**
	 * Adds additional to total score player bot gained at the end of a single game.
	 * 
	 * @author ozgur.ozbil
	 * @param point
	 */
	public void addScore(int point);
	
	/**
	 * Gets already played cards.
	 * 
	 * @author ozgur.ozbil
	 * @param card
	 */
	public void getPlayedCard(Card card);
	
	/**
	 * Formats and prepares player bot to a new game.
	 * 
	 * @author ozgur.ozbil
	 */
	public void getReadyForNewGame();
}
