import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RabinKarp {
    public final static int d = 33;

    static void search(String pattern, String txt, int q, HashMap<Character,Integer> map) {

        int patternLength = pattern.length();
        int DNALength = txt.length();
        int i, j;
        int p = 0;
        int t = 0;
        int h = 1;

        for (i = 0; i < patternLength - 1; i++)
            h = (h * d) % q;

        // Calculate hash value for pattern and text
        for (i = 0; i < patternLength; i++) {
            p = (d * p + map.get(pattern.charAt(i))) % q;
            t = (d * t + map.get(txt.charAt(i))) % q;
        }


//        for(i=0; i<patternLength;i++){
//            p = 33 * p + pattern.charAt(i);
//            t = 33 * t + pattern.charAt(i);
//        }

        // Find the match
        for (i = 0; i <= DNALength - patternLength; i++) {
            if (p == t) {
                for (j = 0; j < patternLength; j++) {
                    if (txt.charAt(i + j) != pattern.charAt(j))
                        break;
                }

                if (j == patternLength)
                    System.out.println("Pattern is found at position: " + (i + 1));
            }

            if (i < DNALength - patternLength) {
//                System.out.println(txt.charAt(i+patternLength));
//                System.out.println(map.get(txt.charAt(i+patternLength)));
                t = (d * (t - map.get(txt.charAt(i)) * h) + map.get(txt.charAt(i + patternLength))) % q;
                if (t < 0)
                    t = (t + q);
            }
        }
    }

    public static void function(String filePath, String dataType) throws IOException {

        HashMap<Character,Integer> map = new HashMap<>();
        Character[] alphabets = new Character[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        for(int i=0; i<26;i++){
            map.put(alphabets[i], i+1);
        }

        long startTime = System.nanoTime();

        StringBuilder txt = new StringBuilder();
        String demo;
        BufferedReader bufferedReader = null;

        try{
            bufferedReader = new BufferedReader(new FileReader(filePath));
        }catch (IOException ioException){
            System.out.println("File does not exist");
            System.exit(0);
        }
        System.out.println(dataType);
        System.out.println("DNA Length: "+bufferedReader.readLine());
        while (!bufferedReader.readLine().equals("DNA")) {
        }
        demo  = bufferedReader.readLine();
        while(!(demo.equals("Pattern"))) {
            txt.append(demo);
            demo = bufferedReader.readLine();
        }
        StringBuilder pattern = new StringBuilder(bufferedReader.readLine());
        int q = 13;
        search(pattern.toString(), txt.toString(), q, map);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(TimeUnit.MILLISECONDS.convert(totalTime, TimeUnit.NANOSECONDS));
        System.out.println("-----------------------------------------------------------------------------------------");
    }
}