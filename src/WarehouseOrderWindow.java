import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarehouseOrderWindow {
    private JPanel panelMain;
    private JTable warehouseOrderTable;
    private JButton odswiezZamowieniaButton;
    private JButton cofnijButton;
    private Database database;

    public WarehouseOrderWindow(Database database){
        this.database = database;
        DefaultTableModel model = database.getWarehouseOrder();
        warehouseOrderTable.setModel(model);
        warehouseOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JFrame frame = new JFrame("Katalog sklepu");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        odswiezZamowieniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = database.getWarehouseOrder();
                warehouseOrderTable.setModel(model);
            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeWindow employeeWindow = new EmployeeWindow(database);
                frame.dispose();
            }
        });
    }
}
