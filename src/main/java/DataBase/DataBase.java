package DataBase;

import Users.Customer;

import java.sql.*;

public class DataBase {
    public static Connection getConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/Supermarket";
        String username = "root";
        String password = "12345"; //0865
        return DriverManager.getConnection(dbURL, username, password);
    }

    public static Customer loginCustomer(String email, String passwordHash){

        try {
            String sql = "SELECT * FROM customers WHERE email = ? and password_hash = ?";



            Connection dbConnection = getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setBalance(resultSet.getDouble("balance"));

                return customer;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void registerCustomer(Customer customer){
        try {
            String sql = "INSERT INTO customers (name, email, password_hash, balance) VALUES (?,?,?,?)";

            Connection dbConnection = getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPasswordHash());
            preparedStatement.setDouble(4, customer.getBalance());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
