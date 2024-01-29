import javax.swing.*;

public class AdminWindow {
    private Database database;
    private JPanel panelMain;

    public AdminWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witamy w sklepie!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
