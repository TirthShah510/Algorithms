import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Advance{

    public static void function(String filePath, String dataType) throws IOException {

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

        int n = txt.length();
        int m = pattern.length();
        int world_len = 4;
        int count = 0;
        int num_window = 0;
        List<Integer> window_index = new ArrayList<>();
        while(count <= n-m){

            if(txt.substring(count, count + world_len).equals(pattern.substring(0, world_len))){
                window_index.add(count);
                num_window = num_window+1;
            }
            count++;
        }
        int k = m % world_len;
        int start_index;
        if(k==0){
            start_index = world_len;
        }else{
            start_index = k;
        }
        count = 0;
        int s, c;
        int num_match = 0;
        List<Integer> match_index = new ArrayList<>();
        while(count < num_window){
            s = window_index.get(count);
            c = start_index;
            while(c<=m-world_len){
                if(!pattern.substring(c, c + world_len).equals(txt.substring(s + c, s + c + world_len))){
                    break;
                }
                c = c+world_len;
            }
            if(c==m){
                match_index.add(s+1);
                num_match = num_match + 1;
            }
            count++;
        }

        System.out.println(match_index);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(TimeUnit.MILLISECONDS.convert(totalTime, TimeUnit.NANOSECONDS));
    }
}