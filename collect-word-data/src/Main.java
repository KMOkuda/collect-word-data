import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class Word {
	String word;
	String category;

	public Word(String word, String category) {
		this.word = word;
		this.category = category;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	};

}

public class Main {
	public static void main(String[] args) throws Exception {
		char firstAlphabet = 'a';
		String startPattern = "<dt>";
		String endPattern = "　";

		//アルファベットの頭文字とそれに紐づく単語を格納する
		HashMap<String, ArrayList<Word>> dataMap = new LinkedHashMap<String, ArrayList<Word>>();

		ArrayList<String> initials = new ArrayList<String>();

		for (int i = (int) firstAlphabet; i <= (int) firstAlphabet + 25; i++) {
			initials.add("_" + Character.toString((char) i));
		}

		/**
		initials.addAll(Arrays.asList(
		"xa", "xi", "xu", "xe", "xo",
		"ka", "ki", "ku", "ke", "ko",
		"sa", "si", "su", "se", "so",
		"ta", "ti", "tu", "te", "to",
		"na", "ni", "nu", "ne", "no",
		"ha", "hi", "hu", "he", "ho",
		"ma", "mi", "mu", "me", "mo",
		"ya", "yu", "yo", "wa"
		));
		**/

		for (String al : initials) {

			//同じ頭文字の単語を格納する
			ArrayList<Word> currentList = new ArrayList<Word>();

			String urlString = "https://www.fe-siken.com/keyword/" + al + ".html";
			URL url = new URL(urlString);

			BufferedReader read = new BufferedReader(
					new InputStreamReader(url.openStream(), "Shift_JIS"));

			//読み込みが終了するとendJudgerが-1になる
			int endJudger = 0;

			char singleWord;
			StringBuffer sb = new StringBuffer();
			StringBuffer collectingWord = new StringBuffer();
			boolean isCollecting = false;

			while ((endJudger = read.read()) != -1) {
				singleWord = (char) endJudger;
				sb.append(singleWord);

				if (isCollecting) {
					if (sb.indexOf(endPattern) != -1) {

						currentList.add(new Word(collectingWord.toString(), "カテゴリー"));
						isCollecting = false;
						sb = new StringBuffer();
						collectingWord = new StringBuffer();

					} else {
						collectingWord.append(singleWord);
					}

				} else {
					if (sb.indexOf(startPattern) != -1) {
						isCollecting = true;
						collectingWord = new StringBuffer();
						sb = new StringBuffer();
					}
				}
			}

			dataMap.put(al, currentList);
			read.close();

		}

		System.out.println("ループ終了");

		FileOutputStream fo = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try {
			fo = new FileOutputStream("output.csv");
			osw = new OutputStreamWriter(fo, "SHIFT_JIS");
			bw = new BufferedWriter(osw);

			for (Map.Entry<String, ArrayList<Word>> entry : dataMap.entrySet()) {
				ArrayList<Word> tmpList = entry.getValue();
				System.out.println("-----------------------" + entry.getKey() + "------------------------");
				for (Word word : tmpList) {
					System.out.println("word:" + word.getWord());
					bw.write(word.getWord());
					bw.write("\n");
				}
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String al : initials) {
			System.out.println(al);
		}
	}
}
