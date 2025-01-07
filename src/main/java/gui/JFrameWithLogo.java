package gui;

import javax.swing.*;

/**
 * This class only adds the icon to the JFrame.
 *
 * @author Grumanda
 */
public class JFrameWithLogo extends JFrame {

    public JFrameWithLogo() {
        ImageIcon imageIcon = new ImageIcon(JFrameWithLogo.class.getResource("/logo.png"));
        setIconImage(imageIcon.getImage());
    }
}
