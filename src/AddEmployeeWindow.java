import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployeeWindow {
    private Database database;
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JPanel panelMain;
    private JButton createUserButton;
    private JButton cofnijButton;

    public AddEmployeeWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witaj pracowniku!");
        frame.setContentPane(panelMain);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginTextField.getText(),password = passwordTextField.getText();
                if(database.checkLogin(login)){
                    JOptionPane.showMessageDialog(null, "Login zajęty!", "Błąd!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                database.addEmployee(login,password);
                JOptionPane.showMessageDialog(null, "Użytkownik został dodany!", "Zakończono!", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
