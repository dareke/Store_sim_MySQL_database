import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class Database {
    private Connection connection;
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

                // Przetwarzamy wyniki
                /*while (resultSet.next()) {
                    int id = resultSet.getInt("id_produktu");
                    String nazwa = resultSet.getString("nazwa");
                    String typ = resultSet.getString("typ");
                    String kolor = resultSet.getString("kolor");
                    String rozmiar = resultSet.getString("rozmiar");
                    float cena = resultSet.getFloat("cena");

                    // Wyświetlamy wyniki
                    System.out.println("ID: " + id + ", Username: " + nazwa + ", Email: " + typ +
                            ", Age: " + kolor + ", Created At: " + rozmiar + "cena " + cena);
                }*/
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            e.printStackTrace();
        }
    }

    public String getLogin(String login, String password) throws SQLException {
            String sqlQuery = "SELECT * FROM Użytkownicy";
        try (Statement statement = connection.createStatement()) {
            // Wykonujemy zapytanie i otrzymujemy wyniki
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String login_result = resultSet.getString("login");
                String password_result = resultSet.getString("hasło");
                if (Objects.equals(login, login_result)) {
                    if(Objects.equals(password, password_result)){
                        JOptionPane.showMessageDialog(null, "Zostałeś zalogowany!");
                        return resultSet.getString("typ");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Nieprawidłowe hasło!");
                        return null;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Nie znaleziono loginu w bazie!");
                    return null;
                }
            }
            return null;
        }catch (SQLException e) {
            System.out.println("Błąd podczas połączenia z bazą danych:");
            return null;
        }
    }

}