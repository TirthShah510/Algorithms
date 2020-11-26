import java.io.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator {
	static HashMap<String, Integer> countMap = new HashMap<>();
	static HashMap<String, Integer> totalMap = new HashMap<String, Integer>();
	static TreeMap<String, Double> probabMap = new TreeMap<String, Double>();

	static int totalA = 0;
	static int totalC = 0;
	static int totalG = 0;
	static int totalT = 0;

	static double[][] probab_matrix = new double[4][4];

	static char[] characters = { 'A', 'C', 'G', 'T' };
	static double[] init_probab = new double[4];
	static ArrayList<Character> characterSet = new ArrayList<Character>();

	static char choosenChar;
	static StringBuilder generatedOutput = new StringBuilder();

	public static void learnChains(String sequence) {
		// A followed by *
		countMap.put("AT", sequence.split(Pattern.quote("AT"), -1).length - 1);
		countMap.put("AA", sequence.split(Pattern.quote("AA"), -1).length - 1);
		countMap.put("AG", sequence.split(Pattern.quote("AG"), -1).length - 1);
		countMap.put("AC", sequence.split(Pattern.quote("AC"), -1).length - 1);

		// C followed by *
		countMap.put("CT", sequence.split(Pattern.quote("CT"), -1).length - 1);
		countMap.put("CA", sequence.split(Pattern.quote("CA"), -1).length - 1);
		countMap.put("CG", sequence.split(Pattern.quote("CG"), -1).length - 1);
		countMap.put("CC", sequence.split(Pattern.quote("CC"), -1).length - 1);

		// G followed by *
		countMap.put("GT", sequence.split(Pattern.quote("GT"), -1).length - 1);
		countMap.put("GA", sequence.split(Pattern.quote("GA"), -1).length - 1);
		countMap.put("GG", sequence.split(Pattern.quote("GG"), -1).length - 1);
		countMap.put("GC", sequence.split(Pattern.quote("GC"), -1).length - 1);

		// T followed by *
		countMap.put("TT", sequence.split(Pattern.quote("TT"), -1).length - 1);
		countMap.put("TA", sequence.split(Pattern.quote("TA"), -1).length - 1);
		countMap.put("TG", sequence.split(Pattern.quote("TG"), -1).length - 1);
		countMap.put("TC", sequence.split(Pattern.quote("TC"), -1).length - 1);

		// Print the map.
		// System.out.println(countMap);

		for (Map.Entry<String, Integer> e : countMap.entrySet()) {
			if (e.getKey().startsWith("A")) {
				totalA += e.getValue(); // Let total[A] = count[AA] + count[AC] + count[AG] + count[AT]
				totalMap.put("A", totalA);
			} else if (e.getKey().startsWith("C")) {
				totalC += e.getValue();
				totalMap.put("C", totalC);
			} else if (e.getKey().startsWith("G")) {
				totalG += e.getValue();
				totalMap.put("G", totalG);
			} else if (e.getKey().startsWith("T")) {
				totalT += e.getValue();
				totalMap.put("T", totalT);
			}
		}
		// System.out.println(totalMap);

		for (String subString : countMap.keySet()) {
			if (subString.startsWith("A")) {
				if (subString.endsWith("A")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("A")));
				}
				if (subString.endsWith("C")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("A")));
				}
				if (subString.endsWith("G")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("A")));
				}
				if (subString.endsWith("T")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("A")));
				}
			} else if (subString.startsWith("C")) {
				if (subString.endsWith("A")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("C")));
				}
				if (subString.endsWith("C")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("C")));
				}
				if (subString.endsWith("G")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("C")));
				}
				if (subString.endsWith("T")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("C")));
				}
			} else if (subString.startsWith("G")) {
				if (subString.endsWith("A")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("G")));
				}
				if (subString.endsWith("C")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("G")));
				}
				if (subString.endsWith("G")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("G")));
				}
				if (subString.endsWith("T")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("G")));
				}
			} else if (subString.startsWith("T")) {
				if (subString.endsWith("A")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("T")));
				}
				if (subString.endsWith("C")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("T")));
				}
				if (subString.endsWith("G")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("T")));
				}
				if (subString.endsWith("T")) {
					probabMap.put(subString, (double) ((double) countMap.get(subString) / (double) totalMap.get("T")));
				}
			}
		}
		// System.out.println(probabMap);

		double[] init_probablities = new double[4];
		// initial probabilities of A,C,G,T respectively.
		init_probablities[0] = (double) ((double) totalMap.get("A") / (double) sequence.length());
		init_probablities[1] = (double) ((double) totalMap.get("C") / (double) sequence.length());
		init_probablities[2] = (double) ((double) totalMap.get("T") / (double) sequence.length());
		init_probablities[3] = (double) ((double) totalMap.get("G") / (double) sequence.length());

		System.out.println(Arrays.toString(init_probablities));

		// Create the matrix of 4*4 with rows and columns as A C G T
		// The matrix would be like this
		/*
		 * A C G T A 0.20 C 0.30 G 0.21 T 0.28 ---- 1.00 matrix[A][A] == p(A|A) which is
		 * conditional probability of A following A and so on..
		 */

		probab_matrix = new double[4][4];
		probab_matrix[0][0] = probabMap.get("AA");
		probab_matrix[1][0] = probabMap.get("AC");
		probab_matrix[2][0] = probabMap.get("AG");
		probab_matrix[3][0] = probabMap.get("AT");

		probab_matrix[0][1] = probabMap.get("CA");
		probab_matrix[1][1] = probabMap.get("CC");
		probab_matrix[2][1] = probabMap.get("CG");
		probab_matrix[3][1] = probabMap.get("CT");

		probab_matrix[0][2] = probabMap.get("GA");
		probab_matrix[1][2] = probabMap.get("GC");
		probab_matrix[2][2] = probabMap.get("GG");
		probab_matrix[3][2] = probabMap.get("GT");

		probab_matrix[0][3] = probabMap.get("TA");
		probab_matrix[1][3] = probabMap.get("TC");
		probab_matrix[2][3] = probabMap.get("TG");
		probab_matrix[3][3] = probabMap.get("TT");

		System.out.println("Conditional Probability Matrix generated for the Model .. ");
		for (int i = 0; i < probab_matrix.length; i++) {
			for (int j = 0; j < probab_matrix[i].length; j++) {
				System.out.print(probab_matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("Model Made..");
		System.out.println();
	}

	/*
	 * function to generate sequence from the model.
	 * 
	 * @params - probab_matrix, first_character, prev_character, init_probablities.
	 */
	public static void generateSequence(int length) {
		for (char c : characters) {
			characterSet.add(c);
		}
		// System.out.println(characterSet);

		Random random = new Random();
		choosenChar = characters[random.nextInt(4)];

		// System.out.println("In gen Seq --> " + choosenChar);

		char first_char = choosenChar;
		// System.out.println("first_char" + first_char);
		generatedOutput.append(first_char);
		char prev;
		for (int i = 1; i <= length; i++) {
			prev = generatedOutput.charAt(i - 1);
			// System.out.println(prev);
			generatedOutput.append(generateCharacter(prev));
		}
		System.out.println("String Generated Based on Model.. ");
		System.out.println(generatedOutput.toString());
	}

	private static String generateCharacter(char prev) {
		Random random = new Random();
		double value = random.nextDouble();
		// System.out.println("value -->" + value);
		double sumOfPrevProbabs = 0;
		// System.out.println("sum of prev -- >" + sumOfPrevProbabs);
		int indexOfPrev = characterSet.indexOf(prev);
		// System.out.println("Index of prev " + indexOfPrev);

		for (int i = 0; i < probab_matrix[indexOfPrev].length; i++) {
			// System.out.println(probab_matrix[i][indexOfPrev]);
			if (sumOfPrevProbabs + probab_matrix[i][indexOfPrev] >= value)
				return Character.toString(characters[i]);
			sumOfPrevProbabs += probab_matrix[i][indexOfPrev];
		}
		throw new IllegalStateException("Sum of frequencies (" + sumOfPrevProbabs + ") < " + value);

	}

	public static void main(String args[]) {
		String sequence = "AACACCTGACCTAACGGTAAGAGAGTCTCATAATGCGTCCGGCCGCGTGCCCAGGGTATATTTGGACAGTATCGAATGGACTGAGATGAACCTTTACACCGATCCGGAAACGGGTGCGTGGATTAGCC";
		learnChains(sequence);
		generateSequence(10);
	}
}
