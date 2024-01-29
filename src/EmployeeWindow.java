import javax.swing.*;

public class EmployeeWindow {
    private Database database;
    private JPanel panelMain;

    public EmployeeWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witamy w sklepie!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
