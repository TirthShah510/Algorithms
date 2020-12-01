import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = sc.nextLine();
        int run=1;

        try {
            System.out.println("Enter desired run for algorithms: ");
            run = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid length for DNA");
        }

        String[] seperate = fileName.split("\\.");
        String f = seperate[0];
        char[] fdash = f.toCharArray();

        for (char c : fdash) {
            if (c == 'r') {
                Naive.function("./Data/Real Data/" + fileName, "Real Data", run);
                RabinKarp.function("./Data/Real Data/" + fileName, "Real Data", run);
                KMP.function("./Data/Real Data/" + fileName, "Real Data", run);
                Quick.function("./Data/Real Data/" + fileName, "Real Data", run);
            } else if (c == 's') {
                Naive.function("./Data/Synthetic Data/" + fileName, "Synthetic Data", run);
                RabinKarp.function("./Data/Synthetic Data/" + fileName, "Synthetic Data", run);
                KMP.function("./Data/Synthetic Data/" + fileName, "Synthetic Data", run);
                Quick.function("./Data/Synthetic Data/" + fileName, "Synthetic Data", run);
            }
        }
        sc.close();
    }
}
