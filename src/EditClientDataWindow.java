import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClientDataWindow {
    private Database database;
    private JTextField emailTextField;
    private JTextField cityTextField;
    private JTextField streetTextField;
    private JTextField numberTextField;
    private JTextField surnameTextField;
    private JTextField nameTextField;
    private JButton button1;
    private JPanel panelMain;
    private JButton cofnijButton;

    public EditClientDataWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witamy w Sklepie!");
        frame.setContentPane(panelMain);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        String[] s;
        s = database.getClientData();
        nameTextField.setText(s[0]);
        surnameTextField.setText(s[1]);
        numberTextField.setText(s[2]);
        emailTextField.setText(s[3]);
        streetTextField.setText(s[4]);
        cityTextField.setText(s[5]);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() ||
                        numberTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
                        streetTextField.getText().isEmpty() || cityTextField.getText().isEmpty()) {
                    // Jeśli jakiekolwiek pole jest puste, wyświetl komunikat
                    JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return; // Przerwij działanie metody
                }

                // Sprawdzenie czy pole nameTextField zawiera tylko litery
                if (!nameTextField.getText().matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "Imię może zawierać tylko litery", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sprawdzenie czy pole surnameTextField zawiera tylko litery
                if (!surnameTextField.getText().matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "Nazwisko może zawierać tylko litery", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sprawdzenie czy pole cityTextField zawiera tylko litery
                if (!cityTextField.getText().matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "Miasto może zawierać tylko litery", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sprawdzenie czy pole numberTextField zawiera tylko cyfry i czy ma dokładnie 9 cyfr
                if (!numberTextField.getText().matches("\\d{9}")) {
                    JOptionPane.showMessageDialog(null, "Numer telefonu musi zawierać dokładnie 9 cyfr", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sprawdzenie czy pole emailTextField zawiera znak @ i jest poprawnym adresem email
                if (!emailTextField.getText().contains("@") || !emailTextField.getText().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Niepoprawny adres email", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sprawdzenie czy pole streetTextField zawiera spację i numer na końcu oraz czy nie zawiera cyfr poza numerem
                if (!streetTextField.getText().matches(".*\\s\\d+$") || streetTextField.getText().replaceAll("\\d", "").matches("\\s")) {
                    JOptionPane.showMessageDialog(null, "Niepoprawny adres", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Jeśli wszystkie sprawdzenia zakończyły się powodzeniem, zarejestruj klienta

                JOptionPane.showMessageDialog(null, "Dane zostały poprawnie zmienione", "Komunikat", JOptionPane.PLAIN_MESSAGE);
                database.editClientData(nameTextField.getText(), surnameTextField.getText(),
                        numberTextField.getText(), emailTextField.getText(),
                        streetTextField.getText(), cityTextField.getText());
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
