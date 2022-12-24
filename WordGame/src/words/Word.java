package words;

import javax.swing.*;
import java.awt.*;

public class Word {

    private String name; // 단어 이름
    private double x; // x좌표
    private double y; // y좌표
    private double speed; // 단어가 내려가는 속도
    private Color color; // 단어 색상
    private JLabel label;

    public Word(String word, int x, int y, double speed) { // 생성자
        this.name = word;
        this.x = x;
        this.y = y;
        this.speed = speed;
        label = new JLabel(word);
        color = getRandomColor(); // 랜덤 색상
    }

    private Color getRandomColor() { // 랜덤 색상 가져오기
        switch ((int) (Math.random() * 5)) {
            case 0 -> {
                return Color.RED;
            }
            case 1 -> {
                return Color.GREEN;
            }
            case 2 -> {
                return Color.BLUE;
            }
            default -> {
                return Color.WHITE;
            }
        }
    }

    public Color getColor() { // 색상 가져오기
        return color;
    }

    public String getName() { // 단어 이름 가져오기
        return name;
    }

    public double getX() { // x좌표 가져오기
        return x;
    }

    public double getY() { // y좌표 가져오기
        return y;
    }

    public double getSpeed() { // 속도 가져오기
        return speed;
    }

    public JLabel getLabel() { // 라벨 가져오기
        return label;
    }

    public void setY(double y) { // y좌표 설정
        this.y = y;
    }
}
