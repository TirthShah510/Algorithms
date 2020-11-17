import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class KMP {
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
		int q = 13;
		search(pattern.toString(), txt.toString());
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println(TimeUnit.MILLISECONDS.convert(totalTime, TimeUnit.NANOSECONDS));
		System.out.println("-----------------------------------------------------------------------------------------");

	}

	static void search(String pat, String txt) {
		int M = pat.length();
		int N = txt.length();

		// create lps[] that will hold the longest
		// prefix suffix values for pattern
		int lps[] = new int[M];
		int j = 0; // index for pat[]

		// Preprocess the pattern (calculate lps[]
		// array)
		computeLPSArray(pat, M, lps);

		int i = 0; // index for txt[]
		while (i < N) {
			if (pat.charAt(j) == txt.charAt(i)) {
				j++;
				i++;
			}
			if (j == M) {
				System.out.println("Found pattern " + "at index " + (i - j));
				// System.out.println("Pattern from index : " + j + " to :" + i);
				j = lps[j - 1];
			}

			// mismatch after j matches
			else if (i < N && pat.charAt(j) != txt.charAt(i)) {
				// Do not match lps[0..lps[j-1]] characters,
				// they will match anyway
				if (j != 0)
					j = lps[j - 1];
				else
					i = i + 1;
			}
		}
	}

	static void computeLPSArray(String pat, int M, int lps[]) {
		// length of the previous longest prefix suffix
		int len = 0;
		int i = 1;
		lps[0] = 0; // lps[0] is always 0

		// the loop calculates lps[i] for i = 1 to M-1
		while (i < M) {
			if (pat.charAt(i) == pat.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			} else // (pat[i] != pat[len])
			{
				// This is tricky. Consider the example.
				// AAACAAAA and i = 7. The idea is similar
				// to search step.
				if (len != 0) {
					len = lps[len - 1];

					// Also, note that we do not increment
					// i here
				} else // if (len == 0)
				{
					lps[i] = len;
					i++;
				}
			}
		}
	}

	// Driver program to test above function

}
