import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

class Quick {

    // Stores the time taken by each run of algorithm.
    static ArrayList<Long> time = new ArrayList<>();

    static int Chars = 256;

    /***** Driver Phase *****/
    // Function to read the DNA string and Pattern from the user given file
    public static ArrayList<Long> function(String filePath, String dataType, int run, HashSet<Integer> indexQuick)
            throws IOException {

        for (int i = 0; i < run; i++) {

            long startTime = System.currentTimeMillis();

            StringBuilder txt = new StringBuilder();
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
                txt.append(demo);
                demo = bufferedReader.readLine();
            }
            StringBuilder pattern = new StringBuilder(bufferedReader.readLine());

            search(txt.toString().toCharArray(), pattern.toString().toCharArray(), indexQuick);

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
     * This method carries to find the last occurrence of the character in the pattern string.
     */

    /***** Pre-Processing Phase *****/
    public static void lastOccurrenceChar(char[] pattern, int indexLastOccurChar[]) {

        // Fill the correct index of last occurrence of the given character
        for (int i = 0; i < pattern.length; i++) {
            indexLastOccurChar[(int) pattern[i]] = pattern.length - i - 1;
        }
    }

    /*
     * @param dna - Input DNA sequence
     *
     * @param pattern - Input pattern to be searched.
     *
     * @param indexQuick - Stores the indexes of occurrence of pattern.
     * This method carries out the matching of pattern in DNA sequence. Before
     * matching starts, the algorithm computes last occurrence (as per index) of the distinct character from pattern.
     *
     * Subsequently, the algorithm will calculate the matching indexes, if found, add them to list.
     */

    /***** Pattern Matching Phase *****/
    // Function to search occurrence of the pattern in the DNA
    public static void search(char[] dna, char[] pattern, HashSet<Integer> indexQuick) {

        int[] indexLastOccurChar = new int[Chars];

        // Initialize all last occurrences of characters with length of the pattern
        for (int i = 0; i < Chars; i++) {
            indexLastOccurChar[i] = pattern.length;
        }

        int dnaLength = dna.length;
        int patternLength = pattern.length;

        lastOccurrenceChar(pattern, indexLastOccurChar);

        // index is shift of pattern with respect to DNA string
        int index = 0;

        while (index <= (dnaLength - patternLength)) {

            // j keep track of matched character of the DNA with pattern
            int j = 0;


            // Keep increasing j until pattern found or failure occur in the matching
            while (j < patternLength && pattern[j] == dna[index + j]) {
                j++;
                // if j reaches to the length of the pattern then pattern found
                if (j >= patternLength) {
                    indexQuick.add(index);
                }
            }

            // This checks if it still have room to make shift in the DNA
            if (index + patternLength < dnaLength) {
                index += indexLastOccurChar[dna[index + patternLength]] + 1;
            } else {
                return;
            }
        }
    }
}
