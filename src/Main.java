import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = sc.nextLine();
        if(fileName.charAt(1) == 'r'){
//            RabinKarp.function("./Data/Real Data/" + fileName, "Real Data");
//            Advance.function("./Data/Real Data/" + fileName, "Real Data");
            KMP.function("./Data/Real Data/" + fileName, "Real Data");
//            Naive.function("./Data/Real Data/" + fileName, "Real Data");
        }else if(fileName.charAt(1) == 's') {
//            RabinKarp.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
//            Advance.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
            KMP.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
//            Naive.function("./Data/Synthetic Data/" + fileName, "Synthetic Data");
        }
    }

}
