package DataBase;

import Users.Customer;
import Users.Product;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public static Connection getConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/Supermarket";
        String username = "root";
        String password = "0865"; //0865 //12345
        return DriverManager.getConnection(dbURL, username, password);
    }

    public static Customer loginCustomer(String email, String passwordHash) {

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

    public static void registerCustomer(Customer customer) {
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


    public static ArrayList<Product> getPurchasedProducts() {
        try {
            String sql = "SELECT product_id, amount_sold  FROM purchase_history";
            Connection dbConnection = DataBase.getConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Product> contacts = new ArrayList<>();

            while (resultSet.next()) {
                Product dbProduct = new Product(resultSet.getInt("product_id"), resultSet.getDouble("amount_sold"));
                contacts.add(dbProduct);
            }
            return contacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateBalance(Double newBalance, String customerEmail) {
        try {
            Connection dbConnection = getConnection();

            // retrieve the id of the customer with the given email
            PreparedStatement getIdStatement = dbConnection.prepareStatement("SELECT id FROM customers WHERE email = ?");
            getIdStatement.setString(1, customerEmail);
            ResultSet idResult = getIdStatement.executeQuery();

            if (idResult.next()) {
                int customerId = idResult.getInt("id");

                // update the balance of the customer with the retrieved id
                PreparedStatement updateBalanceStatement = dbConnection.prepareStatement("UPDATE customers SET balance = balance + ? WHERE id = ?");
                updateBalanceStatement.setDouble(1, newBalance);
                updateBalanceStatement.setInt(2, customerId);
                int rowsUpdated = updateBalanceStatement.executeUpdate();

                if (rowsUpdated != 1) {
                    throw new SQLException("Failed to update balance for customer with email " + customerEmail);
                }
            } else {
                throw new SQLException("No customer found with email " + customerEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
