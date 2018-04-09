import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Lab8P3 {

	public static void word_count(String in, String out) {
		HashMap<String, Integer> wc = new HashMap<String, Integer>();

		// https://stackoverflow.com/a/19492227/1198896
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File(in));
		} catch (FileNotFoundException e) {
			System.err.println("file does not exist");
			e.printStackTrace();
			return;
		}

		BufferedReader br = new BufferedReader(fileReader);

		String line = null;
		// if no more lines the readLine() returns null
		try {
			while ((line = br.readLine()) != null) {
				String[] words = line.split("\\s");
				for (String w: words) {
					if (w.equals("")) continue;

					if (wc.containsKey(w)) {
						wc.put(w, wc.get(w) + 1);
					} else {
						wc.put(w,  1);
					}
				}
			}
		} catch (IOException e) {
			// XXX do something intelligent here, I guess?
			// This is annoying.
			e.printStackTrace();
			return;
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(out));
			for (Entry<String, Integer> e: wc.entrySet()) {
				writer.write(e.getKey() + ":" + e.getValue());
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e1) {
			// XXX do something intelligent here, I guess?
			// This is annoying.
			System.err.println("can't write to file");
			e1.printStackTrace();
			return;
		}
		
	}

	public static void main(String[] args) {
		word_count("/Users/alex/Nextcloud/rochester/course-planning.org", "/dev/stdout");
	}

}
