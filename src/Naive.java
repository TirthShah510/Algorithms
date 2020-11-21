import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.concurrent.TimeUnit;

public class Naive {
	public static void function(String filePath, String dataType) throws IOException {
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
		// int q = 13;

		search(txt.toString(), pattern.toString());
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time taken for Naive ---->   " + totalTime);
		System.out.println("-----------------------------------------------------------------------------------------");

	}

	public static void search(String txt, String pat) {
		int patternLength = pat.length();
		int sequenceLength = txt.length();
		for (int i = 0; i <= sequenceLength - patternLength; i++) {
			int j;
			/*
			 * For current index i, check for pattern match
			 */
			for (j = 0; j < patternLength; j++)
				if (txt.charAt(i + j) != pat.charAt(j))
					break;

			if (j == patternLength)
				System.out.println("Pattern found at index " + i);
		}
	}
}
