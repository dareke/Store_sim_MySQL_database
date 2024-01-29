import javax.swing.*;

public class CatalogWindow {
    private Database database;
    private JPanel panelMain;

    public CatalogWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Katalog sklepu");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
