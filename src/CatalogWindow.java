import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatalogWindow {
    private Database database;
    private JPanel panelMain;
    private JTable catalogTable;
    private JButton odswiezKatalogButton;
    private JButton zamówWybranyProduktButton;
    private JButton cofnijButton;

    public CatalogWindow(Database database){
        this.database = database;
        DefaultTableModel model = database.getCatalog();
        catalogTable.setModel(model);
        catalogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JFrame frame = new JFrame("Katalog sklepu");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        odswiezKatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = database.getCatalog();
                catalogTable.setModel(model);
            }
        });
        zamówWybranyProduktButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = catalogTable.getSelectedRow();
                int value = (int) catalogTable.getModel().getValueAt(row, column);
                database.orderToClient(value);

            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientWindow clientWindow = new ClientWindow(database);
                frame.dispose();
            }
        });
    }
}
