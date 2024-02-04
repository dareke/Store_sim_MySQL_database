import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditOrderStatusWindow {
    private Database database;

    private JComboBox statusComboBox;
    private JButton cofnijButton;
    private JButton zmieńStatusButton;
    private JPanel panelMain;

    public EditOrderStatusWindow(Database database, int id) {
        this.database = database;

        // Definicja opcji dla ComboBox
        String[] statusString = {"Zrealizowano", "Do realizacji"};

        // Utworzenie modelu ComboBox na podstawie tablicy opcji
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(statusString);

        // Ustawienie modelu ComboBox
        statusComboBox.setModel(comboBoxModel);

        statusComboBox.setSelectedIndex(0);

        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        zmieńStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println((String) statusComboBox.getSelectedItem());
                database.changeOrderStatus((String) statusComboBox.getSelectedItem(), id);
                JOptionPane.showMessageDialog(null, "Status zamówienia został zmieniony!", "Powodzenie!", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
