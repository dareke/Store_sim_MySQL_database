import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientOrderWindow {
    private JPanel panelMain;
    private JButton refreshOrderButton;
    private JTable clientOrderTable;
    private Database database;

    public ClientOrderWindow(Database database){
        this.database = database;
        DefaultTableModel model = database.getClientOrder();
        clientOrderTable.setModel(model);
        clientOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JFrame frame = new JFrame("Katalog sklepu");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refreshOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = database.getClientOrder();
                clientOrderTable.setModel(model);
            }
        });
    }
}
