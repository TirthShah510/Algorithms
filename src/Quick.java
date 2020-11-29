import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Quick {

    static int NO_OF_CHARS = 256;

    //A utility function to get maximum of two integers
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    //The preprocessing function for Boyer Moore's
    //bad character heuristic
    static void badCharHeuristic(char[] str, int size, int badchar[]) {
        int i;

        // Initialize all occurrences as -1
        for (i = 0; i < NO_OF_CHARS; i++)
            badchar[i] = size;

        // Fill the actual value of last occurrence
        // of a character
        for (i = 0; i < size; i++)
            badchar[(int) str[i]] = size - i - 1;
    }

    /* A pattern searching function that uses Bad
    Character Heuristic of Boyer Moore Algorithm */
    static void search(char txt[], char pat[]) {
        int m = pat.length;
        int n = txt.length;

        int badchar[] = new int[NO_OF_CHARS];

	/* Fill the bad character array by calling
		the preprocessing function badCharHeuristic()
		for given pattern */
        badCharHeuristic(pat, m, badchar);

        int s = 0; // s is shift of the pattern with
        // respect to text
        while (s <= (n - m)) {
//            int j = m-1;
            int j = 0;

		/* Keep reducing index j of pattern while
			characters of pattern and text are
			matching at this shift s */
//            while(j >= 0 && pat[j] == txt[s+j])
//                j--;
            while (j < m && pat[j] == txt[s + j])
                j++;

		/* If the pattern is present at current
			shift, then index j will become -1 after
			the above loop */
            if (j >= m) {
                System.out.println("Patterns occur at shift = " + s);

			/* Shift the pattern so that the next
				character in text aligns with the last
				occurrence of it in pattern.
				The condition s+m < n is necessary for
				the case when pattern occurs at the end
				of text */
//                s += (s+m < n)? m-badchar[txt[s+m]] : 1;

            }

//            else
//			/* Shift the pattern so that the bad character
//				in text aligns with the last occurrence of
//				it in pattern. The max function is used to
//				make sure that we get a positive shift.
//				We may get a negative shift if the last
//				occurrence of bad character in pattern
//				is on the right side of the current
//				character. */
////                s += max(1, j - badchar[txt[s+j]]);
            if (s + m < n)
                s += badchar[txt[s + m]] + 1;
        }
    }

    /* Driver program to test above function */
//    public static void main(String []args) {
//
//        char txt[] = "ABAAABCD".toCharArray();
//        char pat[] = "AAA".toCharArray();
//        search(txt, pat);
//    }

    public static void function(String filePath, String dataType) throws IOException {

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
        System.out.println(dataType);
        System.out.println("DNA Length: " + bufferedReader.readLine());
        while (!bufferedReader.readLine().equals("DNA")) {
        }
        demo = bufferedReader.readLine();
        while (!(demo.equals("Pattern"))) {
            txt.append(demo);
            demo = bufferedReader.readLine();
        }
        StringBuilder pattern = new StringBuilder(bufferedReader.readLine());
        int q = 13;
        search(txt.toString().toCharArray(), pattern.toString().toCharArray());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time taken by Quick Search ---->   " + totalTime);
        System.out.println("-----------------------------------------------------------------------------------------");
    }
}
