import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarehouseManageWindow {
    private Database database;
    private JTable warehouseManageTable;
    private JButton orderProductTable;
    private JButton odświeżButton;
    private JPanel panelMain;

    public WarehouseManageWindow(Database database) {
        this.database = database;
        DefaultTableModel model = database.getWarehouse();
        warehouseManageTable.setModel(model);
        warehouseManageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        odświeżButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        orderProductTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = warehouseManageTable.getSelectedRow();
                String[] value = new String[warehouseManageTable.getColumnCount()];
                for(int i = 0; i < warehouseManageTable.getColumnCount(); i++) {
                    value[i] = warehouseManageTable.getValueAt(row, i).toString();
                }
                OrderToWarehouse orderToWarehouse = new OrderToWarehouse(database, value);
                frame.dispose();
            }
        });
    }
}
