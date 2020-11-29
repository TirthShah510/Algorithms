import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int lps[] = new int[patternLength];
        int j = 0; // index for pat[]

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pattern, patternLength, lps);

        int i = 0; // index for txt[]
        while (i < dnaLength) {
            if (pattern.charAt(j) == dna.charAt(i)) {
                j++;
                i++;
            }
            if (j == patternLength) {
                System.out.println("Found pattern " + "at index " + (i - j));
                // System.out.println("Pattern from index : " + j + " to :" + i);
                j = lps[j - 1];
            }

            // mismatch after j matches
            else if (i < dnaLength && pattern.charAt(j) != dna.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }

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
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
}
