package app;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel { // 스코어 패널

    private int score = 0;
    private int wordCount = 0;
    private int itemCount = 0;

    private JLabel scoreLabel;
    private JLabel wordCountLabel;
    private JLabel itemCountLabel;

    public ScorePanel() {
        setLayout(null);
        setBackground(new Color(37, 37, 37));
        initLabels(); // 라벨 초기화
    }

    private void initLabels() {
        score = 0; // 점수
        scoreLabel = new JLabel("Gold : 0G");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        scoreLabel.setBounds(20, 20, 200, 20);
        add(scoreLabel);

        wordCount = 0; // 입력된 단어 횟수
        wordCountLabel = new JLabel("Entered Word : 0");
        wordCountLabel.setForeground(Color.WHITE);
        wordCountLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        wordCountLabel.setBounds(20, 60, 200, 20);
        add(wordCountLabel);

        itemCount = 0; // 사용한 아이템 횟수
        itemCountLabel = new JLabel("Used item : 0");
        itemCountLabel.setForeground(Color.WHITE);
        itemCountLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        itemCountLabel.setBounds(20, 100, 200, 20);
        add(itemCountLabel);
    }

    public void setScore(int score) { // 스코어 설정
        this.score = score;
        scoreLabel.setText("Gold : " + score + "G"); // 라벨 텍스트도 변경
    }

    public int getScore() { // 스코어 가져오기
        return score;
    }

    public void addScore(int point) {
        setScore(score + point); // point 만큼 스코어 증가
    }

    public void addWordCount() {
        wordCountLabel.setText("Entered Word : " + ++wordCount); // 입력된 단어 횟수 증가
    }

    public void addItemCount() {
        itemCountLabel.setText("Used item : " + ++itemCount); // 사용된 아이템 횟수 증가
    }
}
