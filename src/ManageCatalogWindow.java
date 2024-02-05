import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCatalogWindow {
    private Database database;
    private JPanel panelMain;
    private JTable manageCatalogTable;
    private JButton dodajProduktButton;
    private JButton zmieńWidocznośćProduktuButton;
    private JButton odświeżTabelęButton;
    private JButton editProductButton;
    private JButton cofnijButton;


    public ManageCatalogWindow(Database database) {
        this.database = database;
        DefaultTableModel model = database.getManageCatalog();
        manageCatalogTable.setModel(model);
        manageCatalogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        odświeżTabelęButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = database.getManageCatalog();
                manageCatalogTable.setModel(model);
            }
        });
        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        dodajProduktButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProductWindow addProductWindow = new AddProductWindow(database);

            }
        });
        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = manageCatalogTable.getSelectedRow();
                int value = (int) manageCatalogTable.getModel().getValueAt(row, column);
                EditProductDataWindow editProductDataWindow = new EditProductDataWindow(database,value);
            }
        });
        zmieńWidocznośćProduktuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = manageCatalogTable.getSelectedRow();
                int value = (int) manageCatalogTable.getModel().getValueAt(row, column);
                EditAvailabilityWindow editAvailabilityWindow = new EditAvailabilityWindow(database,value);

            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminWindow adminWindow = new AdminWindow(database);
                frame.dispose();
            }
        });
    }
}
