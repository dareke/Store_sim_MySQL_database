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
    }
}
