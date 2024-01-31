import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginWindow {
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JPanel panelMain;
    private JButton loginAcceptButton;
    private Database database;
    public LoginWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Logowanie");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        loginAcceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginTextField.getText();
                String password = passwordTextField.getText();
                try {
                    String type = database.getLogin(login, password);
                    System.out.println(type);
                    if(type != null){
                        if(type.equals("admin")){
                            AdminWindow adminWindow = new AdminWindow(database);
                        }
                        else if (type.equals("pracownik")) {
                            System.out.println("pracownik");
                            EmployeeWindow employeeWindow = new EmployeeWindow(database);

                        }
                        else if (type.equals("klient")) {
                            System.out.println("klient");
                            ClientWindow clientWindow = new ClientWindow(database);
                        }
                        frame.dispose();
                    }

                } catch (SQLException ex) {
                    System.out.println("blad bazy");
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
