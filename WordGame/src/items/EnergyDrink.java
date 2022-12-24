package items;

import javax.swing.*;

public class EnergyDrink extends Item {

    public EnergyDrink(double x, double y, double speed) {
        super(x, y, speed); // 슈퍼 클래스 생성자 호출
        icon = new ImageIcon("images/redbull.png"); // 아이콘과 라벨만 설정
        label = new JLabel(icon);
    }
}
