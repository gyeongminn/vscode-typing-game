package app;

import words.WordList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditPanel extends JPanel { // 단어 관리하는 패널

    private WordList wordList;
    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton saveBtn = new JButton("Save");

    public EditPanel(WordList wordList) {
        this.wordList = wordList;
        initEditPanel(); // EditPane 초기화
    }

    private void initEditPanel() {
        setLayout(null);
        setBackground(new Color(37, 37, 37)); // 배경색 설정

        initLabels();
        initTextField(); // 텍스트 필드 초기화
        initTextArea(); // TextArea 초기화
        initScrollPane(); // 스크롤 팬 초기화
        initSaveBtn(); // 단어 저장 버튼 초기화
    }

    private void initLabels() {
        JLabel viewLabel = new JLabel("View All Words");
        viewLabel.setForeground(Color.WHITE);
        viewLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        viewLabel.setBounds(20, 20, 200, 20);
        add(viewLabel);

        JLabel addLabel = new JLabel("Add Word");
        addLabel.setForeground(Color.WHITE);
        addLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        addLabel.setBounds(20, 470, 200, 20);
        add(addLabel);
    }

    private void initSaveBtn() {
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setBackground(new Color(71, 71, 71));
        saveBtn.setBorder(BorderFactory.createEmptyBorder());
        saveBtn.setBounds(170, 500, 70, 20);
        add(saveBtn);

        // 단어 입력 처리
        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                    String word = textField.getText().trim(); // 공백 제거
                    textArea.append(word + "\n"); // 단어 추가
                    wordList.addWord(word);
                    textField.setText(""); // 입력 필드 초기화
            }
        });
    }

    private void initScrollPane() { // ScrollPane 에 TextArea 를 붙여 스크롤이 가능하게 한다.
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(20, 50, 220, 400);
        add(scrollPane);
    }

    private void initTextField() { // 단어 입력하는 TextField
        textField = new JTextField(20);
        textField.setBackground(new Color(30, 30, 30));
        textField.setBorder(new LineBorder(new Color(71, 71, 71)));
        textField.setBounds(20, 500, 140, 20);
        textField.setForeground(Color.WHITE);
        add(textField);
    }

    private void initTextArea() { // 선택된 언어의 모든 단어를 보여주는 TextArea
        textArea = new JTextArea();
        textArea.setBackground(new Color(37, 37, 37));
        textArea.setForeground(Color.WHITE);
        // 단어 추가
        for (String word : wordList.getAllWords()) {
            textArea.append(word + "\n");
        }
    }
}
