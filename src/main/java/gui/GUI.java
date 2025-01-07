package gui;

import management.Datensatz;
import management.Main;
import management.SSHConnector;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrameWithLogo {

    private GUI instance;
    private JPanel centerPanel;
    private JComboBox<Datensatz> comboBox;
    private JButton connectButton;
    private JMenuBar menuBar;
    private JLabel commandLabel;
    private JLabel commandInputLabel;
    private JLabel passwordLabel;
    private JLabel passwordInputLabel;
    private JButton showPasswordButton;
    private boolean isPasswordShown;

    public GUI() {
        isPasswordShown = false;
        this.instance = this;
        setTitle("SSH Connector");
        setLocation(100, 50);
        setSize(750, 750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(getMenuBarGUI());

        // Add components
        add(getComboBox(), BorderLayout.NORTH);
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getConnectButton(), BorderLayout.SOUTH);
        setVisible(true);
    }

    private JMenuBar getMenuBarGUI() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            JMenu menu = new JMenu("Settings");
            JMenuItem menuItemAdd = new JMenuItem("Add ssh connection");
            menuItemAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SSHCreatorFrame(instance);
                }
            });
            menu.add(menuItemAdd);

            JMenuItem menuItemDelete = new JMenuItem("Delete ssh connection");
            menuItemDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SSHDeleterFrame(instance);
                }
            });
            menu.add(menuItemDelete);

            menuBar.add(menu);
        }
        return menuBar;
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel();
            centerPanel.setLayout(new GridBagLayout());
            Font font = new Font("Tahoma", Font.PLAIN, 25);
            Font boldFont = new Font("Tahoma", Font.BOLD, 25);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            commandLabel = new JLabel("Command:");
            commandInputLabel = new JLabel(" ");
            passwordLabel = new JLabel("Password:");
            passwordInputLabel = new JLabel(" ");
            commandLabel.setFont(Main.underline(boldFont));
            passwordLabel.setFont(Main.underline(boldFont));
            commandInputLabel.setFont(font);
            passwordInputLabel.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            centerPanel.add(commandLabel, gbc);
            gbc.gridy = 1;
            centerPanel.add(passwordLabel, gbc);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 1;
            gbc.gridy = 0;
            centerPanel.add(commandInputLabel, gbc);
            gbc.gridy = 1;
            centerPanel.add(passwordInputLabel, gbc);
            gbc.gridx = 2;
            centerPanel.add(getShowPasswordButton(), gbc);
        }
        return centerPanel;
    }

    private JButton getShowPasswordButton() {
        if (showPasswordButton == null) {
            showPasswordButton = new JButton();
            ImageIcon imageIcon = new ImageIcon(GUI.class.getResource("/show.png"));
            showPasswordButton.setIcon(imageIcon);
            showPasswordButton.addActionListener(this::showPasswordAction);
        }
        return showPasswordButton;
    }

    private void showPasswordAction(ActionEvent event) {
        Datensatz selection = (Datensatz) getComboBox().getSelectedItem();
        if (!isPasswordShown) {
            isPasswordShown = true;
            passwordInputLabel.setText(selection.getPassword());
        } else {
            isPasswordShown = false;
            String pwd = "";
            for (int i = 0; i < selection.getPassword().length(); i++) {
                pwd = pwd + "*";
            }
            passwordInputLabel.setText(pwd);
        }
    }

    private JComboBox getComboBox() {
        if (comboBox == null) {
            comboBox = new JComboBox<>();
            comboBox.setFont(new Font("Tahoma", Font.PLAIN, 25));
            comboBox.addItem(Main.EMPTY_DATENSATZ);
            comboBox.setSelectedItem(0);
            for (Datensatz data : Main.datensatzList) {
                comboBox.addItem(data);
            }
            comboBox.addActionListener(this::comboBoxAction);
        }
        return comboBox;
    }

    private void comboBoxAction(ActionEvent event) {
        Datensatz selection = (Datensatz) getComboBox().getSelectedItem();
        commandInputLabel.setText(selection.getCommand());
        String pwd = "";
        for (int i = 0; i < selection.getPassword().length(); i++) {
            pwd = pwd + "*";
        }
        passwordInputLabel.setText(pwd);
    }

    public void reload() {
        dispose();
        Main.startSession();
    }

    private JButton getConnectButton() {
        if (connectButton == null) {
            connectButton = new JButton();
            connectButton.setText("CONNECT");
            connectButton.setFont(new Font("Tahoma", Font.BOLD, 30));
            connectButton.addActionListener(this::connectButtonAction);
        }
        return connectButton;
    }

    private void connectButtonAction(ActionEvent event) {
        if (!getComboBox().getSelectedItem().equals(Main.EMPTY_DATENSATZ)) {
            Datensatz datensatz = (Datensatz) getComboBox().getSelectedItem();
            String command = datensatz.getCommand();
            String passwort = datensatz.getPassword();

            // Passwort in die Zwischenablage kopieren
            if (Main.configManager.isAutoCopyToClipboard()) {
                StringSelection stringSelection = new StringSelection(passwort);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }

            // Shell öffnen und Command ausführen
            SSHConnector.connect(command);

        }
    }
}
