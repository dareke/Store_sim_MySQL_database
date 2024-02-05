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
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public String getLogin(String login, String password){
        String sqlQuery = "SELECT * FROM Użytkownicy WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password_result = resultSet.getString("hasło");
                if (Objects.equals(password, password_result)) {
                    if(resultSet.getInt("aktywność") == 1){
                        logged_as = login;
                        return logged_type = resultSet.getString("typ");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Konto zdezaktywowane!");
                        return null;
                    }

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

    public boolean checkLogin(String login) {
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
            System.out.println("Błąd podczas połączenia z bazą danych");
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
    public void editClientData(String name, String surname, String number, String email, String street, String city){

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
        String[] columns = {"ID", "Nazwa", "Typ", "Kolor", "Rozmiar", "Cena", "Widoczność"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT Katalog.*, Magazyn.widoczność FROM Katalog " +
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
        String[] columns = {"login", "hasło", "typ użytkownika", "czy aktywne"};
        model.setColumnIdentifiers(columns);

        String sqlQuery = "SELECT * FROM Użytkownicy";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getString("login");
                row[1] = resultSet.getString("hasło");
                row[2] = resultSet.getString("typ");
                row[3] = resultSet.getInt("aktywność");
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

    public void addEmployee(String login, String password) {
        String sqlQuery = "INSERT INTO Użytkownicy (login,hasło,typ)" +
                "VALUES (?,?,?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(sqlQuery)){

            insertStatement.setString(1, login);
            insertStatement.setString(2,password);
            insertStatement.setString(3,"pracownik");
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public void deactivateUser(String value) {

        String updateQuery = "UPDATE Użytkownicy SET aktywność = 0 WHERE login = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)){
            updateStatement.setString(1, value);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public void addProduct(String name, String type, String color, String size, float price) {
        String sqlQuery = "INSERT INTO Katalog (nazwa,typ,kolor,rozmiar,cena)" +
                "VALUES (?,?,?,?,?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(sqlQuery)){

            insertStatement.setString(1, name);
            insertStatement.setString(2,type);
            insertStatement.setString(3,color);
            insertStatement.setString(4,size);
            insertStatement.setFloat(5,price);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        String id_result = null;
        String idQuery = "SELECT id_produktu FROM Katalog ORDER BY id_produktu DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(idQuery)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id_result = resultSet.getString("id_produktu");

            }
        }catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        if(id_result == null) return;
        String warehouseQuery = "INSERT INTO magazyn (katalogid_produktu, ilość, widoczność)" +
                "VALUES (?,0,1)";

        try (PreparedStatement insertStatement = connection.prepareStatement(warehouseQuery)){

            insertStatement.setString(1, id_result);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public String[] getProductData(int id) {
        String[] s = new String[5];
        String sqlQuery = "SELECT * FROM Katalog WHERE id_produktu = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                s[0] = resultSet.getString("nazwa");
                s[1] = resultSet.getString("typ");
                s[2] = resultSet.getString("rozmiar");
                s[3] = resultSet.getString("kolor");
                s[4] = resultSet.getString("cena");
            }
            return s;
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        return null;
    }

    public void editProductData(int id, String name, String type, String color, String size, float price) {

        String clientDataQuery = "UPDATE Katalog SET nazwa = ?, typ = ?, kolor = ?, rozmiar = ?, cena = ? WHERE id_produktu = ?";

        try (PreparedStatement clientDataStatement = connection.prepareStatement(clientDataQuery)) {
            clientDataStatement.setString(1, name);
            clientDataStatement.setString(2, type);
            clientDataStatement.setString(3, color);
            clientDataStatement.setString(4, size);
            clientDataStatement.setFloat(5, price);
            clientDataStatement.setInt(6, id);
            clientDataStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public void editAvailability(int selectedIndex, int id) {

        String updateQuery = "UPDATE Magazyn SET widoczność = ? WHERE Katalogid_produktu = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, selectedIndex);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
    }

    public void orderToClient(int value) {
        String selectQuery = "SELECT * FROM Magazyn WHERE Katalogid_produktu = ?";
        int amount = 0;
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, value);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt("ilość");

            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        if(amount== 0){
            JOptionPane.showMessageDialog(null,"Z przykrością informujemy, że aktualnie nie mamy tego produktu w magazynie.\n" +
                    "Prosimy sprawdzić później.", "Błąd!", JOptionPane.ERROR_MESSAGE);

            return;
        }
        String updateQuery = "UPDATE Magazyn SET ilość = ? WHERE Katalogid_produktu = ?";

        try (PreparedStatement userStatement = connection.prepareStatement(updateQuery)) {
            userStatement.setInt(1, amount-1);
            userStatement.setInt(2, value);
            userStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        String userQuery = "INSERT INTO zamówienia_klientów (MagazynKatalogid_produktu, Uzytkownicylogin," +
                "data_zamówienia, status_zamówienia) VALUES (?, ?, ?, 'Do realizacji')";
        try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
            userStatement.setInt(1, value);
            userStatement.setString(2, logged_as);
            userStatement.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            userStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych");
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"Zamówienie zostało przesłane do realizacji," +
                " zostanie wysłane na podany adres w ciągu 3 dni roboczych.", "Sukces!", JOptionPane.INFORMATION_MESSAGE);

    }
    public void logOut(){
        this.logged_as = null;
        this.logged_type = null;
        JOptionPane.showMessageDialog(null,"Wylogowano z konta!", "Żegnamy!", JOptionPane.INFORMATION_MESSAGE);

    }
}