import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminWindow {
    private Database database;
    private JPanel panelMain;
    private JButton manageCatalogButton;
    private JButton employeeOptionsButton;
    private JButton manageUsersButton;
    private JButton wylogujSięButton;

    public AdminWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witamy w sklepie!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        manageCatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageCatalogWindow manageCatalogWindow = new ManageCatalogWindow(database);
                frame.dispose();
            }
        });
        employeeOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeWindow employeeWindow = new EmployeeWindow(database);
                frame.dispose();
            }
        });
        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageUserWindow manageUserWindow = new ManageUserWindow(database);
                frame.dispose();
            }
        });
        wylogujSięButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.logOut();
                MainMenu mainMenu = new MainMenu(database);
                frame.dispose();
            }
        });
    }
}
