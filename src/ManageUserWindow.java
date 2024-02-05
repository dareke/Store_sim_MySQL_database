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
    private JButton cofnijButton;

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
        dodajPracownikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeWindow addEmployeeWindow = new AddEmployeeWindow(database);

            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = manageUserTable.getSelectedRow();
                String value = (String) manageUserTable.getModel().getValueAt(row, column);
                database.deactivateUser(value);
                JOptionPane.showMessageDialog(null, "Konto usunięte!", "Zakończono!", JOptionPane.INFORMATION_MESSAGE);

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
