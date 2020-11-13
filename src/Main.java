import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        int option = displayMainMenu(sc);
        if(option == 1){
            runSyntheticData();
        }else if(option == 2){
            runRealData();
        }
    }

    private static void runRealData() throws IOException {
        for(int i = 1; i<=10; i++){
            RabinKarp.function("./Data/Real Data/" + i + ".txt");
//            KMP.function("./Data/Real Data/" + i + ".txt");
//            Naive.function("./Data/Real Data/" + i + ".txt");
//            Advance.function("./Data/Real Data/" + i + ".txt");
        }
    }

    private static void runSyntheticData() throws IOException {
        for(int i = 1; i<=10; i++){
//            RabinKarp.function("./Data/Synthetic Data/" + i + ".txt");
//            KMP.function("./Data/Synthetic Data/" + i + ".txt");
//            Naive.function("./Data/Synthetic Data/" + i + ".txt");
//            Advance.function("./Data/Synthetic Data/" + i + ".txt");
        }
    }

    private static int displayMainMenu(Scanner sc) {
        System.out.println("1. Run the Synthetic Data");
        System.out.println("2. Run the Real Data");
        System.out.println("3. Quit");
        System.out.println("Choose option from 1, 2 and 3: ");
        while(!sc.hasNextInt()){
            System.out.println("That's not integer. Choose option from 1,2 and 3");
            sc.next();
        }
        int option = sc.nextInt();

        if(option == 1 || option == 2){
            return option;
        }else if(option == 3) {
            System.exit(0);
        }
        System.out.println("Invalid selection");
        return displayMainMenu(sc);
    }

    /*private static void displaySyntheticData(Scanner sc) throws IOException {
        System.out.println("1. 1.txt");
        System.out.println("2. 2.txt");
        System.out.println("3. 3.txt");
        System.out.println("4. Quit");

        while(!sc.hasNextInt()){
            System.out.println("That's not integer. Choose option from 1,2 and 3");
            sc.next();
        }
        int option = sc.nextInt();

        if(option == 4){
            System.exit(0);
        }

        switch (option){
            case 1:
                RabinKarp.function("./Data/Real Data/1.txt");
                KMP.function("./Data/Real Data/1.txt");
                Naive.function("./Data/Real Data/1.txt");
                Advance.function("./Data/Real Data/1.txt");
            case 2:
                RabinKarp.function("./Data/Real Data/2.txt");
                KMP.function("./Data/Real Data/2.txt");
                Naive.function("./Data/Real Data/2.txt");
                Advance.function("./Data/Real Data/2.txt");
            case 3:
                RabinKarp.function("./Data/Real Data/3.txt");
                KMP.function("./Data/Real Data/3.txt");
                Naive.function("./Data/Real Data/3.txt");
                Advance.function("./Data/Real Data/3.txt");
            default:
                System.out.println("Invalid selection");
        }
    }*/
}
