package gui;

import management.Datensatz;
import management.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SSHDeleterFrame extends JFrameWithLogo {

    private GUI gui;
    private JComboBox<Datensatz> comboBox;
    private JButton deleteButton;

    public SSHDeleterFrame (GUI gui) {
        this.gui = gui;
        setTitle("SSH Connector -> Delete connection");
        setLocationRelativeTo(gui);
        setSize(500, 500);
        setLayout(new BorderLayout());

        add(getComboBox(), BorderLayout.NORTH);
        add(getDeleteButton(), BorderLayout.SOUTH);

        setVisible(true);
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
        }
        return comboBox;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText("DELETE");
            deleteButton.setFont(new Font("Tahoma", Font.BOLD, 30));
            deleteButton.addActionListener(this::deleteButtonAction);
        }
        return deleteButton;
    }

    private void deleteButtonAction(ActionEvent event) {
        Datensatz selection = (Datensatz) comboBox.getSelectedItem();
        int option = JOptionPane.showConfirmDialog(gui, "Are you sure?", "Delete",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            Main.commander.deleteData(selection.getName());
            dispose();
            gui.reload();
        }
    }
}