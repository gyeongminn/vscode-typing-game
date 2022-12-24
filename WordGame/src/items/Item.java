package items;

import javax.swing.*;
import java.awt.*;

public abstract class Item { // 아이템 추상 클래스

    protected double x; // x좌표
    protected double y; // y좌표
    protected double speed; // 아이템이 내려가는 속도
    protected ImageIcon icon;
    protected JLabel label;

    public Item(double x, double y, double speed) { // 생성자
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public JLabel getLabel() { // 라벨 가져오기
        return label;
    }

    public double getX() { // x좌표 가져오기
        return x;
    }

    public double getY() { // y좌표 가져오기
        return y;
    }

    public void setY(double y) { // y좌표 설정
        this.y = y;
    }

    public double getSpeed() { // 속도 설정
        return speed;
    }
}
