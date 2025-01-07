package gui;

import javax.swing.*;

public class JFrameWithLogo extends JFrame {

    public JFrameWithLogo() {
        ImageIcon imageIcon = new ImageIcon(JFrameWithLogo.class.getResource("/logo.png"));
        setIconImage(imageIcon.getImage());
    }
}
