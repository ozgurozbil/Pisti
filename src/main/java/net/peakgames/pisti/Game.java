package net.peakgames.pisti;

import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import net.peakgames.pisti.ArgumentUtil.ArgumentTypes;
import net.peakgames.pisti.bot.IBot;
import net.peakgames.pisti.model.impl.Card;
import net.peakgames.pisti.model.impl.Deck;

public class Game implements Runnable {
	private Map<String, Integer> winCount = new Hashtable<String, Integer>();
	private IBot players[] = new IBot[4];
	private final int adjacentGames;

	public Game(Map<ArgumentTypes, String> arguments) {
		super();
		this.adjacentGames = Integer.parseInt(arguments.get(ArgumentTypes.TOTALCOUNT))
				/ Integer.parseInt(arguments.get(ArgumentTypes.CONCURRENTCOUNT));
		this.players[0] = ArgumentUtil.getBotInstance(arguments.get(ArgumentTypes.PLAYER2));
		this.players[1] = ArgumentUtil.getBotInstance(arguments.get(ArgumentTypes.PLAYER3));
		this.players[2] = ArgumentUtil.getBotInstance(arguments.get(ArgumentTypes.PLAYER4));
		this.players[3] = ArgumentUtil.getBotInstance(arguments.get(ArgumentTypes.PLAYER1));
	}

	@Override
	public void run() {
		for (int i = 0; i < adjacentGames; i++) {
			Stack<Card> pileCards = new Stack<Card>();

			Deck deck = new Deck();
			deck.shuffle();

			// First four cards are put on the table
			for (int j = 0; j < 4; j++) {
				sendPlayedCardInfoToPlayers(pileCards.push(deck.dealCard()));
			}
			while (deck.cardLeft() != 0) {
				// Dealer (Player1) deals the cards
				while (!(players[3].getHandedCardCount() == 4)) {
					for (int j = 0; j < 4; j++) {
						players[j].getCard(deck.dealCard());
					}
				}

				while (!(players[3].getHandedCardCount() == 0)) {
					for (int j = 0; j < 4; j++) {
						sendPlayedCardInfoToPlayers(players[j].play(pileCards));
					}
				}
			}
			// Give remaining pile to last taker of pile
			if (!pileCards.isEmpty()) {
				switch ((pileCards.size() % 4)) {
				case 1:
					players[2].addAllCollectedCards(pileCards);
					pileCards.clear();
					break;
				case 2:
					players[1].addAllCollectedCards(pileCards);
					pileCards.clear();
					break;
				case 3:
					players[0].addAllCollectedCards(pileCards);
					pileCards.clear();
					break;
				case 0:
					players[3].addAllCollectedCards(pileCards);
					pileCards.clear();
					break;
				}
			}
			// Owner of majority of cards gets 3 more points
			findMajorityOwner(players);
			// Winner information is held for
			findGameWinner(players);
			for (int j = 0; j < 4; j++) {
				players[j].getReadyForNewGame();
			}
		}
	}

	private void findMajorityOwner(IBot[] players) {
		int majorityIndex = 0;
		for (int i = 1; i < players.length; i++) {
			if (players[i].getCollectedCards().size() > players[i - 1].getCollectedCards().size()) {
				majorityIndex = i;
			}
		}
		players[majorityIndex].addScore(3);
	}

	private void findGameWinner(IBot[] players) {
		int winnerIndex = 0;
		for (int i = 1; i < players.length; i++) {
			if (players[i].getScore() > players[winnerIndex].getScore()) {
				winnerIndex = i;
			}
		}
		if (winCount.get(players[winnerIndex].getClass().getName()) == null) {
			winCount.put(players[winnerIndex].getClass().getName(), 1);
		} else {
			winCount.put(players[winnerIndex].getClass().getName(),
					winCount.get(players[winnerIndex].getClass().getName()) + 1);
		}
	}

	public void sendPlayedCardInfoToPlayers(Card card) {
		for (int i = 0; i < players.length; i++) {
			players[i].getPlayedCard(card);
		}
	}

	public Map<String, Integer> getWinCount() {
		return winCount;
	}
}
