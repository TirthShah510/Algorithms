import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RabinKarp {

    // selected base value for calculating the hash value
    public final static int d = 10;

    public static void function(String filePath, String dataType) throws IOException {

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
        System.out.println(dataType);
        System.out.println("DNA Length: " + bufferedReader.readLine());
        while (!bufferedReader.readLine().equals("DNA")) {
        }
        demo = bufferedReader.readLine();
        while (!(demo.equals("Pattern"))) {
            dna.append(demo);
            demo = bufferedReader.readLine();
        }
        StringBuilder pattern = new StringBuilder(bufferedReader.readLine());
        int q = 13;
        search(dna.toString(), pattern.toString(), q);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time taken by Rabin-Karp ---->   " + totalTime);
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    // Function checks whether pattern occurs in DNA or not
    // If yes then find all the occurrences of the pattern in DNA
    public static void search(String dna, String pattern, int q) {

        int i, j;
        int index = 0;
        int patternLength = pattern.length();
        int dnaLength = dna.length();
        int patternHash = 0;
        int dnaHash = 0;
        int h = 1;
        ArrayList<Integer> patternOccurrenceIndex = new ArrayList<>();

        for (i = 0; i < patternLength - 1; i++) {
            h = (h * d) % q;
        }

        // Calculate hash value for pattern and DNA
        for (i = 0; i < patternLength; i++) {
            patternHash = (d * patternHash + pattern.charAt(i)) % q;
            dnaHash = (d * dnaHash + dna.charAt(i)) % q;
        }

        // Find the match

        while (index <= dnaLength - patternLength) {

            // check if hash value of pattern and DNA is equal or not
            // if yes then check the individual element is equivalent or not
            if (patternHash == dnaHash) {
                for (j = 0; j < patternLength; j++) {
                    if (dna.charAt(index + j) != pattern.charAt(j))
                        break;
                }

                // if pattern found then add the index of DNA to the ArrayList
                if (j == patternLength) {
                    patternOccurrenceIndex.add(index);
                }
            }

            if (index < dnaLength - patternLength) {

                // Calculates the hash value for next substring of DNA in order to find pattern
                dnaHash = (d * (dnaHash - dna.charAt(index) * h) + dna.charAt(index + patternLength)) % q;

                if (dnaHash < 0) {
                    dnaHash = (dnaHash + q);
                }
            }
            index++;
        }

        System.out.println(patternOccurrenceIndex);
    }
}