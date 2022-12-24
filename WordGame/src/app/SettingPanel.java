package app;

import words.WordList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingPanel extends JPanel {

    private GamePanel gamePanel;
    private WordList wordList;
    private JRadioButton lv1;
    private JRadioButton lv2;
    private JRadioButton lv3;
    protected JRadioButton clang;
    protected JRadioButton java;
    protected JRadioButton python;

    public SettingPanel(GamePanel gamePanel, WordList wordList) {
        this.wordList = wordList;
        this.gamePanel = gamePanel;
        setLayout(null); // 레이아웃 초기화
        setBackground(new Color(37, 37, 37)); // 배경 색 설정
        initLanguageSetting(); // 단어 설정 초기화
        initLevelSetting(); // 레벨 설정 초기화
    }

    private void initLanguageSetting() {
        // 라벨 추가
        JLabel titleLabel = new JLabel("Select Language");
        titleLabel.setForeground(Color.WHITE); // 글자 색 설정
        titleLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        titleLabel.setBounds(20, 20, 200, 24);
        add(titleLabel);

        ButtonGroup group = new ButtonGroup(); // 버튼 그룹 설정

        // C언어 선택 버튼
        clang = new JRadioButton("C/C++");
        initRadioButton(group, clang); // 라디오 버튼 초기화
        clang.setBackground(new Color(37, 37, 37));
        clang.setBounds(20, 50, 100, 30);
        clang.setSelected(true); // 선택 초기값으로 설정
        clang.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                wordList.loadWordFile("clang.txt"); // 단어 파일 불러오기
            }
        });

        // JAVA 선택 버튼
        java = new JRadioButton("Java");
        initRadioButton(group, java);
        java.setBackground(new Color(37, 37, 37));
        java.setBounds(20, 80, 100, 30);
        java.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                wordList.loadWordFile("data/java.txt"); // 단어 파일 불러오기
            }
        });

        // Python 선택 버튼
        python = new JRadioButton("Python");
        initRadioButton(group, python);
        python.setBackground(new Color(37, 37, 37));
        python.setBounds(20, 110, 100, 30);
        python.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                wordList.loadWordFile("data/python.txt"); // 단어 파일 불러오기
            }
        });
    }

    private void initLevelSetting() {
        // 라벨 추가
        JLabel titleLabel = new JLabel("Select Level");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        titleLabel.setBounds(20, 170, 200, 24);
        add(titleLabel);

        ButtonGroup group = new ButtonGroup(); // 버튼 그룹 설정

        lv1 = new JRadioButton("Level 1");
        initRadioButton(group, lv1);
        lv1.setSelected(true); // 선택 초기값으로 설정
        lv1.setBackground(new Color(37, 37, 37));
        lv1.setBounds(20, 200, 100, 30);
        lv1.addActionListener(e -> setLv1()); // 레벨 1 설정

        lv2 = new JRadioButton("Level 2");
        initRadioButton(group, lv2);
        lv2.setBackground(new Color(37, 37, 37));
        lv2.setBounds(20, 230, 100, 30);
        lv2.addActionListener(e -> setLv2()); // 레벨 2 설정

        lv3 = new JRadioButton("Level 3");
        initRadioButton(group, lv3);
        lv3.setBackground(new Color(37, 37, 37));
        lv3.setBounds(20, 260, 100, 30);
        lv3.addActionListener(e -> setLv3()); // 레벨 3 설정
    }

    public void setLv1() { // 레벨 설정
        gamePanel.setDivDelay(5);
        lv1.setSelected(true);
    }

    public void setLv2() { // 레벨 설정
        gamePanel.setDivDelay(3.5);
        lv2.setSelected(true);
    }

    public void setLv3() { // 레벨 설정
        gamePanel.setDivDelay(1);
        lv3.setSelected(true);
    }

    public void setLanguagePython() { // 언어 설정
        wordList.loadWordFile("data/python.txt");
        python.setSelected(true);
    }

    public void setLanguageJava() { // 언어 설정
        wordList.loadWordFile("data/java.txt");
        java.setSelected(true);
    }

    public void setLanguageC() { // 언어 설정
        wordList.loadWordFile("data/clang.txt");
        clang.setSelected(true);
    }

    private void initRadioButton(ButtonGroup group, JRadioButton radioButton) {
        radioButton.setForeground(Color.WHITE); // 글자 색 설정
        radioButton.setFont(new Font("consolas", Font.PLAIN, 16)); // 폰트 설정
        group.add(radioButton); // 그룹에 라디오버튼 추가
        add(radioButton); // 패널에 라디오버튼 추가
    }
}
