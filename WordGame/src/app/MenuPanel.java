package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

    private MainFrame mainFrame;

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(16, 1)); // 그리드 레이아웃
        setBackground(new Color(51, 51, 51)); // 배경색 설정
        initMenuLabels(); // 메뉴 라벨 초기화
    }

    private void initMenuLabels() {
        // 스코어 패널 라벨
        JLabel score = new JLabel(new ImageIcon("images/coin.png"));
        add(score);
        score.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainFrame.setScorePanel(); // 패널 변경
            }
        });

        // 편집 패널 라벨
        JLabel edit = new JLabel(new ImageIcon("images/edit.png"));
        add(edit);
        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainFrame.setEditPanel(); // 패널 변경
            }
        });

        // 설정 패널 라벨
        JLabel setting = new JLabel(new ImageIcon("images/setting.png"));
        add(setting);
        setting.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainFrame.setSettingPanel(); // 패널 변경
            }
        });
    }
}
