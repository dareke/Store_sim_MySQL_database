import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Objects;

public class Database {
    private Connection connection;
    private String logged_as =null;
    private String logged_type = null;
    public Database() {
        // Dane do połączenia z bazą danych
        String url = "jdbc:mysql://localhost:3306/sys"; // URL do bazy danych
        String user = "root"; // Nazwa użytkownika bazy danych
        String password = "root"; // Hasło użytkownika bazy danych

        // Nawiązanie połączenia z bazą danych
        try {connection = DriverManager.getConnection(url, user, password);
            System.out.println("Połączono z bazą danych!");

            // Tworzymy zapytanie SQL
            String sqlQuery = "SELECT * FROM Katalog";

            // Tworzymy obiekt Statement
            try (Statement statement = connection.createStatement()) {
                // Wykonujemy zapytanie i otrzymujemy wyniki
                ResultSet resultSet = statement.executeQuery(sqlQuery);

            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            e.printStackTrace();
        }
    }

    public String getLogin(String login, String password) throws SQLException {
        String sqlQuery = "SELECT * FROM Użytkownicy WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password_result = resultSet.getString("hasło");
                if (Objects.equals(password, password_result)) {
                    logged_as = login;
                    return logged_type = resultSet.getString("typ");
                } else {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowe hasło!");
                    return null;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nie znaleziono loginu w bazie!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            e.printStackTrace();
            return null;
        }
    }
    public DefaultTableModel getCatalog() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"ID", "Nazwa", "Typ", "Kolor", "Rozmiar", "Cena"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT * FROM Katalog";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[6];
                row[0] = resultSet.getInt("id_produktu");
                row[1] = resultSet.getString("nazwa");
                row[2] = resultSet.getString("typ");
                row[3] = resultSet.getString("kolor");
                row[4] = resultSet.getString("rozmiar");
                row[5] = resultSet.getFloat("cena");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            e.printStackTrace();
        }
        return model;
    }
    public DefaultTableModel getClientOrder() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"ID Zamówienia", "Klient", "Data zamówienia"};
        model.setColumnIdentifiers(columns);
        String sqlQuery;
        if(logged_type.equals("klient")) {
            sqlQuery = "SELECT * FROM Zamówienia_klientów WHERE Uzytkownicylogin = \"" + logged_as + "\"";
        }else{
            sqlQuery = "SELECT * FROM Zamówienia_klientów";
        }

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getInt("MagazynKatalogid_produktu");
                row[1] = resultSet.getString("Uzytkownicylogin");
                row[2] = resultSet.getDate("data_zamówienia");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            e.printStackTrace();
        }
        return model;
    }
    public DefaultTableModel getWarehouseOrder() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"ID Zamówienia", "Klient", "Data zamówienia"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT * FROM zamówienia_do_magazynu";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getInt("MagazynKatalogid_produktu");
                row[1] = resultSet.getString("Uzytkownicylogin");
                row[2] = resultSet.getDate("data_zamówienia");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            e.printStackTrace();
        }
        return model;
    }

}