package app;

import words.WordList;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MainFrame extends JFrame {

    private WordList wordList = new WordList();
    private ScorePanel scorePanel = new ScorePanel();
    private EditPanel editPanel = new EditPanel(wordList);
    private GamePanel gamePanel = new GamePanel(wordList, scorePanel);
    private MenuPanel menuPanel = new MenuPanel(this);
    private JPanel statusPanel = new JPanel();
    private SettingPanel settingPanel = new SettingPanel(gamePanel, wordList);
    private JSplitPane menuSplitPane = new JSplitPane();

    public MainFrame() {
        loadScore(); // 스코어 불러오기
        initMenuBar(); // 메뉴 바 초기화
        initSplitPane(); // 스플릿 팬 초기화
        initStatusPanel(); // 상태 패널 초기화
        initFrame(); // 메인 프레임 초기화
    }

    private void loadScore() { // 단어 불러오기
        try {
            Scanner scanner = new Scanner(new FileReader("data/data.txt"));
            while (scanner.hasNext()) {
                scorePanel.setScore(Integer.parseInt(scanner.nextLine().trim())); // 불러온 값을 스코어에 저장
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            gamePanel.saveScore(); // 파일이 없으면 새로 만들기
        }
    }

    private void initStatusPanel() {
        statusPanel.setLayout(new BorderLayout()); // 보더 레이아웃
        statusPanel.setBackground(new Color(62, 120, 201));

        // 이름 라벨 추가
        JLabel nameLabel = new JLabel("2171333 이경민 ");
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);
        statusPanel.add(nameLabel, BorderLayout.EAST);

        // 타이틀 라벨 추가
        JLabel titleLabel = new JLabel(" VSCode Typing Game");
        titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        titleLabel.setForeground(Color.WHITE);
        statusPanel.add(titleLabel, BorderLayout.WEST);
    }

    private void initFrame() {
        setTitle("VSCode Typing Game"); // 타이틀 설정
        setSize(1000, 800); // 메인 프레임 사이즈
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/vscode.png")); // 아이콘 설정
        setResizable(false); // 사이즈 조절 방지
        setVisible(true);
    }

    private void initMenuBar() { // 메뉴 바 초기화
        JMenuBar bar = new JMenuBar();
        bar.setBackground(new Color(60,60,60));
        bar.setBorder(BorderFactory.createEmptyBorder());
        this.setJMenuBar(bar);

        // 게임 메뉴 추가
        JMenu gameMenu = new JMenu("Game");
        initGameMenu(gameMenu);
        bar.add(gameMenu);

        // 보기 메뉴 추가
        JMenu viewMenu = new JMenu("View");
        initViewMenu(viewMenu);
        bar.add(viewMenu);

        // 언어 선택 메뉴 추가
        JMenu languageMenu = new JMenu("Language");
        initLanguageMenu(languageMenu);
        bar.add(languageMenu);

        // 레벨 선택 메뉴 추가
        JMenu levelMenu = new JMenu("Level");
        initLevelMenu(levelMenu);
        bar.add(levelMenu);
    }

    private void initLevelMenu(JMenu menu) { // 언어 메뉴 초기화
        menu.setForeground(Color.WHITE);
        JMenuItem lv1 = new JMenuItem("Level 1");
        menu.add(lv1);
        lv1.addActionListener(e -> settingPanel.setLv1());

        JMenuItem lv2 = new JMenuItem("Level 2");
        menu.add(lv2);
        lv2.addActionListener(e -> settingPanel.setLv2());

        JMenuItem lv3 = new JMenuItem("Level 3");
        menu.add(lv3);
        lv3.addActionListener(e -> settingPanel.setLv3());
    }

    private void initLanguageMenu(JMenu languageMenu) { // 언어 메뉴 초기화
        languageMenu.setForeground(Color.WHITE);
        JMenuItem clang = new JMenuItem("C/C++");
        languageMenu.add(clang);
        clang.addActionListener(e -> settingPanel.setLanguageC());

        JMenuItem java = new JMenuItem("Java");
        languageMenu.add(java);
        java.addActionListener(e -> settingPanel.setLanguageJava());

        JMenuItem python = new JMenuItem("Python");
        languageMenu.add(python);
        python.addActionListener(e -> settingPanel.setLanguagePython());
    }



    private void initViewMenu(JMenu editMenu) { // 보기 메뉴 추가
        editMenu.setForeground(Color.WHITE);

        JMenuItem show = new JMenuItem("Score");
        show.addActionListener(e -> setScorePanel()); // 마우스 이벤트 추가
        editMenu.add(show);

        JMenuItem edit = new JMenuItem("Edit");
        edit.addActionListener(e -> setEditPanel()); // 마우스 이벤트 추가
        editMenu.add(edit);

        JMenuItem setting = new JMenuItem("Settings");
        setting.addActionListener(e -> setSettingPanel()); // 마우스 이벤트 추가
        editMenu.add(setting);
    }

    private void initGameMenu(JMenu gameMenu) { // 게임 메뉴 추가
        gameMenu.setForeground(Color.WHITE);

        JMenuItem startGame = new JMenuItem("Start Game");
        startGame.addActionListener(e -> gamePanel.startGame()); // 마우스 이벤트 추가
        gameMenu.add(startGame);

        JMenuItem pauseGame = new JMenuItem("Pause Game");
        pauseGame.addActionListener(e -> gamePanel.pauseGame()); // 마우스 이벤트 추가
        gameMenu.add(pauseGame);

        JMenuItem stopGame = new JMenuItem("Stop Game");
        stopGame.addActionListener(e -> gamePanel.gameOver()); // 마우스 이벤트 추가
        gameMenu.add(stopGame);
        gameMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(1));  // 마우스 이벤트 추가. 게임 종료
        gameMenu.add(exitItem);
    }

    private void initSplitPane() {
        JSplitPane verticalPane = new JSplitPane(); // 하단 StatusPanel 나누는 SplitPane
        verticalPane.setDividerSize(0);
        verticalPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        verticalPane.setDividerLocation(720);
        verticalPane.setBorder(BorderFactory.createEmptyBorder());

        JSplitPane horizontalPane = new JSplitPane(); // 좌측 패널과 우측 GamePanel 나누는 SplitPane
        horizontalPane.setDividerSize(0);
        horizontalPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        horizontalPane.setDividerLocation(300);
        horizontalPane.setBorder(BorderFactory.createEmptyBorder());

        // 좌측 MenuPanel 과 우측 패널 나누는 SplitPane
        menuSplitPane.setDividerSize(0);
        menuSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        menuSplitPane.setDividerLocation(50);
        menuSplitPane.setBorder(BorderFactory.createEmptyBorder());

        // SplitPane 설정
        getContentPane().add(verticalPane,BorderLayout.CENTER);
        verticalPane.setTopComponent(horizontalPane);
        verticalPane.setBottomComponent(statusPanel);
        horizontalPane.setLeftComponent(menuSplitPane);
        horizontalPane.setRightComponent(gamePanel);
        menuSplitPane.setLeftComponent(menuPanel);
        menuSplitPane.setRightComponent(scorePanel);
    }

    public void setScorePanel() { // 패널 변경
        menuSplitPane.setRightComponent(scorePanel);
        menuSplitPane.setDividerLocation(50);
    }

    public void setEditPanel() { // 패널 변경
        menuSplitPane.setRightComponent(editPanel);
        menuSplitPane.setDividerLocation(50);
    }

    public void setSettingPanel() { // 패널 변경
        menuSplitPane.setRightComponent(settingPanel);
        menuSplitPane.setDividerLocation(50);
    }
}
