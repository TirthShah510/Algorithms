import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KMP {
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
        search(dna.toString(), pattern.toString());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time taken by KMP ---->   " + totalTime);
        System.out.println("-----------------------------------------------------------------------------------------");

    }

    static void search(String dna, String pattern) {

        int patternLength = pattern.length();
        int dnaLength = dna.length();
        ArrayList<Integer> patternOccurrenceIndex = new ArrayList<>();

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int lps[] = new int[patternLength];
        int j = 0; // index for pat[]

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pattern, patternLength, lps);

        int index = 0; // index for txt[]
        while (index < dnaLength) {
            if (pattern.charAt(j) == dna.charAt(index)) {
                j++;
                index++;
            }
            if (j == patternLength) {
                patternOccurrenceIndex.add(index - j);
                j = lps[j - 1];
            } else if (index < dnaLength && pattern.charAt(j) != dna.charAt(index)) {
                // mismatch after j matches
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    index++;
                }
            }
        }
        System.out.println(patternOccurrenceIndex);
    }

    static void computeLPSArray(String pat, int M, int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else {
                    // if (len == 0)
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
}
