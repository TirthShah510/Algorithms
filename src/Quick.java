import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Quick {

    static ArrayList<Long> time = new ArrayList<>();

    static int Chars = 256;

    /***** Driver Phase *****/
    // Function to read the DNA string and Pattern from the user given file
    public static void function(String filePath, String dataType, int run) throws IOException {

        for(int i=0; i<run; i++){

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
            System.out.println("****** Quick Search ******");
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

            /*for(int i=0; i<run; i++){
                long startTime1 = System.currentTimeMillis();
                search(txt.toString().toCharArray(), pattern.toString().toCharArray());
                long endTime1 = System.currentTimeMillis();
                long totalTime1 = endTime1 - startTime1;
                time.add(totalTime1);
            }*/




        search(txt.toString().toCharArray(), pattern.toString().toCharArray());
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Time taken by Quick Search ---->   " + totalTime + " milliseconds");
            System.out.println("-----------------------------------------------------------------------------------------");

            time.add(totalTime);
        }

        System.out.println("TIME QUICK: " + time);
    }


    /***** Pre-Processing Phase *****/
    //The Pre-processing function for Quick search algorithm
    //Function to find the last occurrence of the character in the pattern string
    public static void lastOccurrenceChar(char[] pattern, int indexLastOccurChar[]) {

        // Fill the correct index of last occurrence of the given character
        for (int i = 0; i < pattern.length; i++){
            indexLastOccurChar[(int) pattern[i]] = pattern.length - i - 1;
        }
    }

    /***** Pattern Matching Phase *****/
    // Function to search occurrence of the pattern in the DNA
    public static void search(char[] dna, char[] pattern) {

        int[] indexLastOccurChar = new int[Chars];
        // Initialize all last occurrences of characters with length of the pattern
        for (int i = 0; i < Chars; i++){
            indexLastOccurChar[i] = pattern.length;
        }

        ArrayList<Integer> patternOccurrenceIndex = new ArrayList<>();

        int dnaLength = dna.length;
        int patternLength = pattern.length;

        lastOccurrenceChar(pattern, indexLastOccurChar);

        // index is shift of pattern with respect to DNA string
        int index = 0;

        while (index <= (dnaLength - patternLength)) {

            // j keep track of matched charater of the DNA with pattern
            int j = 0;


            // Keep increasing j until pattern found or failure occur in the matching
            while (j < patternLength && pattern[j] == dna[index + j]){
                j++;
                // if j reaches to the length of the pattern then pattern found
                if (j >= patternLength) {
                    patternOccurrenceIndex.add(index);
                }
            }

            // This checks if it still have room to make shift in the DNA
            if (index + patternLength < dnaLength){
                index += indexLastOccurChar[dna[index + patternLength]] + 1;
            }else{
                System.out.println(patternOccurrenceIndex);
                return;
            }
        }
        System.out.println(patternOccurrenceIndex);
    }
}
