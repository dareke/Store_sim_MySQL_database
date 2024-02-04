import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientOrderManageWindow {
    Database database;


    private JButton refreshButton;
    private JPanel panelMain;
    private JTable clientOrderManageTable;
    private JButton zmieńStatusZamówieniaButton;

    public ClientOrderManageWindow(Database database) {
        this.database = database;
        DefaultTableModel model = database.getClientOrder();
        clientOrderManageTable.setModel(model);
        clientOrderManageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = database.getClientOrder();
                clientOrderManageTable.setModel(model);
            }
        });
        zmieńStatusZamówieniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = clientOrderManageTable.getSelectedRow();
                int value = (int) clientOrderManageTable.getModel().getValueAt(row, column);
                EditOrderStatusWindow editOrderStatusWindow = new EditOrderStatusWindow(database,value);
            }
        });
    }
}
