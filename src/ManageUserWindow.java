import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageUserWindow {
    private Database database;
    private JTable manageUserTable;
    private JButton dodajPracownikaButton;
    private JButton deleteUserButton;
    private JButton refreshButton;
    private JPanel panelMain;

    public ManageUserWindow(Database database) {
        this.database = database;
        DefaultTableModel model = database.getManageUser();
        manageUserTable.setModel(model);
        manageUserTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = database.getManageUser();
                manageUserTable.setModel(model);
            }
        });
    }
}
