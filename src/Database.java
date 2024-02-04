import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Objects;
import java.time.LocalDateTime;

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
            System.out.println("Błąd podczas połączenia z bazą danych");
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
            System.out.println("Błąd podczas połączenia z bazą danych");
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
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        return model;
    }
    public DefaultTableModel getClientOrder() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"ID Zamówienia", "Id produktu", "Klient", "Data zamówienia", "Status"};
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
                Object[] row = new Object[5];
                row[0] = resultSet.getInt("id_zamówienia");
                row[1] = resultSet.getInt("MagazynKatalogid_produktu");
                row[2] = resultSet.getString("Uzytkownicylogin");
                row[3] = resultSet.getDate("data_zamówienia");
                row[4] = resultSet.getString("status_zamówienia");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        return model;
    }
    public DefaultTableModel getWarehouseOrder() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"ID produktu","ID zamówienia","Ilość", "Pracownik", "Data zamówienia"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT * FROM zamówienia_do_magazynu";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getInt("id_zamówienia");
                row[1] = resultSet.getInt("MagazynKatalogid_produktu");
                row[3] = resultSet.getInt("ilość");
                row[2] = resultSet.getString("Uzytkownicylogin");
                row[4] = resultSet.getDate("data_zamówienia");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        return model;
    }
    public void registerClient(String login, String password, String name, String surname, String number, String email, String street, String city) {
        try {
            // Start a transaction
            connection.setAutoCommit(false);

            // Insert user data
            String userQuery = "INSERT INTO Użytkownicy (login, hasło, typ) VALUES (?, ?, 'klient')";
            try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                userStatement.setString(1, login);
                userStatement.setString(2, password);
                userStatement.executeUpdate();
            }

            // Insert client data
            String clientDataQuery = "INSERT INTO Dane_klientów (Uzytkownicylogin, imie, nazwisko, numer_telefonu, email, ulica, miasto)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement clientDataStatement = connection.prepareStatement(clientDataQuery)) {
                clientDataStatement.setString(1, login);
                clientDataStatement.setString(2, name);
                clientDataStatement.setString(3, surname);
                clientDataStatement.setString(4, number);
                clientDataStatement.setString(5, email);
                clientDataStatement.setString(6, street);
                clientDataStatement.setString(7, city);
                clientDataStatement.executeUpdate();
            }

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            try {
                // Rollback the transaction in case of an exception
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace(); // Handle the exception or log it
        } finally {
            try {
                // Restore auto-commit mode
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkLogin(String login) throws SQLException {
        String sqlQuery = "SELECT * FROM Użytkownicy WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String login_result = resultSet.getString("login");
                if (Objects.equals(login, login_result)) {
                    return true;
                } else {
                    return false;
                }
            }
        }catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public String[] getClientData(){
        String[] s = new String[6];
        String sqlQuery = "SELECT * FROM Dane_klientów WHERE Uzytkownicylogin = '" + logged_as + "'";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if(resultSet.next()) {
                s[0] = resultSet.getString("imie");
                s[1] = resultSet.getString("nazwisko");
                s[2] = resultSet.getString("numer_telefonu");
                s[3] = resultSet.getString("email");
                s[4] = resultSet.getString("ulica");
                s[5] = resultSet.getString("miasto");
            }
            return s;

        }
        catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        return null;
    }
    public void changeClientData(String name, String surname, String number, String email, String street, String city){

        String clientDataQuery = "UPDATE Dane_klientów SET imie = ?, nazwisko = ?, numer_telefonu = ?, email = ?, ulica = ?, miasto = ? WHERE Uzytkownicylogin = ?";

        try (PreparedStatement clientDataStatement = connection.prepareStatement(clientDataQuery)) {
            clientDataStatement.setString(1, name);
            clientDataStatement.setString(2, surname);
            clientDataStatement.setString(3, number);
            clientDataStatement.setString(4, email);
            clientDataStatement.setString(5, street);
            clientDataStatement.setString(6, city);
            clientDataStatement.setString(7, logged_as);
            clientDataStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public DefaultTableModel getWarehouse() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"ID produktu", "Ilość"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT Katalogid_produktu, ilość FROM Magazyn";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[2];
                row[0] = resultSet.getInt("Katalogid_produktu");
                row[1] = resultSet.getInt("ilość");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getManageCatalog() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"ID", "Nazwa", "Typ", "Kolor", "Rozmiar", "Cena"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT Katalog.*, Magazyn.widoczność FROM Katalog" +
                "INNER JOIN Magazyn ON Katalog.id_produktu=Magazyn.katalogid_produktu";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[7];
                row[0] = resultSet.getInt("id_produktu");
                row[1] = resultSet.getString("nazwa");
                row[2] = resultSet.getString("typ");
                row[3] = resultSet.getString("kolor");
                row[4] = resultSet.getString("rozmiar");
                row[5] = resultSet.getFloat("cena");
                row[6] = resultSet.getInt("widoczność");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }

        return model;
    }

    public DefaultTableModel getManageUser() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"login", "hasło", "typ użytkownika"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT * FROM Użytkownicy";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getString("login");
                row[1] = resultSet.getString("hasło");
                row[2] = resultSet.getString("typ");
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }

        return model;
    }

    public void orderToWarehouse(String amount, String product_id) {
        String userQuery = "INSERT INTO zamówienia_do_magazynu (MagazynKatalogid_produktu, Uzytkownicylogin," +
                "ilość,data_zamówienia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
            userStatement.setString(1, product_id);
            userStatement.setString(2, logged_as);
            userStatement.setString(3, amount);
            userStatement.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            userStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public void changeOrderStatus(String orderStatus, int id) {

        String updateQuery = "UPDATE zamówienia_klientów SET status_zamówienia = ? WHERE id_zamówienia = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)){

            updateStatement.setString(1, orderStatus);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }
}