package net.peakgames.pisti;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.peakgames.pisti.bot.BotUtil;
import net.peakgames.pisti.bot.IBot;
import net.peakgames.pisti.bot.impl.DummyBot;
import net.peakgames.pisti.bot.impl.SmartBot;
import net.peakgames.pisti.model.impl.Card;
import net.peakgames.pisti.model.impl.Card.Suit;

public class UtilTest {

	@Test
	public void gameCountControlTest() {
		Assert.assertFalse(ArgumentUtil.gameCountControl("a"));
		Assert.assertFalse(ArgumentUtil.gameCountControl("-1"));
		Assert.assertTrue(ArgumentUtil.gameCountControl("1"));
	}
	
	@Test
	public void getBotInstanceTest() {
		Assert.assertTrue(ArgumentUtil.getBotInstance("net.peakgames.pisti.bot.impl.DummyBot") instanceof DummyBot);
		Assert.assertTrue(ArgumentUtil.getBotInstance("net.peakgames.pisti.bot.impl.DummyBot") instanceof IBot);
		Assert.assertTrue(ArgumentUtil.getBotInstance("net.peakgames.pisti.bot.impl.SmartBot") instanceof IBot);
		Assert.assertTrue(ArgumentUtil.getBotInstance("net.peakgames.pisti.bot.impl.SmartBot") instanceof SmartBot);
	}
	
	@Test
	public void playerBotExistenceControlTest() {
		Assert.assertTrue(ArgumentUtil.playerBotExistenceControl("net.peakgames.pisti.bot.impl.SmartBot"));
		Assert.assertFalse(ArgumentUtil.playerBotExistenceControl("net.peakgames.pisti.bot.impl.SmartBoth"));
	}

	@Test
	public void calculatePointsTest() {
		List<Card> collectedCards = new ArrayList<Card>();
		collectedCards.add(new Card(BotUtil.TWO, Suit.SPADE));
		collectedCards.add(new Card(BotUtil.TEN, Suit.DIAMOND));

		List<Card> pistiCards = new ArrayList<Card>();
		pistiCards.add(new Card(BotUtil.JACK, Suit.DIAMOND));
		pistiCards.add(new Card(BotUtil.JACK, Suit.HEART));
		pistiCards.add(new Card(BotUtil.ACE, Suit.DIAMOND));
		pistiCards.add(new Card(BotUtil.ACE, Suit.HEART));

		Assert.assertEquals(55, BotUtil.calculatePoints(collectedCards, pistiCards));
	}

	@Test
	public void cardSortTest() {
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(BotUtil.ACE, Suit.DIAMOND));
		cards.add(new Card(BotUtil.TEN, Suit.DIAMOND));
		cards.add(new Card(BotUtil.JACK, Suit.DIAMOND));
		cards.add(new Card(BotUtil.TWO, Suit.DIAMOND));
		BotUtil.cardSort(cards);
		Assert.assertTrue(cards.get(cards.size() - 1).getRank() == BotUtil.JACK);
	}
}
