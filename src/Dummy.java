import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Dummy {
    public final static int d = 10;

    static void search(String pattern, String txt, int q, HashMap<Character,Integer> map) {
        int m = pattern.length();
        int n = txt.length();
        int i, j;
        int p = 0;
        int t = 0;
        int h = 1;

        for (i = 0; i < m - 1; i++)
            h = (h * d) % q;

        // Calculate hash value for pattern and text
        for (i = 0; i < m; i++) {
            p = (d * p + pattern.charAt(i)) % q;
            t = (d * t + txt.charAt(i)) % q;
        }

        // Find the match
        for (i = 0; i <= n - m; i++) {
            if (p == t) {
                for (j = 0; j < m; j++) {
                    if (txt.charAt(i + j) != pattern.charAt(j))
                        break;
                }

                if (j == m)
                    System.out.println("Pattern is found at position: " + (i + 1));
            }

            if (i < n - m) {
                t = (d * (t - txt.charAt(i) * h) + txt.charAt(i + m)) % q;
                if (t < 0)
                    t = (t + q);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HashMap<Character,Integer> map = new HashMap<>();
        Character[] alphabets = new Character[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        for(int i=0; i<26;i++){
            map.put(alphabets[i], i+1);
        }


        long startTime = System.nanoTime();

        StringBuilder txt = new StringBuilder();
        String demo;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./Data/Real Data/10.txt"));
//        BufferedReader bufferedReader = new BufferedReader(new FileReader("10.txt"));
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
    }
}
