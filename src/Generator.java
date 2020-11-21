import java.util.Random;

public class Generator {

    public static String randomDNAString(int dnaLength) {
        Random rand = new Random();
        StringBuilder dna = new StringBuilder(dnaLength);

        for (int i = 0; i < dnaLength; i++) {
            dna.append("ACGT".charAt(rand.nextInt(4)));
        }

        return dna.toString();
    }

    public static void main(String args[]){
        System.out.println(randomDNAString(15));
    }
}
