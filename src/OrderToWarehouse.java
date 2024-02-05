import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderToWarehouse {
    private Database database;
    private JPanel panelMain;
    private JTextField productTextField;
    private JTextField amountTextField;
    private JButton orderButton;
    private JButton returnButton;

    public OrderToWarehouse(Database database, String[] chosen_product) {
        this.database = database;
        productTextField.setText("Id produktu: " + chosen_product[0] + " Ilość w magazynie: " + chosen_product[1]);
        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = amountTextField.getText();

                try {
                    int amount = Integer.parseInt(input);

                    if (amount >= 1 && amount <= 99) {
                        database.orderToWarehouse(input, chosen_product[0]);
                        JOptionPane.showMessageDialog(null, "Produkt został zamówiony!", "Zamówiono!", JOptionPane.INFORMATION_MESSAGE);
                        EmployeeWindow employeeWindow = new EmployeeWindow(database);
                        frame.dispose();
                    } else {
                        // Komunikat o błędzie gdy liczba nie mieści się w zakresie
                        JOptionPane.showMessageDialog(null, "Wprowadź liczbę od 1 do 99.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // Komunikat o błędzie gdy wprowadzony tekst nie jest liczbą
                    JOptionPane.showMessageDialog(null, "Wprowadź poprawną liczbę.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WarehouseManageWindow warehouseManageWindow= new WarehouseManageWindow(database);
                frame.dispose();
            }
        });
    }
}
