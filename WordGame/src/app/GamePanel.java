package app;

import items.*;
import words.Word;
import words.WordList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class GamePanel extends JPanel { // 게임이 진행되는 Panel

    private final int LABEL_WIDTH = 150; // 단어 라벨 너비
    private final int LABEL_HEIGHT = 40; // 단어 라벨 높이

    private WordList wordList;
    private Vector<Word> currentWords; // 현재 화면에 보여지는 단어들
    private Vector<Item> currentItems; // 현재 화면에 보여지는 아이템들
    private GroundPanel groundPanel;
    private ScorePanel scorePanel;
    private JLabel comboLabel;
    private JLabel gameOverLabel;

    private GameThread gameThread = null; // 게임 진행 쓰레드
    private ItemThread itemThread = null; // 아이템 처리 쓰레드
    private JProgressBar healthBar;

    private double divDelay = 5; // 딜레이를 나눌 값. 값이 낮을수록 느리게 떨어진다
    private int combo = 1; // 콤보 횟수
    private int boost = 1; // 획득하는 점수의 배율
    private Color recentColor = null; // 최근 입력된 컬러. 콤보 계산할 때 쓰인다
    private int healthPoint = 0; // 체력 포인트
    private boolean paused = false; // 멈춤 상태 플래그

    // 아이템 플래그
    private boolean coffeeFlag = false;
    private boolean energyDrinkFlag = false;
    private boolean googleFlag = false;
    private boolean napFlag = false;

    public GamePanel(WordList wordList, ScorePanel scorePanel) { // 생성자
        this.wordList = wordList;
        this.scorePanel = scorePanel;

        // 라벨, 벡터 초기화
        currentWords = new Vector<>(30);
        currentItems = new Vector<>(30);
        gameOverLabel = new JLabel("GAME OVER");

        groundPanel = new GroundPanel();
        initGroundPanel(); // GroundPanel 초기화

        JPanel inputPanel = new JPanel(); // Terminal Panel 추가
        add(inputPanel, BorderLayout.SOUTH);

        comboLabel = new JLabel("Combo"); // comboLabel 설정
        initComboLabel(comboLabel);

        initTerminalPanel(scorePanel, inputPanel, comboLabel); // TerminalPanel 초기화
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(71, 71, 71));
        g.drawRect(0, 572, 700, 1); // TerminalPanel 구분 선 그리기
    }

    private void initGroundPanel() {
        groundPanel.setBackground(new Color(30, 30, 30)); // 배경 색
        groundPanel.setBorder(BorderFactory.createEmptyBorder()); // 테두리 제거

        //레이아웃 설정
        setLayout(new BorderLayout());
        add(groundPanel, BorderLayout.CENTER);

        // 아이템 클릭 이벤트 처리
        groundPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                for (Item item : currentItems) {
                    // 클릭한 아이템과 좌표가 일치하면
                    if ((e.getX() <= item.getX() + 50 && item.getX() < e.getX() + 25) &&
                            (e.getY() <= item.getY() + 50 && item.getY() < e.getY() + 25)) {
                        // 플래그 변경
                        if (item instanceof Coffee) {
                            coffeeFlag = true;
                        } else if (item instanceof EnergyDrink) {
                            energyDrinkFlag = true;
                        } else if (item instanceof Google) {
                            googleFlag = true;
                        } else if (item instanceof Nap) {
                            napFlag = true;
                        }
                        item.setY(10000); // 아이템 제거. 제거는 Thread 에서 일괄 처리한다
                        scorePanel.addItemCount(); // 아이템 count 증가
                        break;
                    }
                }
            }
        });

    }

    private void initTerminalPanel(ScorePanel scorePanel, JPanel inputPanel, JLabel comboLabel) {
        inputPanel.setLayout(null); // 레이아웃 설정
        inputPanel.setBackground(new Color(30, 30, 30)); // 색 설정
        inputPanel.setBorder(BorderFactory.createEmptyBorder()); // 테두리 제거
        inputPanel.setPreferredSize(new Dimension(30, 150));

        JLabel terminalLabel = new JLabel("Terminal"); // 터미널 라벨
        terminalLabel.setFont(new Font("consolas", Font.PLAIN, 16));
        terminalLabel.setBounds(20, 4, 100, 40);
        terminalLabel.setForeground(Color.WHITE);
        inputPanel.add(terminalLabel);

        JLabel dirLabel = new JLabel("C:\\Users> "); // 디렉터리 라벨
        dirLabel.setFont(new Font("consolas", Font.PLAIN, 18));
        dirLabel.setBounds(20, 4, 130, 100);
        dirLabel.setForeground(Color.WHITE);
        inputPanel.add(dirLabel);

        JTextField inputField = new JTextField(); // 텍스트 입력 필드
        inputField.setFont(new Font("consolas", Font.PLAIN, 18));
        inputField.setBounds(120, 4, 500, 100);
        inputField.setBackground(new Color(30, 30, 30));
        inputField.setBorder(BorderFactory.createEmptyBorder()); // 테두리 제거
        inputField.setForeground(Color.YELLOW);
        inputPanel.add(inputField);

        // 텍스트 입력 필드 키보드 이벤트 추가
        inputField.addActionListener(e -> {
            JTextField textField = (JTextField) e.getSource();
            command(textField.getText());
            for (Word word : currentWords) {
                if (textField.getText().equals(word.getName())) { // 현재 단어 중 입력한 단어와 일치하는 경우
                    if (recentColor == word.getColor()) { // 콤보 판정
                        combo++;
                        if (combo > 1) {
                            comboLabel.setText("Combo " + combo + "x!");
                            comboLabel.setVisible(true);
                        }
                    } else { // 콤포 실패
                        combo = 1;
                        comboLabel.setVisible(false);
                    }
                    recentColor = word.getColor(); // 콤보 판정을 위해 이전 컬러 저장
                    scorePanel.addScore(10 * combo * boost); // 점수 추가
                    scorePanel.addWordCount(); // 단어 count 증가
                    word.setY(10000); // 단어 제거. 제거는 Thread 에서 일괄 처리한다
                    break;
                }
            }
            textField.setText("");
        });


    }

    private void command(String command) {
        // 커맨드 패턴
        if (command.equals("start")) {
            startGame();
        } else if (command.equals("pause")) {
            pauseGame();
        } else if (command.equals("stop")) {
            gameOver();
        } else if (command.equals("exit")) {
            System.exit(0);
        } else if (command.equals("set level 1")) {
            setDivDelay(5);
        } else if (command.equals("set level 2")) {
            setDivDelay(3.5);
        } else if (command.equals("set level 3")) {
            setDivDelay(1);
        }
    }

    private void initComboLabel(JLabel comboLabel) { // 콤보 라벨 초기화
        comboLabel.setFont(new Font("consolas", Font.PLAIN, 20));
        comboLabel.setForeground(Color.WHITE);
        comboLabel.setLocation(550, 50);
        comboLabel.setSize(150, 20);
        comboLabel.setVisible(false);
        groundPanel.add(comboLabel);
    }

    public void startGame() { // 게임 시작
        gameOverLabel.setVisible(false); // 게임 오버 라벨 안보이게
        groundPanel.initHealthBar(); // 체력바 초기화
        paused = false; // resume

        // 쓰레드 초기화, 시작
        gameThread = new GameThread();
        gameThread.start();
        itemThread = new ItemThread();
        itemThread.start();
    }

    public void pauseGame() {
        paused = true; // 플래그 변경
    }

    public void setDivDelay(double divDelay) {
        this.divDelay = divDelay;
    }

    private void addItem() { // 아이템 추가
        int x = (int) (Math.random() * (groundPanel.getWidth() - LABEL_WIDTH / 2)); // 랜덤 좌표
        double speed = Math.random() / 20 + 0.1; // 일정 범위 내 랜덤한 속도를 가진다
        Item item;
        switch ((int) (Math.random() * 7)) { // 랜덤 아이템
            case 0 -> item = new Coffee(x, 0, speed);
            case 1 -> item = new EnergyDrink(x, 0, speed);
            case 2 -> item = new Google(x, 0, speed);
            case 3 -> item = new Nap(x, 0, speed);
            case 4 -> item = new Google(x, 0, speed);
            case 5 -> item = new Nap(x, 0, speed);
            case 6 -> item = new Google(x, 0, speed);
            case 7 -> item = new Nap(x, 0, speed);
            default -> throw new IllegalStateException();
        }
        currentItems.add(item); // 벡터에 아이템 추가

        // 아이템 라벨 추가
        JLabel label = item.getLabel();
        label.setSize(50, 50);
        label.setLocation(getX(), 0);
        groundPanel.add(label);
    }

    private void addWord() {
        int x = (int) (Math.random() * (groundPanel.getWidth() - LABEL_WIDTH / 2)); // 랜덤 좌표
        double speed = Math.random() / 20 + 0.1; // 일점 범위 내 랜덤한 속도를 가진다.
        Word word = new Word(wordList.getWord(), x, 0, speed);
        currentWords.add(word); // 벡터에 단어 추가

        // 단어 라벨 추가
        JLabel label = word.getLabel();
        label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        label.setLocation(getX(), 0);
        label.setFont(new Font("consolas", Font.PLAIN, 20));
        label.setForeground(word.getColor());
        groundPanel.add(label);
    }

    public void setItems() {
        Iterator<Item> iterator = currentItems.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            item.setY(item.getY() + (item.getSpeed() / divDelay)); // 아이템 위치 재설정
            JLabel label = item.getLabel();
            label.setLocation((int) (item.getX()), (int) item.getY()); // 아이템 라벨 위치 재설정

            // 아이템이 떨어진 경우 삭제 처리
            if (label.getY() >= groundPanel.getHeight()) {
                groundPanel.remove(label);
                iterator.remove();
            }
        }
    }

    public void setWords() {
        Iterator<Word> iterator = currentWords.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            word.setY(word.getY() + (word.getSpeed() / divDelay)); // 단어 위치 재설정
            JLabel label = word.getLabel();
            label.setLocation((int) word.getX(), (int) word.getY()); // 단어 라벨 위치 재설정

            // 단어가 떨어진 경우 삭제 처리
            if (label.getY() >= groundPanel.getHeight()) {
                if (label.getY() < 10000) { // 타이핑하지 못하고 떨어진 경우
                    healthPoint -= 10;
                }
                groundPanel.remove(label);
                iterator.remove();
            }
        }
    }

    public void gameOver() {
        saveScore();

        if (gameThread == null) {
            return; // 게임이 시작된 적이 없다면 return
        }
        pauseGame(); // 게임 멈추기
        gameThread.interrupt(); // 쓰레드 종료
        itemThread.interrupt();

        // 단어, 아이템 삭제
        for (Word word : currentWords) {
            word.getLabel().setVisible(false);
            groundPanel.remove(word.getLabel());
        }
        currentWords.removeAllElements();
        for (Item item : currentItems) {
            item.getLabel().setVisible(false);
            groundPanel.remove(item.getLabel());
        }
        currentItems.removeAllElements();
        healthBar.setVisible(false);
        comboLabel.setVisible(false);

        //게임 오버 라벨 추가
        gameOverLabel.setFont(new Font("consolas", Font.PLAIN, 100));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setBounds(100, 100, 500, 300);
        groundPanel.add(gameOverLabel);
        gameOverLabel.setVisible(true);
    }

    public void saveScore() { // 스코어 저장
        try {
            FileWriter fw = new FileWriter("data/data.txt");
            fw.write(Integer.toString(scorePanel.getScore())); // 파일에 스코어 String 으로 저장
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class GroundPanel extends JPanel { // 단어 떨어지는 패널

        public GroundPanel() {
            setLayout(null);
            setBackground(Color.GRAY);
        }

        public void initHealthBar() { // 체력바 초기화
            healthBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
            healthPoint = 100; // 체력 100으로 설정
            healthBar.setForeground(Color.RED);
            healthBar.setLocation(100, 20);
            healthBar.setSize(500, 20);
            add(healthBar);
        }
    }

    class GameThread extends Thread {

        public GameThread() {
            super("GameThread");
        }

        @Override
        public void run() {
            long count = 0;
            while (true) {
                try {
                    if (paused) { // pause 상태인 경우
                        sleep(1000);
                        continue;
                    } else {
                        sleep(1);
                    }
                } catch (InterruptedException e) {
                    return;
                }
                if (count % 1000 == 0) {
                    if (!energyDrinkFlag && !coffeeFlag) {
                        healthPoint--;
                    }
                }
                if (count % (700 * divDelay) == 0) {
                    addWord(); // 단어 추가
                }
                count++;
                if (count % 10000 == 0) {
                    addItem();
                }
                setWords(); // 단어 위치 재설정
                setItems(); // 아이템 위치 재설정
                healthBar.setValue(healthPoint); // 체력 바 설정
                if (healthPoint <= 0) {
                    gameOver(); // 체력바가 0이면 게임 종료
                }
            }
        }
    }

    class ItemThread extends Thread { // 아이템 처리 쓰레드

        @Override
        public void run() {
            long count = 1;
            while (true) {
                try {
                    if (coffeeFlag) { // 커피 아이템
                        boost = 2; // 점수 배율 2배
                        if (count % 100 == 0) { // 10초 이후 아이템 효과 종료
                            coffeeFlag = false;
                            boost = 1;
                            count = 1;
                        }
                    }
                    if (energyDrinkFlag) { // 에너지드링크 아이템
                        boost = 3; // 점수 배율 3배
                        if (count % 100 == 0) { // 10초 이후 아이템 효과 종료
                            boost = 1;
                            energyDrinkFlag = false;
                            count = 1;
                        }
                    }
                    if (googleFlag) { // 구글 아이템
                        for (Word word : currentWords) {
                            word.setY(10000); // 단어 지우기. 삭제는 쓰레드에서 일괄 처리
                            scorePanel.addScore((int) (10 * boost * 0.5)); // 점수 1/2 추가
                        }
                        googleFlag = false;
                    }
                    if (napFlag) { // 낮잠 아이템
                        healthPoint += 20; // 체력 추가
                        if (healthPoint > 100) {
                            healthPoint = 100;
                        }
                        divDelay = 10; // 떨어지는 속도 느리게
                        if (count % 50 == 0) { // 5초 이후 아이템 효과 종료
                            divDelay = 2;
                            napFlag = false;
                            count = 1;
                        }
                    }
                    count++;
                    sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
