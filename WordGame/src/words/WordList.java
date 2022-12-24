package words;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class WordList {

    private Vector<String> words = new Vector<>(1000); // 단어가 저장될 벡터

    public WordList() {
        loadWordFile("data/clang.txt"); // 기본 언어 불러오기
    }

    public void loadWordFile(String file) {
        words.removeAllElements(); // 단어 리스트 초기화
        try {
            Scanner scanner = new Scanner(new FileReader(file)); // 파일 읽기
            while (scanner.hasNext()) {
                words.add(scanner.nextLine()); // 벡터에 단어 추가
            }
            scanner.close();
        } catch (FileNotFoundException e) { // 파일 찾기 실패
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Vector<String> getAllWords() { // words 벡터 가저오기
        return words;
    }

    public String getWord() { // 랜덤한 단어 가져오기
        int index = (int) (Math.random() * words.size());  // 랜덤 인덱스
        return words.get(index);
    }

    public void addWord(String word) {
        words.add(word.trim());
        try {
            FileWriter fw = new FileWriter("data/clang.txt", true); // 파일 쓰기
            fw.write(word.trim() + "\n"); // 파일에 단어 쓰기
            fw.flush();
        } catch (IOException e) { // 단어 입력 실패
            e.printStackTrace();
            System.exit(1);
        }
    }
}