package net.peakgames.pisti.bot.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.peakgames.pisti.bot.BotUtil;
import net.peakgames.pisti.bot.IBot;
import net.peakgames.pisti.model.impl.Card;

public class DummyBot implements IBot {
	private List<Card> handedCards = new ArrayList<Card>(4);
	private List<Card> collectedCards = new ArrayList<Card>();
	private List<Card> pistiCards = new ArrayList<Card>();
	private int score;

	@Override
	public Card play(Stack<Card> pileCards) {
		boolean score = false;
		// if there is card(s) on the top of the table
		if (!pileCards.isEmpty()) {
			for (int i = 0; i < handedCards.size() && !score; i++) {
				// Bacause cards are sorted, if this card's rank is greater than
				// top card's rank, there is no matching card on hand.
				if (handedCards.get(i).getRank() > pileCards.peek().getRank()) {
					break;
				} else if (handedCards.get(i).getRank() == pileCards.peek().getRank()) {
					return playMatchingCard(pileCards, handedCards.get(i));
				}
			}
			// Check for if there is any Jack in the hand
			for (int i = 0; i < handedCards.size(); i++) {
				if (handedCards.get(i).getRank() > BotUtil.JACK) {
					break;
				} else if (handedCards.get(i).getRank() == BotUtil.JACK) {
					return playJackCard(pileCards, handedCards.get(i));
				}
			}
		}
		return playNonWiningHand(pileCards);
	}

	/**
	 * Plays matching card on hand with one on the top of the table. Player gets
	 * all the pile on table.
	 * 
	 * @author ozgur.ozbil
	 * @param pileCards
	 * @param card
	 * @return
	 */
	private Card playMatchingCard(Stack<Card> pileCards, Card card) {
		// If there is only one card in the pile then it means pisti
		if (pileCards.size() == 1) {
			pileCards.push(card);
			pistiCards.add(pileCards.pop());
			pistiCards.add(pileCards.pop());
			collectedCards.add(pistiCards.get(pistiCards.size() - 1));
			collectedCards.add(pistiCards.get(pistiCards.size() - 2));
		} else {
			// There are more then one card in the pile
			// Card matching on hand and all pile is added to
			// collectedCards
			pileCards.push(card);
			collectedCards.addAll(pileCards);
			pileCards.clear();
		}
		// Card on hand is also removed
		Card usedCard = card;
		handedCards.remove(card);
		return usedCard;
	}

	/**
	 * Plays a Jack on hand. Player gets all the pile on table.
	 * 
	 * @author ozgur.ozbil
	 * @param pileCards
	 * @param card
	 * @return
	 */
	private Card playJackCard(Stack<Card> pileCards, Card card) {
		// Jack on hand and all pile is added to
		// collectedCards (There are more then one card in the
		// pile)
		pileCards.push(card);
		collectedCards.addAll(pileCards);
		pileCards.clear();
		// Card on hand is also removed
		Card usedCard = card;
		handedCards.remove(card);
		return usedCard;
	}

	/**
	 * Plays smallest card on hand. Players get no cards.
	 * 
	 * @author ozgur.ozbil
	 * @param pileCards
	 * @return
	 */
	private Card playNonWiningHand(Stack<Card> pileCards) {
		// If hand doesn't have any mathing cards or Jack or there is no
		// card on the table
		// Smallest ranked card is added to pile
		pileCards.push(handedCards.get(0));
		// Card on hand is also removed
		Card usedCard = handedCards.get(0);
		handedCards.remove(handedCards.get(0));
		return usedCard;
	}

	private void sortHandedCards() {
		BotUtil.cardSort(handedCards);
	}

	@Override
	public void getCard(Card card) {
		this.handedCards.add(card);
		if (handedCards.size() == 4) {
			sortHandedCards();
		}
	}

	@Override
	public List<Card> getCollectedCards() {
		return collectedCards;
	}

	@Override
	public void addAllCollectedCards(List<Card> cards) {
		collectedCards.addAll(cards);
	}

	@Override
	public List<Card> getPistiCards() {
		return pistiCards;
	}

	@Override
	public int getScore() {
		score = BotUtil.calculatePoints(collectedCards, pistiCards);
		return this.score;
	}

	@Override
	public void addScore(int point) {
		this.score += point;
	}

	@Override
	public int getHandedCardCount() {
		return handedCards.size();
	}

	@Override
	public void getPlayedCard(Card card) {
	}

	@Override
	public void getReadyForNewGame() {
		this.handedCards.clear();
		this.collectedCards.clear();
		this.pistiCards.clear();
		this.score = 0;
	}
}
