package net.peakgames.pisti.bot;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.peakgames.pisti.model.impl.Card;
import net.peakgames.pisti.model.impl.Card.Suit;

public class BotUtil {
	public static final Integer ACE = 1;
	public static final Integer TWO = 2;
	public static final Integer TEN = 10;
	public static final Integer JACK = 11;

	/**
	 * Calculates total score gained from collected cards and pistis by a single
	 * player bot.
	 * 
	 * @author ozgur.ozbil
	 * @param collectedCards
	 * @param pistiCards
	 * @return
	 */
	public static int calculatePoints(List<Card> collectedCards, List<Card> pistiCards) {
		int totalScore;
		// Pistis are counted. 10 points for each pisti
		totalScore = pistiCards.size() * 10;
		int jackCountInPistis = 0;
		for (int i = 0; i < pistiCards.size(); i++) {
			if (pistiCards.get(i).getRank() == BotUtil.JACK) {
				jackCountInPistis++;
			}
		}
		// Jack pistis are double points. 10 points more for each pisti consists
		// of pair of Jacks
		totalScore += (jackCountInPistis / 2) * 10;

		// Look for all cards gained
		for (int i = 0; i < collectedCards.size(); i++) {
			if (collectedCards.get(i).getRank() == BotUtil.JACK) {
				// 1 point for all Jacks
				totalScore++;
			} else if (collectedCards.get(i).getRank() == BotUtil.ACE) {
				// 1 point for all aces
				totalScore++;
			} else if (collectedCards.get(i).getRank() == BotUtil.TWO
					&& collectedCards.get(i).getSuit().equals(Suit.SPADE)) {
				// 2 points for 2 of spades
				totalScore += 2;
			} else if (collectedCards.get(i).getRank() == BotUtil.TEN
					&& collectedCards.get(i).getSuit().equals(Suit.DIAMOND)) {
				// 3 points for 10 of diamonds
				totalScore += 3;
			}
		}

		return totalScore;
	}

	/**
	 * Sorts given cards on hand ascendingly.
	 * 
	 * @author ozgur.ozbil
	 */
	public static void cardSort(List<Card> cards) {
		Comparator<Card> cardComparator = (Card card1, Card card2) -> Integer.valueOf(card1.getRank()).compareTo(card2.getRank());
		Collections.sort(cards, cardComparator);
	}
}
