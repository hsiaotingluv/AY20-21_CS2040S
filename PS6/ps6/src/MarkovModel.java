import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private final Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	private final int order;
	private final long seed;
	private final HashMap<String, ArrayList<Integer>> table = new HashMap<>();

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.seed = seed;

		// Initialize the random number generator
		this.generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		for (int i = 0; i <= text.length() - order - 1; i++) {
			String subtext = text.substring(i, i + order);
			char nextChar = text.charAt(i + order);
			int ascii = (int) nextChar;

			if (!table.containsKey(subtext)) {
				ArrayList<Integer> arrayValue = new ArrayList<Integer>(256);
				for (int j = 0; j < 256; j++) {
					arrayValue.add(0);
				}
				arrayValue.set(ascii, 1);
				table.put(subtext, arrayValue);
			} else {
				ArrayList<Integer> arrayValue = table.get(subtext);
				int newValue = arrayValue.get(ascii) + 1;
				arrayValue.set(ascii, newValue);
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) throws IllegalArgumentException {
		if (kgram.length() != this.order) {
			throw new IllegalArgumentException("Invalid argument. " +
					"Enter an input with length equals to the order " +
					"of the Markov Model.");
		} else if (!table.containsKey(kgram)) {
			return 0;
		}

		int frequency = 0;
		ArrayList<Integer> arrayValue = table.get(kgram);
		for (int i : arrayValue) {
			frequency += i;
		}
		return frequency;

	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) throws IllegalArgumentException {
		if (kgram.length() != this.order) {
			throw new IllegalArgumentException("Invalid argument. " +
					"Enter an input with length equals to the order " +
					"of the Markov Model.");
		} else if (!table.containsKey(kgram)) {
			return 0;
		}

		ArrayList<Integer> arrayValue = table.get(kgram);
		return arrayValue.get((int) c);
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (table.containsKey(kgram)) {
			int count = 0;
			int index = -1;
			int randomValue = this.generator.nextInt(this.getFrequency(kgram)) + 1;

			ArrayList<Integer> arrayValues = table.get(kgram);
			for (int i = 0; i < arrayValues.size(); i++) {
				count += arrayValues.get(i);
				if (count >= randomValue) {
					index = i;
					break;
				}
			}
			return (char) index;

		} else {
			return NOCHARACTER;
		}
	}
}
