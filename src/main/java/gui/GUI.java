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

/**
 * This class defines the main window of the program.
 *
 * @author Grumanda
 */
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

    /**
     * This is the constructor of the main window.
     */
    public GUI() {
        isPasswordShown = false;
        this.instance = this;

        // Window Configuration
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

    /**
     * This method defines the JMenuBar and returns it.
     *
     * @return JMenuBar
     */
    private JMenuBar getMenuBarGUI() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            JMenu menu = new JMenu("Settings");
            JMenuItem menuItemAdd = new JMenuItem("Add ssh connection");
            // The following action creates a new window where a new connection can be added
            menuItemAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SSHCreatorFrame(instance);
                }
            });
            menu.add(menuItemAdd);

            JMenuItem menuItemDelete = new JMenuItem("Delete ssh connection");
            // The following action creates a new window where a connection can be deleted
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

    /**
     * This method defines the centerPanel where the information is shown.
     *
     * @return JPanel
     */
    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            // Setup of needed components
            centerPanel = new JPanel();
            centerPanel.setLayout(new GridBagLayout());
            Font font = new Font("Tahoma", Font.PLAIN, 25);
            Font boldFont = new Font("Tahoma", Font.BOLD, 25);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            // Creation and adjustment of the labels
            commandLabel = new JLabel("Command:");
            commandInputLabel = new JLabel(" ");
            passwordLabel = new JLabel("Password:");
            passwordInputLabel = new JLabel(" ");
            commandLabel.setFont(Main.underline(boldFont));
            passwordLabel.setFont(Main.underline(boldFont));
            commandInputLabel.setFont(font);
            passwordInputLabel.setFont(font);

            // Adding components to the centerPanel
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

    /**
     * This method defines the showPasswordButton and returns it.
     *
     * @return JButton
     */
    private JButton getShowPasswordButton() {
        if (showPasswordButton == null) {
            showPasswordButton = new JButton();
            ImageIcon imageIcon = new ImageIcon(GUI.class.getResource("/show.png"));
            showPasswordButton.setIcon(imageIcon);
            showPasswordButton.addActionListener(this::showPasswordAction);
        }
        return showPasswordButton;
    }

    /**
     * This method defines the ActionEvent for the showPasswordButton.
     * The action toggles the visibility of the password.
     *
     * @param event
     */
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

    /**
     * This method defines the comboBox where the connection can be selected and
     * returns it.
     *
     * @return JComboBox
     */
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

    /**
     * This ActionEvent gets triggered when an entry in the comboBox is selected.
     * It updates the data in the centerPanel.
     *
     * @param event
     */
    private void comboBoxAction(ActionEvent event) {
        Datensatz selection = (Datensatz) getComboBox().getSelectedItem();
        commandInputLabel.setText(selection.getCommand());
        String pwd = "";
        for (int i = 0; i < selection.getPassword().length(); i++) {
            pwd = pwd + "*";
        }
        passwordInputLabel.setText(pwd);
    }

    /**
     * This method reloads the program. It is to see a new entry after a
     * new connection have been added.
     */
    public void reload() {
        dispose();
        Main.startSession();
    }

    /**
     * This method defines the connectButton and returns it.
     *
     * @return JButton
     */
    private JButton getConnectButton() {
        if (connectButton == null) {
            connectButton = new JButton();
            connectButton.setText("CONNECT");
            connectButton.setFont(new Font("Tahoma", Font.BOLD, 30));
            connectButton.addActionListener(this::connectButtonAction);
        }
        return connectButton;
    }

    /**
     * This ActionEvent is triggered by the connectButton.
     * It saves the password to the clipboard (if activated) and starts the
     * SSHConnector.
     *
     * @param event
     */
    private void connectButtonAction(ActionEvent event) {
        if (!getComboBox().getSelectedItem().equals(Main.EMPTY_DATENSATZ)) {
            Datensatz datensatz = (Datensatz) getComboBox().getSelectedItem();
            String command = datensatz.getCommand();
            String passwort = datensatz.getPassword();

            // Saves the password to the clipboard
            if (Main.configManager.isAutoCopyToClipboard()) {
                StringSelection stringSelection = new StringSelection(passwort);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }

            // opens the shell
            SSHConnector.connect(command);
        }
    }
}