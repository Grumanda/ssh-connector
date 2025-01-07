package gui;

import management.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SSHCreatorFrame extends JFrameWithLogo {

    private GUI gui;
    private JLabel nameLabel;
    private JLabel commandLabel;
    private JLabel pwdLabel;
    private JTextField nameField;
    private JTextField commandField;
    private JPasswordField pwdField;
    private JButton saveButton;
    private JPanel contentPanel;

    public SSHCreatorFrame(GUI gui) {
        this.gui = gui;
        setTitle("SSH Connector -> Add connection");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(gui);
        setLayout(new BorderLayout());

        add(getContentPanel(), BorderLayout.CENTER);
        add(getSaveButton(), BorderLayout.SOUTH);

        getRootPane().setDefaultButton(getSaveButton());
        setVisible(true);
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.BOTH;

            nameLabel = new JLabel("Name:");
            commandLabel = new JLabel("Command:");
            pwdLabel = new JLabel("Password:");
            nameField = new JTextField();
            commandField = new JTextField();
            pwdField = new JPasswordField();

            Font font = new Font("Tahoma", Font.PLAIN, 20);
            nameLabel.setFont(font);
            commandLabel.setFont(font);
            pwdLabel.setFont(font);
            nameField.setFont(font);
            commandField.setFont(font);
            pwdField.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            contentPanel.add(nameLabel, gbc);
            gbc.gridy = 1;
            contentPanel.add(commandLabel, gbc);
            gbc.gridy = 2;
            contentPanel.add(pwdLabel, gbc);

            gbc.gridwidth = 2;
            gbc.weightx = 2;
            gbc.gridx = 1;
            gbc.gridy = 0;
            contentPanel.add(nameField, gbc);
            gbc.gridy = 1;
            contentPanel.add(commandField, gbc);
            gbc.gridy = 2;
            contentPanel.add(pwdField, gbc);
        }
        return contentPanel;
    }

    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton();
            saveButton.setText("Save");
            saveButton.setFont(new Font("Tahoma", Font.BOLD, 25));
            saveButton.addActionListener(this::saveButtonAction);
        }
        return saveButton;
    }

    private void saveButtonAction(ActionEvent event) {
        String name = nameField.getText();
        String command = commandField.getText();
        String pwd = pwdField.getText();

        Main.commander.addCommand(name, command, pwd);
        dispose();
        gui.reload();
    }
}
