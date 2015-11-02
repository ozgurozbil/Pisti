package net.peakgames.pisti.bot;

import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.peakgames.pisti.bot.impl.DummyBot;
import net.peakgames.pisti.bot.impl.SmartBot;
import net.peakgames.pisti.model.impl.Card;
import net.peakgames.pisti.model.impl.Card.Suit;

public class IBotTest {
	public IBot dummyBotIns;
	public IBot smartBotIns;
	public Stack<Card> pileCards;

	@Before
	public void beginsWith() {
		this.dummyBotIns = new DummyBot();
		this.smartBotIns = new SmartBot();
		this.pileCards = new Stack<Card>();
		// Pile with some cards needed to be used
		pileCards.push(new Card(BotUtil.TWO, Suit.SPADE));
		pileCards.push(new Card(BotUtil.ACE, Suit.CLUB));

		dummyBotIns.getCard(new Card(BotUtil.ACE, Suit.HEART));
		dummyBotIns.getCard(new Card(BotUtil.TEN, Suit.HEART));

		smartBotIns.getCard(new Card(BotUtil.JACK, Suit.HEART));
		smartBotIns.getCard(new Card(BotUtil.TWO, Suit.HEART));
	}

	@Test
	public void dummyBotPlayTest() {
		// Bot plays card with rank ace and gets all what is in pile.
		Assert.assertEquals(BotUtil.ACE, Integer.valueOf(dummyBotIns.play(pileCards).getRank()));
		// Cards in pile and played are added to collectedCards.
		Assert.assertEquals(BotUtil.TWO, Integer.valueOf(dummyBotIns.getCollectedCards().get(0).getRank()));
		Assert.assertEquals(BotUtil.ACE, Integer.valueOf(dummyBotIns.getCollectedCards().get(1).getRank()));
		Assert.assertEquals(BotUtil.ACE, Integer.valueOf(dummyBotIns.getCollectedCards().get(2).getRank()));
		// No card is added to pistiCards.
		Assert.assertEquals(0, dummyBotIns.getPistiCards().size());
		// All cards in pile must be cleaned.
		Assert.assertEquals(0, pileCards.size());
		// A new card with rank ten is added.
		pileCards.push(new Card(BotUtil.TEN, Suit.DIAMOND));
		// Bot plays card with rank ten and makes pisti.
		Assert.assertEquals(BotUtil.TEN, Integer.valueOf(dummyBotIns.play(pileCards).getRank()));
		// Card in pile and played are added to collectedCards.
		Assert.assertEquals(BotUtil.TWO, Integer.valueOf(dummyBotIns.getCollectedCards().get(0).getRank()));
		Assert.assertEquals(BotUtil.ACE, Integer.valueOf(dummyBotIns.getCollectedCards().get(1).getRank()));
		Assert.assertEquals(BotUtil.ACE, Integer.valueOf(dummyBotIns.getCollectedCards().get(2).getRank()));
		Assert.assertEquals(BotUtil.TEN, Integer.valueOf(dummyBotIns.getCollectedCards().get(3).getRank()));
		Assert.assertEquals(BotUtil.TEN, Integer.valueOf(dummyBotIns.getCollectedCards().get(4).getRank()));
		// Card in pile and played are added to pistiCards.
		Assert.assertEquals(BotUtil.TEN, Integer.valueOf(dummyBotIns.getPistiCards().get(0).getRank()));
		Assert.assertEquals(BotUtil.TEN, Integer.valueOf(dummyBotIns.getPistiCards().get(1).getRank()));
		// All cards in pile must be cleaned.
		Assert.assertEquals(0, pileCards.size());
		// Score with 5 collected cards and a pisti pair should be equal to 27.
		Assert.assertEquals(27, dummyBotIns.getScore());
	}

	@Test
	public void smartBotPlayTest() {
		// Bot plays card with rank ace and gets all what is in pile.
		Assert.assertEquals(BotUtil.JACK, Integer.valueOf(smartBotIns.play(pileCards).getRank()));
		// Cards in pile and played are added to collectedCards.
		Assert.assertEquals(BotUtil.TWO, Integer.valueOf(smartBotIns.getCollectedCards().get(0).getRank()));
		Assert.assertEquals(BotUtil.ACE, Integer.valueOf(smartBotIns.getCollectedCards().get(1).getRank()));
		Assert.assertEquals(BotUtil.JACK, Integer.valueOf(smartBotIns.getCollectedCards().get(2).getRank()));
		// No card is added to pistiCards.
		Assert.assertEquals(0, smartBotIns.getPistiCards().size());
		// All cards in pile must be cleaned.
		Assert.assertEquals(0, pileCards.size());
		// A new card with rank ten is added.
		pileCards.push(new Card(BotUtil.TEN, Suit.DIAMOND));
		// Because there is no matching card or Bot Jack plays mostly seen rank. 
		Assert.assertEquals(BotUtil.TWO, Integer.valueOf(smartBotIns.play(pileCards).getRank()));
		// No cards added to collectedCards.
		Assert.assertEquals(BotUtil.TWO, Integer.valueOf(smartBotIns.getCollectedCards().get(0).getRank()));
		Assert.assertEquals(BotUtil.ACE, Integer.valueOf(smartBotIns.getCollectedCards().get(1).getRank()));
		Assert.assertEquals(BotUtil.JACK, Integer.valueOf(smartBotIns.getCollectedCards().get(2).getRank()));
		// No card is added to pistiCards.
		Assert.assertEquals(0, smartBotIns.getPistiCards().size());
		// A card is added to pile.
		Assert.assertEquals(2, pileCards.size());
		// Score with 5 collected cards and a pisti pair should be equal to 4.
		Assert.assertEquals(4, smartBotIns.getScore());
	}
}
