package net.peakgames.pisti.model.impl;

/**
 * @author ozgur.ozbil
 */
public class Card {
	private int rank;
	private Suit suit;

	public Card() {
		super();
	}

	public Card(int rank, Suit suit) {
		super();
		this.rank = rank;
		this.suit = suit;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		return "Suit: " + suit + " Rank: " + rank;
	}

	public enum Suit {
		HEART(0), DIAMOND(1), SPADE(2), CLUB(3);
		private final int id;

		Suit(int id) {
			this.id = id;
		}

		public int getValue() {
			return id;
		}
	}
}
