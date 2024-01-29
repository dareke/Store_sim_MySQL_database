import javax.swing.*;

public class ClientWindow {
    private Database database;
    private JPanel panelMain;

    public ClientWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witamy w Sklepie!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}