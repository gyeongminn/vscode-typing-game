package items;

import javax.swing.*;

public class Google extends Item {

    public Google(double x, double y, double speed) {
        super(x, y, speed); // 슈퍼 클래스의 생성자 호출
        icon = new ImageIcon("images/google.png"); // 아이콘과 라벨만 설정
        label = new JLabel(icon);
    }
}
