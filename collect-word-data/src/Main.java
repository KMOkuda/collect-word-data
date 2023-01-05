import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws Exception {
		char firstAlphabet = 'a';
		String startPattern = "<dt>";
		String endPattern = "　";
		HashMap<String, ArrayList<String>> dataMap = new HashMap<String, ArrayList<String>>();

		for (int i = (int) firstAlphabet; i <= (int) firstAlphabet + 25; i++) {
			int count = 0;
			ArrayList<String> currentList = new ArrayList<String>();
			String urlString = "https://www.fe-siken.com/keyword/_" + (char) i + ".html";
			URL url = new URL(urlString);
			System.out.println("URL:" + url);
			BufferedReader read = new BufferedReader(
					new InputStreamReader(url.openStream(), "Shift_JIS"));

			int endJudger = 0;
			char j;
			StringBuffer sb = new StringBuffer();
			StringBuffer collectingWord = new StringBuffer();
			boolean isCollecting = false;


			while ((endJudger = read.read()) != -1) {
				j = (char)endJudger;
				//				System.out.print(j);
				sb.append(j);
				if (isCollecting) {
					if (sb.indexOf(endPattern) != -1) {
						count++;
//						System.out.println("equals.");
						System.out.println(collectingWord);
						currentList.add(collectingWord.toString());
						isCollecting = false;
						sb = new StringBuffer();
						collectingWord = new StringBuffer();

						System.out.println("------デバッグ------");
						for (String word : currentList) {
//							System.out.println("word:" + word);
						}
					} else {
						collectingWord.append(j);
						//						System.out.print(j);
					}

				} else {
					if (sb.indexOf(startPattern) != -1) {
//						System.out.println("found.");
						isCollecting = true;
						collectingWord = new StringBuffer();
						sb = new StringBuffer();
					}
				}
			}

			System.out.println("count:" + count);
			dataMap.put(Character.toString((char) i), currentList);
			read.close();

		}

		System.out.println("ループ終了");

		for (Map.Entry<String, ArrayList<String>> entry : dataMap.entrySet()) {
			ArrayList<String> tmpList = entry.getValue();
			for (String word : tmpList) {
				System.out.println("word:" + word);
			}
		}
	}
}
