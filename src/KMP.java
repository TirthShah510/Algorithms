

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class KMP {

    static ArrayList<Long> time = new ArrayList<>();

    public static ArrayList<Long> function(String filePath, String dataType, int run, HashSet<Integer> indexKMP) throws IOException {

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

            search(dna.toString().toCharArray(), pattern.toString().toCharArray(), indexKMP);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            time.add(totalTime);
        }
        return time;
    }
    /*
     * @param dna - Input DNA sequence
     *
     * @param pattern - Input pattern to be searched.
     *
     * This method carries out the matching of pattern in DNA sequence. Before
     * matching starts, the algorithm calls pre-processing method to calculate lps[]
     * values.
     * Subsequently, the algorithm will calculate the matching indexes, if found, add them to list.
     * The method prints the list at the end.
     */

    static void search(char[] dna, char[] pattern, HashSet<Integer> indexKMP) {

        int dnaLength = dna.length;
        int patLength = pattern.length;

        int[] lps = new int[patLength];

        int j = 0;
        // Pre-processing of pattern string.
        computeLPS(pattern, patLength, lps);

        for (int i = 0; i < dnaLength; ) {
            // If character match, increment.
            if (pattern[j] == dna[i]) {
                j++;
                i++;
            }
            if (j == patLength) {
                indexKMP.add(i - j);
                 /*iterator j over pattern will switch to index lps[j - 1] and later continue to
                 find other occurences of pattern*/
                j = lps[j - 1];
            } else if (i < dnaLength && pattern[j] != dna[i]) {  // If mismatch :
                if (j != 0){
                    j = lps[j - 1]; // j looks into lps, will go to index which is lps[j-1].
                } else{
                    i = i + 1; // if they anyhow do not match, i will increment and not backtrack.
                }
            }
        }
    }

    /*
     * Pre-Processing Phase
     *
     * @param pattern - the pattern string
     *
     * @param patternLength
     *
     * @param lps[] array. This method is the pre-proccesing phase for pattern
     * string.
     *
     * This pre-proccesing counts the prefix-suffix combinations in the pattern
     * string. In case of a mismatch, the pattern does not need to be looked out
     * again, as the lps[] array will show which previous pattern repeats.
     *
     * A small Example -    A B A B D
     * 		     lps[] - [0,0,0,1,2,0]
     * If a mismatch occurs at B, iterator j will look into lps[] and will go to index 2 to search pattern
     * again.
     *
     */

    static void computeLPS(char[] pattern, int patternLength, int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        for (int i = 1; i < patternLength; ) {
            if (pattern[i] == pattern[len]) {
                len++;
                lps[i] = len;
                i++;
            } else {  // (pat[i] != pat[len])
                if (len != 0) {
                    len = lps[len - 1];
                } else { // if (len == 0)
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
}
