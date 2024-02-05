import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProductDataWindow {
    private Database database;
    private JPanel panelMain;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField typeTextField;
    private JTextField colorTextField;
    private JTextField sizeTextField;
    private JButton zakończEdycjęButton;
    private JButton cofnijButton;

    public EditProductDataWindow(Database database, int value) {
        this.database = database;
        JFrame frame = new JFrame("Witamy w Sklepie!");
        frame.setContentPane(panelMain);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        String[] s;
        s = database.getProductData(value);
        nameTextField.setText(s[0]);
        typeTextField.setText(s[1]);
        colorTextField.setText(s[2]);
        sizeTextField.setText(s[3]);
        priceTextField.setText(s[4]);
        zakończEdycjęButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                database.editProductData(value,name,type,size,color,price);
                JOptionPane.showMessageDialog(panelMain, "Edytowano produkt", "Edytowano!", JOptionPane.INFORMATION_MESSAGE);
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
    private boolean containsDigit(String s) {
        return s != null && s.chars().anyMatch(Character::isDigit);
    }
}
