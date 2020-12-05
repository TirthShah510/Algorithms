import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = sc.nextLine();
        int run = 1;

        try {
            System.out.println("Enter desired run for algorithms: ");
            run = sc.nextInt();
            if(run < 1){
                System.out.println("Invalid length for DNA");
                System.exit(0);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid length for DNA");
            System.exit(0);
        }

        String[] seperate = fileName.split("\\.");
        String f = seperate[0];
        char[] fdash = f.toCharArray();

        ArrayList<Long> naiveTime = new ArrayList<>();
        ArrayList<Long> rabinKarpTime = new ArrayList<>();
        ArrayList<Long> kmpTime = new ArrayList<>();
        ArrayList<Long> quickTime = new ArrayList<>();

        HashSet<Integer> indexNaive = new HashSet<>();
        HashSet<Integer> indexRabinKarp = new HashSet<>();
        HashSet<Integer> indexKMP = new HashSet<>();
        HashSet<Integer> indexQuick = new HashSet<>();

        for (char c : fdash) {
            if (c == 'r') {

                printDataInfo("Real Data", fileName, run);

                naiveTime = Naive.function("Data/Real Data/" + fileName, "Real Data", run, indexNaive);
                rabinKarpTime = RabinKarp.function("Data/Real Data/" + fileName, "Real Data", run, indexRabinKarp);
                kmpTime = KMP.function("Data/Real Data/" + fileName, "Real Data", run, indexKMP);
                quickTime = Quick.function("Data/Real Data/" + fileName, "Real Data", run, indexQuick);
            } else if (c == 's') {

                printDataInfo("Synthetic Data", fileName, run);

                naiveTime = Naive.function("Data/Synthetic Data/" + fileName, "Synthetic Data", run, indexNaive);
                rabinKarpTime = RabinKarp.function("Data/Synthetic Data/" + fileName, "Synthetic Data", run,indexRabinKarp);
                kmpTime = KMP.function("Data/Synthetic Data/" + fileName, "Synthetic Data", run, indexKMP);
                quickTime = Quick.function("Data/Synthetic Data/" + fileName, "Synthetic Data", run, indexQuick);
            }
        }

        if(indexNaive.size() != 0) {

            printIndexes(indexNaive, "Naive");
            printIndexes(indexRabinKarp, "Rabin Karp");
            printIndexes(indexKMP, "KMP");
            printIndexes(indexQuick, "Quick Search");

            System.out.println();

            average(naiveTime, "Naive Algorithm");
            average(rabinKarpTime, "Rabin Karp Algorithm");
            average(kmpTime, "KMP Algorithm");
            average(quickTime, "Quick Search Algorithm");
        }

        sc.close();
    }

    public static void printDataInfo(String dataType, String fileName, int run) throws IOException {

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader("Data/" + dataType + "/" + fileName));
        }catch (IOException ioException) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        System.out.println("Total number of run : " + run);
        System.out.println("***** " + dataType + " *****");
        System.out.println("DNA Length: " + bufferedReader.readLine());
        System.out.println("Pattern Length: " + bufferedReader.readLine());
        System.out.println("\nRunnig.....\n");


    }

    private static void printIndexes(HashSet<Integer> set, String algoType) {

        ArrayList<Integer> list = new ArrayList<>(set);
        Collections.sort(list);

        System.out.println(algoType + " - Pattern found at : " + list);
    }

    public static void average(ArrayList<Long> list, String algoType){

        long sum = 0;

        for (Long a : list) {
            sum += a;
        }
        System.out.println("Average running time for " + algoType + " : " + sum/list.size());
    }
}
