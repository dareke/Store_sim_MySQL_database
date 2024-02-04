import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterWindow {
    private JPanel panelMain;
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JTextField surnameTextField;
    private JTextField numberTextField;
    private JTextField emailTextField;
    private JTextField streetTextField;
    private JTextField cityTextField;
    private JTextField nameTextField;
    private JButton cofnijButton;
    private JButton zarejestrujButton;
    private Database database;


    public RegisterWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Rejestracja");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        zarejestrujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sprawdzenie czy pola tekstowe nie są puste
                try {
                    if(database.checkLogin(loginTextField.getText())==true){
                        JOptionPane.showMessageDialog(null, "login istnieje w bazie", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return; // Przerwij działanie metody
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (loginTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || nameTextField.getText().isEmpty() ||
                        surnameTextField.getText().isEmpty() || numberTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
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
                database.registerClient(loginTextField.getText(),passwordTextField.getText(),nameTextField.getText(),
                        surnameTextField.getText(), numberTextField.getText(), emailTextField.getText(),
                        streetTextField.getText(), cityTextField.getText());
                try {
                    database.getLogin(loginTextField.getText(), passwordTextField.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                CatalogWindow catalogWindow = new CatalogWindow(database);
                frame.dispose();

            }
        });
    }
}