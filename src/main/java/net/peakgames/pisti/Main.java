package net.peakgames.pisti;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.peakgames.pisti.ArgumentUtil.ArgumentTypes;
import net.peakgames.pisti.exception.ArgumentQuitException;

public class Main {
	public static void main(String[] args) {
		List<Game> games = new ArrayList<Game>();

		// Inputs are taken.
		Map<ArgumentTypes, String> arguments = getArguments(args);
		if (arguments == null) {
			return;
		}
		final int concurrentGameCount = Integer.parseInt(arguments.get(ArgumentTypes.CONCURRENTCOUNT));
		final int totalGameCount = Integer.parseInt(arguments.get(ArgumentTypes.TOTALCOUNT));

		// Game process begins.
		long start = System.nanoTime();

		// Thread pools are prepared for concurrent running.
		runConcurrentGames(concurrentGameCount, games, arguments);
		Map<String, Integer> winCount = new Hashtable<String, Integer>();

		for (int i = 0; i < games.size(); i++) {
			Iterator<Map.Entry<String, Integer>> entries = games.get(i).getWinCount().entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, Integer> entry = entries.next();
				if (winCount.get(entry.getKey()) == null) {
					winCount.put(entry.getKey(), entry.getValue());
				} else {
					winCount.put(entry.getKey(), winCount.get(entry.getKey()) + entry.getValue());
				}
			}
		}
		
		// Game process ends.
		long end = System.nanoTime();
		
		System.out.println("Total Games Played: " + totalGameCount);
		for (Map.Entry<String, Integer> entry : winCount.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue() + " wins");
		}
		// Output time process took in miliseconds
		System.out.println("Simulation Duration: " + (end - start) / 1000000 + "ms");
	}

	public static Map<ArgumentTypes, String> getArguments(String[] args) {
		try {
			return ArgumentUtil.areArgsCorrect(args);
		} catch (ArgumentQuitException e) {
			System.out.println("Game is terminated.");
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static void runConcurrentGames(int concurrentGameCount, List<Game> games,
			Map<ArgumentTypes, String> arguments) {
		ExecutorService taskExecutor = Executors.newFixedThreadPool(concurrentGameCount);
		for (int i = 0; i < concurrentGameCount; i++) {
			Game game = new Game(arguments);
			games.add(game);
			taskExecutor.execute(game);
		}
		taskExecutor.shutdown();
		try {
			taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.out.println("An error occured. Thread couldn't safely ended.");
		}
	}
}
