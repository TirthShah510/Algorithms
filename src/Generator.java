import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Generator {
    HashMap<String, Integer> freqTwoGrams = new HashMap<>();
    HashMap<String, Integer> totalFreqTwoGrams = new HashMap<String, Integer>();
    HashMap<String, Float> probababilityTwoGrams = new HashMap<>();
    HashMap<Character, Integer> freqSingleChar = new HashMap<>();

    float[][] probabilityMatrix = new float[4][4];
    float[] initProbability = new float[4];

    char[] characters = {'A', 'C', 'G', 'T'};

    static ArrayList<Character> characterSet = new ArrayList<Character>();

    char choosenChar;
    StringBuilder generatedOutput = new StringBuilder();

    // 2-gram: AA,AC,AG,AT,CA,CC,CG,CT,GA,GC,GG,GT,TA,TC,TG,TT

    // Function to calculate the frequencies of the 2-grams.
    // For ex: "AA" = Number of occurrences of "AA" in DNA, "AC" = Number of occurrences of "AC" in DNA... etc.
    public void countFreqForTwoGrams(String sequence) {
        for (int i = 0; i < characters.length; i++) {
            for (int j = 0; j < characters.length; j++) {
                StringBuilder gram = new StringBuilder(Character.toString(characters[i])).append(characters[j]);
                freqTwoGrams.put(gram.toString(), sequence.split(Pattern.quote(gram.toString()), -1).length - 1);

            }
        }
    }

    // Function to calculate all the 2-grams which has prefix 'A', 'C', 'G' and 'T'.
    public void totalFreqForTwoGrams() {
        for (int i = 0; i < characters.length; i++) {
            for (Map.Entry<String, Integer> e : freqTwoGrams.entrySet()) {
                if (e.getKey().charAt(0) == characters[i]) {
                    if (!totalFreqTwoGrams.containsKey(Character.toString(characters[i]))) {
                        totalFreqTwoGrams.put(Character.toString(characters[i]), e.getValue());
                    } else {
                        totalFreqTwoGrams.put(Character.toString(characters[i]),
                                totalFreqTwoGrams.get(Character.toString(characters[i])) + e.getValue());
                    }
                }
            }
        }
    }

    // Function to calculate conditional probability of the 2-grams
    // For ex: probability of "AA" p(A|A) = frequency of "AA" / frequency of all the 2-grams which have prefix 'A'
    public void countProbabilityForTwoGrams() {
        for (int i = 0; i < characters.length; i++) {
            for (int j = 0; j < characters.length; j++) {
                StringBuilder gram = new StringBuilder(Character.toString(characters[i])).append(characters[j]);
                float prob = (float) freqTwoGrams.get(gram.toString()) /
                        totalFreqTwoGrams.get(Character.toString(characters[i]));
                probababilityTwoGrams.put(gram.toString(), prob);
            }
        }
    }

    // Function to calculate the probability of all the distinct characters in DNA string.
    public void countInitProbability(String sequence) {
        for (int j = 0; j < characters.length; j++) {
            int count = 0;
            for (int i = 0; i < sequence.length(); i++) {
                if (sequence.charAt(i) == characters[j]) {
                    count += 1;
                }
            }
            freqSingleChar.putIfAbsent(characters[j], count);
            initProbability[j] = (float) count / sequence.length();
        }
    }

    // Create the matrix of 4*4 with rows and columns as A C G T
    // The matrix would be like this
    /*     A     C      T     G
       A  0.35  0.25  0.10  0.30  =  1.00
       C  0.15  0.55  0.25  0.05  =  1.00
       G  0.17  0.40  0.23  0.20  =  1.00
       T  0.31  0.24  0.29  0.16  =  1.00
    */
    // matrix[A][A] = p(A|A) -> It denotes the conditional probability of A followed by A.

    public void generateProbMatrix() {

        for (int i = 0; i < probabilityMatrix.length; i++) {
            for (int j = 0; j < probabilityMatrix.length; j++) {
                StringBuilder gram = new StringBuilder(Character.toString(characters[i])).append(characters[j]);
                probabilityMatrix[i][j] = probababilityTwoGrams.get(gram.toString());
            }
        }

        System.out.println("Conditional Probability Matrix generated for the Model .. ");
        for (int i = 0; i < probabilityMatrix.length; i++) {
            for (int j = 0; j < probabilityMatrix[i].length; j++) {
                System.out.print(probabilityMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Function to make call to all the methods to calculate markov model to generate DNA of desired length.
    public void learnChains(String sequence) {


        countFreqForTwoGrams(sequence);
        System.out.println(freqTwoGrams); // Count the frequencies of AA, AC, AG, AT, CA, CC etc.


        totalFreqForTwoGrams();
//		Let total[A] = count[AA] + count[AC] + count[AG] + count[AT]
        System.out.println(totalFreqTwoGrams);


        countProbabilityForTwoGrams();
        System.out.println(probababilityTwoGrams);

        countInitProbability(sequence);

        // initial probabilities of A,C,G,T respectively.
        System.out.println(Arrays.toString(initProbability));

        generateProbMatrix();

        System.out.println("Model Made..");
        System.out.println();
    }


    // Function to generate sequence from the markov model.

    public void generateSequence(int length) {
        for (char c : characters) {
            characterSet.add(c);
        }

        Random random = new Random();
        choosenChar = characters[random.nextInt(4)];

        char first_char = choosenChar;
        generatedOutput.append(first_char); // first character added

        char prev;
        for (int i = 1; i <= length; i++) {
            prev = generatedOutput.charAt(i - 1); // to keep track of previous character
            generatedOutput.append(generateCharacter(prev));
        }
        System.out.println("String Generated Based on Model.. ");
        System.out.println(generatedOutput.toString());
    }

    // Function to generate the next character of the DNA.
    private String generateCharacter(char prev) {
        Random random = new Random();
        float value = random.nextFloat();
        float sumOfPrevProbabs = 0;
        int indexOfPrev = characterSet.indexOf(prev);

        for (int i = 0; i < probabilityMatrix[indexOfPrev].length; i++) {

            if (sumOfPrevProbabs + probabilityMatrix[indexOfPrev][i] >= value)
                return Character.toString(characters[i]);
            sumOfPrevProbabs += probabilityMatrix[indexOfPrev][i];
        }
        throw new IllegalStateException("Sum of frequencies (" + sumOfPrevProbabs + ") < " + value);

    }

    public static void main(String args[]) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        Scanner sc = new Scanner(System.in);
        int length = 0;
        try {
            System.out.println("Enter Training file for DNA: ");
            String fileName = sc.nextLine();
            bufferedReader = new BufferedReader(new FileReader("./Data/Training DNA/" + fileName));
            System.out.println("Enter desired length of DNA: ");
            length = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid length for DNA");
        } catch (IOException ioException) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        String line = bufferedReader.readLine();
        while (line != null) {
            sb.append(line);
            line = bufferedReader.readLine();
        }
        Generator generator = new Generator();
        generator.learnChains(sb.toString());
        if (length > 0) {
            generator.generateSequence(length);
        } else {
            System.out.println("Invalid length for DNA");
        }
    }
}