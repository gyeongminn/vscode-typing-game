package items;

import javax.swing.*;

public class Coffee extends Item { // 커피 아이템

    public Coffee(double x, double y, double speed) {
        super(x, y, speed); // 슈퍼 클래스의 생성자 호출
        icon = new ImageIcon("images/coffee.png"); // 아이콘과 라벨만 설정
        label = new JLabel(icon);
    }
}
