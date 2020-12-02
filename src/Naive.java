

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Naive {

    static ArrayList<Long> time = new ArrayList<>();

    /***** Driver Phase *****/
    public static ArrayList<Long> function(String filePath, String dataType, int run, HashSet<Integer> indexNaive) throws IOException {

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

            search(dna.toString().toCharArray(), pattern.toString().toCharArray(), indexNaive);

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            time.add(totalTime);
        }

        return time;

    }

    /***** Matching Phase*****/
    public static void search(char[] dna, char[] pattern, HashSet<Integer> indexNaive) {

        int patternLength = pattern.length;
        int dnaLength = dna.length;

        // Traverse the DNA string and check at every index that pattern matches or not
        for (int i = 0; i <= dnaLength - patternLength; i++) {
            int j;
            /*
             * For current index i, check for pattern match
             */
            for (j = 0; j < patternLength; j++) {

                if(dna[i+j] != pattern[j]){
                    break;
                }

            }

            // After finding a pattern, add that index into ArrayList.
            if (j == patternLength) {
                indexNaive.add(i);
            }
        }
    }
}
