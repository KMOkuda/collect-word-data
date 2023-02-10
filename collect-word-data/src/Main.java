import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Word implements Comparable<Word>{
	String KW;
	int category;
	int level;

	public Word(String kW, int category, int level) {
		super();
		KW = kW;
		this.category = category;
		this.level = level;
	}

	public String getKW() {
		return KW;
	}
	public void setKW(String kW) {
		KW = kW;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int compareTo(Word word) {
		if(this.category < word.category) {
			return -1;
		}else if(this.category > word.category) {
			return 1;
		}else if(this.level < word.level) {
			return -1;
		}else if (this.level > word.level) {
			return 1;
		}else {
			return 0;
		}
	}
}

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		List<Word> wordList = new ArrayList<Word>();
		try {
			File file = new File("word-list-test.txt");

			if (file.exists()) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String content;
				while ((content = br.readLine()) != null) {
					System.out.println(content);
					System.out.println("カテゴリーを選択");
					System.out.println("1:hardware 2:software 3:DB 4:network 5:security 6:design 7:management 8:strategy 9:?");
					int categoryId = sc.nextInt();
					System.out.println("レベルを選択");
					System.out.println("1, 2, 3, 4(?)");
					int level = sc.nextInt();

					wordList.add(new Word(content, categoryId, level));
				}
				br.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Collections.sort(wordList);

		for(Word word : wordList) {
			System.out.println(word.getKW() + word.getCategory() + word.getLevel());
		}
	}
}
