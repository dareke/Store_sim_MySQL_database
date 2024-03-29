import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow {
    private Database database;
    private JPanel panelMain;
    private JButton przejrzyjKatalogButton;
    private JButton przejrzyjZamowieniaButton;
    private JButton zmienDaneButton;
    private JButton wylogujSięButton;

    public ClientWindow(Database database) {
        this.database = database;
        JFrame frame = new JFrame("Witamy w Sklepie!");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        przejrzyjKatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CatalogWindow catalogWindow = new CatalogWindow(database);
                frame.dispose();
            }
        });
        przejrzyjZamowieniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientOrderWindow clientOrderWindow = new ClientOrderWindow(database);
                frame.dispose();
            }
        });
        zmienDaneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditClientDataWindow editClientDataWindow = new EditClientDataWindow(database);
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