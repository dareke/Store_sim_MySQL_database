import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class MainMenu {
    private JPanel panelMain;
    private JButton registerButton;
    private JButton loginButton;
    private JButton closeButton;
    private Database database;

    public MainMenu(Database database) {
        this.database = database;
        JFrame frame = new JFrame("App");
        frame.setContentPane(panelMain); // Usunięcie tworzenia nowej instancji MainMenu()
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginWindow loginWindow = new LoginWindow(database);
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        Database database = new Database();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu(database);
            }
        });
    }
}


