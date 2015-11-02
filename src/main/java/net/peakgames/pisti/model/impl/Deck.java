package net.peakgames.pisti.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import net.peakgames.pisti.model.IDeck;
import net.peakgames.pisti.model.impl.Card.Suit;

public class Deck implements IDeck {
	private List<Card> orderedDeck = new ArrayList<Card>();
	private Stack<Card> deck = new Stack<Card>();
	
	public Deck() {
		if (!(orderedDeck.size() > 0)) {
			for (int i = 0; i < 52; i++) {
				int suitIndex = i / 13;
				int rankIndex = i % 13;
				orderedDeck.add(new Card(rankIndex + 1, Suit.values()[suitIndex]));
			}
		}
	}

	@Override
	public void shuffle() {
		List<Card> shuffleTemp = new ArrayList<Card>(orderedDeck);
		Collections.shuffle(shuffleTemp);
		for (int j = shuffleTemp.size() - 1; j > -1; j--) {
			deck.push(shuffleTemp.get(j));
		}
	}

	@Override
	public Card dealCard() {
		return deck.pop();
	}

	@Override
	public int cardLeft() {
		return deck.size();
	}

}
