import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAvailabilityWindow {
    private Database database;
    private JPanel panelMain;
    private JComboBox choiceComboBox;
    private JButton button1;
    private JButton cofnijButton;

    public EditAvailabilityWindow(Database database, int id) {
        this.database = database;

        // Definicja opcji dla ComboBox
        String[] statusString = {"Ukryj", "Dostępne"};

        // Utworzenie modelu ComboBox na podstawie tablicy opcji
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(statusString);

        // Ustawienie modelu ComboBox
        choiceComboBox.setModel(comboBoxModel);

        choiceComboBox.setSelectedIndex(0);

        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.editAvailability(choiceComboBox.getSelectedIndex(), id);
                JOptionPane.showMessageDialog(null, "Widoczność została zmieniona!", "Powodzenie!", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
