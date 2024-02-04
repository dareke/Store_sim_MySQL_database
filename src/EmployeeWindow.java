import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeWindow {
    private Database database;
    private JPanel panelMain;
    private JButton clientOrderManageButton;
    private JButton warehouseOrderManageButton;
    private JButton warehouseManageButton;

    public EmployeeWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        warehouseOrderManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WarehouseOrderWindow warehouseOrderWindow = new WarehouseOrderWindow(database);
                frame.dispose();
            }
        });
        clientOrderManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientOrderManageWindow clientOrderManageWindow = new ClientOrderManageWindow(database);
                frame.dispose();
            }
        });
        warehouseManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WarehouseManageWindow warehouseManageWindow = new WarehouseManageWindow(database);
                frame.dispose();
            }
        });
    }
}
