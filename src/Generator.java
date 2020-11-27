import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Generator {
    HashMap<String, Integer> freqTwoGrams = new HashMap<>();
    HashMap<String, Integer> totalFreqTwoGrams = new HashMap<String, Integer>();
    TreeMap<String, Double> probababilityTwoGrams = new TreeMap<String, Double>();
	HashMap<Character, Integer> freqSingleChar = new HashMap<>();

    double[][] probabilityMatrix = new double[4][4];
	double[] initProbability = new double[4];

    char[] characters = {'A', 'C', 'G', 'T'};;
    static ArrayList<Character> characterSet = new ArrayList<Character>();

    char choosenChar;
    StringBuilder generatedOutput = new StringBuilder();

    public void learnChains(String sequence) {

        for (int i = 0; i < characters.length; i++) {
            for (int j = 0; j < characters.length; j++) {
                StringBuilder gram = new StringBuilder(Character.toString(characters[i])).append(characters[j]);
                freqTwoGrams.put(gram.toString(), sequence.split(Pattern.quote(gram.toString()), -1).length - 1);

            }
        }

        System.out.println(freqTwoGrams); // Count the frequencies of AA, AC, AG, AT, CA, CC etc.

        for (int i = 0; i < characters.length; i++) {
            for (Map.Entry<String, Integer> e : freqTwoGrams.entrySet()) {
                if (e.getKey().charAt(0) == characters[i]) {
                    if (!totalFreqTwoGrams.containsKey(Character.toString(characters[i]))) {
                        totalFreqTwoGrams.put(Character.toString(characters[i]), e.getValue());
                    } else {
                        totalFreqTwoGrams.put(Character.toString(characters[i]), totalFreqTwoGrams.get(Character.toString(characters[i])) + e.getValue());
                    }
                }
            }
        }

//		Let total[A] = count[AA] + count[AC] + count[AG] + count[AT]
        System.out.println(totalFreqTwoGrams);

        for (int i = 0; i < characters.length; i++) {
            for (int j = 0; j < characters.length; j++) {
                StringBuilder gram = new StringBuilder(Character.toString(characters[i])).append(characters[j]);
                double prob = (double) freqTwoGrams.get(gram.toString()) / (double) totalFreqTwoGrams.get(Character.toString(characters[i]));
                probababilityTwoGrams.put(gram.toString(), Math.round(prob * 1000.0) / 1000.0);
            }
        }

        System.out.println(probababilityTwoGrams);

        for (int j = 0; j < characters.length; j++) {
            int count = 0;
            for (int i = 0; i < sequence.length(); i++) {
                if (sequence.charAt(i) == characters[j]) {
                    count += 1;
                }
            }
            freqSingleChar.putIfAbsent(characters[j], count);
            initProbability[j] = (double) count / sequence.length();
        }

        // initial probabilities of A,C,G,T respectively.
        System.out.println(Arrays.toString(initProbability));

        // Create the matrix of 4*4 with rows and columns as A C G T
        // The matrix would be like this
        /*
         * A C G T A 0.20 C 0.30 G 0.21 T 0.28 ---- 1.00 matrix[A][A] == p(A|A) which is
         * conditional probability of A following A and so on..
         */

        probabilityMatrix = new double[4][4];

		for(int i = 0; i< probabilityMatrix.length; i++){
			for(int j = 0; j< probabilityMatrix.length; j++){
				StringBuilder gram = new StringBuilder(Character.toString(characters[i])).append(characters[j]);
				probabilityMatrix[j][i] = probababilityTwoGrams.get(gram.toString());
			}
		}

        System.out.println("Conditional Probability Matrix generated for the Model .. ");
        for (int i = 0; i < probabilityMatrix.length; i++) {
            for (int j = 0; j < probabilityMatrix[i].length; j++) {
                System.out.print(probabilityMatrix[i][j] + " ");
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
    public void generateSequence(int length) {
        for (char c : characters) {
            characterSet.add(c);
        }
        // System.out.println(characterSet);

        Random random = new Random();
        choosenChar = characters[random.nextInt(4)];

        // System.out.println("In gen Seq --> " + choosenChar);

        char first_char = choosenChar;
        // System.out.println("first_char" + first_char);
        generatedOutput.append(first_char); // first character added

        char prev;
        for (int i = 1; i <= length; i++) {
            prev = generatedOutput.charAt(i - 1); // to keep track of previous character
            // System.out.println(prev);
            generatedOutput.append(generateCharacter(prev));
        }
        System.out.println("String Generated Based on Model.. ");
        System.out.println(generatedOutput.toString());
    }

    private String generateCharacter(char prev) {
        Random random = new Random();
        double value = random.nextDouble();
        // System.out.println("value -->" + value);
        double sumOfPrevProbabs = 0;
        // System.out.println("sum of prev -- >" + sumOfPrevProbabs);
        int indexOfPrev = characterSet.indexOf(prev);
        // System.out.println("Index of prev " + indexOfPrev);

        for (int i = 0; i < probabilityMatrix[indexOfPrev].length; i++) {
            // System.out.println(probab_matrix[i][indexOfPrev]);
            if (sumOfPrevProbabs + probabilityMatrix[i][indexOfPrev] >= value)
                return Character.toString(characters[i]);
            sumOfPrevProbabs += probabilityMatrix[i][indexOfPrev];
        }
        throw new IllegalStateException("Sum of frequencies (" + sumOfPrevProbabs + ") < " + value);

    }

    public static void main(String args[]) throws IOException {
        StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new FileReader("./Data/Training DNA/demo.txt" ));
		String line = bufferedReader.readLine();
		while(line != null){
			sb.append(line);
			line = bufferedReader.readLine();
		}
        Generator generator = new Generator();
        generator.learnChains(sb.toString());
        generator.generateSequence(10);
    }
}
