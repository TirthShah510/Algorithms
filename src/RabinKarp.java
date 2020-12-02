import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class RabinKarp {

    static ArrayList<Long> time = new ArrayList<>();

    // selected base value for calculating the hash value
    public final static int d = 4;

    public final static int primeDigit = 13;

    /***** Driver Phase *****/
    public static ArrayList<Long> function(String filePath, String dataType, int run, HashSet<Integer> indexRabinKarp) throws IOException {

        for (int i = 0; i < run; i++) {

            long startTime = System.currentTimeMillis();

            StringBuilder dna = new StringBuilder();
            String demo;
            BufferedReader bufferedReader = null;

            try {
                bufferedReader = new BufferedReader(new FileReader(filePath));
            } catch (IOException ioException) {
                System.out.println("File does not exist");
                System.exit(0);
            }

            while (!bufferedReader.readLine().equals("DNA")) {
            }
            demo = bufferedReader.readLine();
            while (!(demo.equals("Pattern"))) {
                dna.append(demo);
                demo = bufferedReader.readLine();
            }
            StringBuilder pattern = new StringBuilder(bufferedReader.readLine());

            search(dna.toString().toCharArray(), pattern.toString().toCharArray(), indexRabinKarp);

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            time.add(totalTime);
        }
        return time;
    }

    /***** Matching Phase *****/
    // Function checks whether pattern occurs in DNA or not
    // If yes then find all the occurrences of the pattern in DNA
    public static void search(char[] dna, char[] pattern, HashSet<Integer> indexRabinKarp) {

        int i, j;
        int index = 0;
        int patternLength = pattern.length;
        int dnaLength = dna.length;
        int patternHash = 0;
        int dnaHash = 0;
        int h = 1;

        for (i = 0; i < patternLength - 1; i++) {
            h = (h * d) % primeDigit;
        }

        // Calculate hash value for pattern and DNA
        for (i = 0; i < patternLength; i++) {
            patternHash = (d * patternHash + pattern[i]) % primeDigit;
            dnaHash = (d * dnaHash + dna[i]) % primeDigit;
        }

        // Find the match

        while (index <= dnaLength - patternLength) {

            // check if hash value of pattern and DNA is equal or not
            // if yes then check the individual element is equivalent or not
            if (patternHash == dnaHash) {
                for (j = 0; j < patternLength; j++) {
                    if (dna[index+j] != pattern[j]){
                        break;
                    }
                }

                // if pattern found then add the index of DNA to the ArrayList
                if (j == patternLength) {
                    indexRabinKarp.add(index);
                }
            }
            dnaHash = computeHash(index, dnaLength, patternLength, dnaHash, dna, h);
            index++;
        }
    }

    /***** Hash Function *****/
    private static int computeHash(int index, int dnaLength, int patternLength, int dnaHash, char[] dna, int h) {

        if (index < dnaLength - patternLength) {

            // Calculates the hash value for next substring of DNA in order to find pattern
            dnaHash = (d * (dnaHash - dna[index] * h) + dna[index + patternLength]) % primeDigit;

            if (dnaHash < 0) {
                dnaHash = (dnaHash + primeDigit);
            }
        }

        return dnaHash;
    }
}