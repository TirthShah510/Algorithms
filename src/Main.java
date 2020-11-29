import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = sc.nextLine();
        String[] seperate = fileName.split("\\.");
        String f = seperate[0];
        char[] fdash = f.toCharArray();

        for (char c : fdash) {
            if (c == 'r') {
                Naive.function("./Data/Real Data/" + fileName, "Real Data");
                RabinKarp.function("./Data/Real Data/" + fileName, "Real Data");
                KMP.function("./Data/Real Data/" + fileName, "Real Data");
                Quick.function("./Data/Real Data/" + fileName, "Real Data");
            } else if (c == 's') {
                Naive.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
                RabinKarp.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
                KMP.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
                Quick.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
            }
        }
        sc.close();
    }
}
