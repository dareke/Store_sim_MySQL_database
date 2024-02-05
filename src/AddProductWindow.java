import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductWindow {
    private Database database;
    private JPanel panelMain;
    private JTextField nameTextField;
    private JTextField typeTextField;
    private JTextField colorTextField;
    private JTextField sizeTextField;
    private JTextField priceTextField;
    private JButton dodajProduktButton;
    private JButton cofnijButton;

    public AddProductWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Dodawanie produktu");
        frame.setContentPane(panelMain);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        dodajProduktButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
// Pobierz wartości z pól tekstowych
                String name = nameTextField.getText();
                String type = typeTextField.getText();
                String color = colorTextField.getText();
                String size = sizeTextField.getText();
                String priceStr = priceTextField.getText();

                // Sprawdź, czy pola nie są puste
                if (name.isEmpty() || type.isEmpty()) {
                    JOptionPane.showMessageDialog(panelMain, "Pola nazwa i typ muszą być wypełnione.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sprawdź, czy pola nie zawierają cyfr
                if (containsDigit(name) || containsDigit(type) || containsDigit(color) || containsDigit(size)) {
                    JOptionPane.showMessageDialog(panelMain, "Pola nazwy, typu, koloru i rozmiaru nie mogą zawierać cyfr.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float price;
                try {
                    price = Float.parseFloat(priceStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Nieprawidłowy format ceny. Podaj liczbę rzeczywistą.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (price <= 0) {
                    JOptionPane.showMessageDialog(panelMain, "Cena musi być większa od zera.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Jeśli wszystkie warunki są spełnione, dodaj produkt do bazy danych
                database.addProduct(name,type,color,size,Float.parseFloat(priceStr));
                JOptionPane.showMessageDialog(panelMain, "Dodano produkt do bazy", "Dodano!", JOptionPane.INFORMATION_MESSAGE);
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

    // Metoda pomocnicza do sprawdzenia czy w danym tekście występują cyfry
    private boolean containsDigit(String s) {
        return s != null && s.chars().anyMatch(Character::isDigit);
    }

}
