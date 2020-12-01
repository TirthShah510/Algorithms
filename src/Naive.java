
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Naive {

    static ArrayList<Long> time = new ArrayList<>();

    /***** Driver Phase *****/
    public static void function(String filePath, String dataType, int run) throws IOException {

        for(int i=0; i<run; i++){
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

        /*for(int i=0; i<run; i++){
            long startTime1 = System.currentTimeMillis();
            search(dna.toString(), pattern.toString());
            long endTime1 = System.currentTimeMillis();
            long totalTime1 = endTime1 - startTime1;
            time.add(totalTime1);
        }*/



        search(dna.toString(), pattern.toString());
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Time taken by Naive ---->   " + totalTime + " milliseconds");
            System.out.println("-----------------------------------------------------------------------------------------");

            time.add(totalTime);
        }

        System.out.println("TIME NAIVE: " + time);

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
