package gui;

import management.Datensatz;
import management.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class defines the JFrame for deleting connections.
 *
 * @author Grumanda
 */
public class SSHDeleterFrame extends JFrameWithLogo {

    private GUI gui;
    private JComboBox<Datensatz> comboBox;
    private JButton deleteButton;

    /**
     * This constructor defines the JFrame.
     *
     * @param gui
     */
    public SSHDeleterFrame (GUI gui) {
        this.gui = gui;
        // Window configuration
        setTitle("SSH Connector -> Delete connection");
        setLocationRelativeTo(gui);
        setSize(500, 500);
        setLayout(new BorderLayout());

        // Add components
        add(getComboBox(), BorderLayout.NORTH);
        add(getDeleteButton(), BorderLayout.SOUTH);

        setVisible(true);
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
        }
        return comboBox;
    }

    /**
     * This method defines the deleteButton and returns it.
     *
     * @return JButton
     */
    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText("DELETE");
            deleteButton.setFont(new Font("Tahoma", Font.BOLD, 30));
            deleteButton.addActionListener(this::deleteButtonAction);
        }
        return deleteButton;
    }

    /**
     * This method defines the action of the deleteButton.
     *
     * @param event
     */
    private void deleteButtonAction(ActionEvent event) {
        Datensatz selection = (Datensatz) comboBox.getSelectedItem();

        // Asks if user really wants to delete the connection (and deletes it)
        int option = JOptionPane.showConfirmDialog(gui, "Are you sure?", "Delete",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            Main.commander.deleteData(selection.getName());
            dispose();
            gui.reload();
        }
    }
}