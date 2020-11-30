import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Naive {

    /***** Driver Phase *****/
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
        System.out.println("****** Naive ******");
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

        search(dna.toString(), pattern.toString());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time taken by Naive ---->   " + totalTime + " milliseconds");
        System.out.println("-----------------------------------------------------------------------------------------");

    }

    /***** Matching Phase*****/
    public static void search(String dna, String pattern) {

        int patternLength = pattern.length();
        int dnaLength = dna.length();
		ArrayList<Integer> patternOccurrenceIndex = new ArrayList<>();

		// Traverse the DNA string and check at every index that pattern matches or not
        for (int i = 0; i <= dnaLength - patternLength; i++) {
            int j;
            /*
             * For current index i, check for pattern match
             */
            for (j = 0; j < patternLength; j++) {
				if (dna.charAt(i + j) != pattern.charAt(j)) {
					break;
				}
			}

            // After finding a pattern, add that index into ArrayList.
            if (j == patternLength) {
				patternOccurrenceIndex.add(i);
			}
        }

		System.out.println(patternOccurrenceIndex);
    }
}
