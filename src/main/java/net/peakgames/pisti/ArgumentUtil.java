package net.peakgames.pisti;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import net.peakgames.pisti.bot.IBot;
import net.peakgames.pisti.exception.ArgumentQuitException;

public class ArgumentUtil {
	public enum ArgumentType {
		CONCURRENTCOUNT, TOTALCOUNT, PLAYER1, PLAYER2, PLAYER3, PLAYER4;
	}

	private static final String ARGUMENTS_COMMAND_QUIT = "q";

	/**
	 * This method checks arguments whether they are suitable for game to run.
	 * 
	 * @author ozgur.ozbil
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Map<ArgumentType, String> areArgsCorrect(String args[]) throws Exception {
		Map<ArgumentType, String> properArguments = new Hashtable<ArgumentType, String>();
		Scanner scanner = new Scanner(System.in);
		try {
			// Arguments count control
			while (args.length != 6) {
				System.err.println("Argument count should be 6. Please enter again:");
				args = scanner.nextLine().trim().split(" ");
				if (args[0].equals(ARGUMENTS_COMMAND_QUIT)) {
					throw new ArgumentQuitException();
				}
			}
			// Concurrent game count argument control
			properArguments.put(ArgumentType.CONCURRENTCOUNT, getProperConcurrentCountArgument(scanner, args[0]));
			// Total game count argument control
			properArguments.put(ArgumentType.TOTALCOUNT, getProperTotalCountArgument(scanner, args[1]));
			// First player bot control
			properArguments.put(ArgumentType.PLAYER1, getProperPlayerArgument(scanner, args[2], ArgumentType.PLAYER1));
			// Second player bot control
			properArguments.put(ArgumentType.PLAYER2, getProperPlayerArgument(scanner, args[3], ArgumentType.PLAYER2));
			// Third player bot control
			properArguments.put(ArgumentType.PLAYER3, getProperPlayerArgument(scanner, args[4], ArgumentType.PLAYER3));
			// Fourth player bot control
			properArguments.put(ArgumentType.PLAYER4, getProperPlayerArgument(scanner, args[5], ArgumentType.PLAYER4));
			
		} catch (ArgumentQuitException e) {
			// Throws ArgumentQuitException to end the operation in the main
			// process as requested by entering Quit command.
			throw new ArgumentQuitException();
		} catch (Exception e) {
			throw new Exception();
		} finally {
			scanner.close();
		}

		return properArguments;
	}
	
	private static String getProperPlayerArgument(Scanner scanner, String argument, ArgumentType type) throws ArgumentQuitException {
		String playerBot = argument;
		while (!playerBotExistenceControl(playerBot)) {
			System.err.println(type + " player bot name is incorrect. Please enter again: ");
			playerBot = scanner.nextLine().trim();
			if (playerBot.equals(ARGUMENTS_COMMAND_QUIT)) {
				throw new ArgumentQuitException();
			}
			argument = playerBot;
		}
		return playerBot;
	}
	
	private static String getProperTotalCountArgument(Scanner scanner, String argument) throws ArgumentQuitException {
		String totalGameCountControl = argument;
		while (!gameCountControl(totalGameCountControl)) {
			System.err.println("Total game count parameter is incorrect. Please enter again: ");
			totalGameCountControl = scanner.nextLine().trim();
			if (totalGameCountControl.equalsIgnoreCase(ARGUMENTS_COMMAND_QUIT)) {
				throw new ArgumentQuitException();
			}
		}
		return totalGameCountControl;
	}
	
	private static String getProperConcurrentCountArgument(Scanner scanner, String argument) throws ArgumentQuitException {
		String concurrentGameCount = argument;
		while (!gameCountControl(concurrentGameCount)) {
			System.err.println("Concurrent game count parameter is incorrect. Please enter again: ");
			concurrentGameCount = scanner.nextLine().trim();
			if (concurrentGameCount.equals(ARGUMENTS_COMMAND_QUIT)) {
				throw new ArgumentQuitException();
			}
		}
		return concurrentGameCount;
	}

	/**
	 * Controls if given value is a proper value.
	 * 
	 * @author ozgur.ozbil
	 * @param amount
	 * @return
	 */
	public static boolean gameCountControl(String amount) {
		// Parameter must be integer and greater than 0.
		try {
			int concurrentGameCount = Integer.parseInt(amount);
			if (concurrentGameCount < 1) {
				throw new Exception();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks any class found in application with given className. <br>
	 * Returns true if such class is found; otherwise returns false.
	 * 
	 * @author ozgur.ozbil
	 * @param arg
	 * @return
	 */
	public static boolean playerBotExistenceControl(String className) {
		// Controls if given class paths are contained by the project
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static IBot getBotInstance(String classPath) {
		try {
			return (IBot) Class.forName(classPath).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
