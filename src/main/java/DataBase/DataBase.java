package DataBase;

import Users.Customer;
import Users.Product;
import Users.PurchaseHistory;
import Users.SalesManager;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public static Connection getConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/Supermarket";
        String username = "root";
        String password = "12345"; //0865 //12345
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


    public static ArrayList<PurchaseHistory> getPurchasedProducts(String customerEmail) {
        try {
            Connection dbConnection = DataBase.getConnection();
            PreparedStatement getIdStatement = dbConnection.prepareStatement("SELECT customer_email, product_name, amount_sold, purchase_price, retail_price, total FROM purchase_history WHERE customer_email = ?");
            getIdStatement.setString(1, customerEmail);
            ResultSet resultSet = getIdStatement.executeQuery();

            ArrayList<PurchaseHistory> purchaseHistory = new ArrayList<>();

            while (resultSet.next()) {
                PurchaseHistory purchasedProduct = new PurchaseHistory();
                purchasedProduct.setCustomerEmail(resultSet.getString("customer_email"));
                purchasedProduct.setProductName(resultSet.getString("product_name"));
                purchasedProduct.setAmountSold(resultSet.getDouble("amount_sold"));
                purchasedProduct.setPurchasePrice(resultSet.getDouble("purchase_price"));
                purchasedProduct.setRetailPrice(resultSet.getDouble("retail_price"));
                purchasedProduct.setTotal(resultSet.getDouble("total"));

                purchaseHistory.add(purchasedProduct);
            }
            return purchaseHistory;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<PurchaseHistory> getPurchasedProducts() {
        try {
            Connection dbConnection = DataBase.getConnection();
            PreparedStatement getIdStatement = dbConnection.prepareStatement("SELECT customer_email, product_name, amount_sold, purchase_price, retail_price, total FROM purchase_history");
            ResultSet resultSet = getIdStatement.executeQuery();

            ArrayList<PurchaseHistory> purchaseHistory = new ArrayList<>();

            while (resultSet.next()) {
                PurchaseHistory purchasedProduct = new PurchaseHistory();
                purchasedProduct.setCustomerEmail(resultSet.getString("customer_email"));
                purchasedProduct.setProductName(resultSet.getString("product_name"));
                purchasedProduct.setAmountSold(resultSet.getDouble("amount_sold"));
                purchasedProduct.setPurchasePrice(resultSet.getDouble("purchase_price"));
                purchasedProduct.setRetailPrice(resultSet.getDouble("retail_price"));
                purchasedProduct.setTotal(resultSet.getDouble("total"));

                purchaseHistory.add(purchasedProduct);
            }
            return purchaseHistory;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateCustomerBalance(Double newBalance, String customerEmail) {
        try {
            Connection dbConnection = getConnection();

            // retrieve the id of the customer with the given email
            PreparedStatement getIdStatement = dbConnection.prepareStatement("SELECT id FROM customers WHERE email = ?");
            getIdStatement.setString(1, customerEmail);
            ResultSet idResult = getIdStatement.executeQuery();

            if (idResult.next()) {
                int customerId = idResult.getInt("id");

                // update the balance of the customer with the retrieved id
                PreparedStatement updateBalanceStatement = dbConnection.prepareStatement("UPDATE customers SET balance = ? WHERE id = ?");
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

    public static void updateSalesManagerBalance(Double newBalance, String salesManagerEmail) {
        try {
            Connection dbConnection = getConnection();

            // retrieve the id of the customer with the given email
            PreparedStatement getIdStatement = dbConnection.prepareStatement("SELECT id FROM sales_managers WHERE email = ?");
            getIdStatement.setString(1, salesManagerEmail);
            ResultSet idResult = getIdStatement.executeQuery();

            if (idResult.next()) {
                int customerId = idResult.getInt("id");

                // update the balance of the customer with the retrieved id
                PreparedStatement updateBalanceStatement = dbConnection.prepareStatement("UPDATE sales_managers SET balance = ? WHERE id = ?");
                updateBalanceStatement.setDouble(1, newBalance);
                updateBalanceStatement.setInt(2, customerId);
                int rowsUpdated = updateBalanceStatement.executeUpdate();

                if (rowsUpdated != 1) {
                    throw new SQLException("Failed to update balance for sales manager with email " + salesManagerEmail);
                }
            } else {
                throw new SQLException("No sales manager found with email " + salesManagerEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SalesManager loginAsSalesManager(String email, String passwordHash) {

        try {
            String sql = "SELECT * FROM sales_managers WHERE email = ? and password_hash = ?";


            Connection dbConnection = getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                SalesManager salesManager = new SalesManager();
                salesManager.setName(resultSet.getString("name"));
                salesManager.setEmail(resultSet.getString("email"));
                salesManager.setShopBalance(resultSet.getDouble("balance"));

                return salesManager;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void registerSalesManager(SalesManager salesManager) {
        try {
            String sql = "INSERT INTO sales_managers (name, email, password_hash, balance) VALUES (?,?,?,?)";

            Connection dbConnection = getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, salesManager.getName());
            preparedStatement.setString(2, salesManager.getEmail());
            preparedStatement.setString(3, salesManager.getPasswordHash());
            preparedStatement.setDouble(4, salesManager.getShopBalance());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Product getProduct(String productName) {
        try {
            String sql = "SELECT * FROM  products WHERE name = ?";

            Connection dbConnection = getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, productName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setAvailableQuantity(resultSet.getDouble("available_quantity"));
                product.setUnitSize(resultSet.getDouble("unit_size"));
                product.setPurchasePrice(resultSet.getDouble("purchase_price"));
                product.setRetailPrice(resultSet.getDouble("retail_price"));

                return product;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addProduct(Product product) {
        try {
            String sql = "INSERT INTO products (name, available_quantity, unit_size, purchase_price, retail_price) VALUES (?,?,?,?,?)";

            Connection dbConnection = getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getAvailableQuantity());
            preparedStatement.setDouble(3, product.getUnitSize());
            preparedStatement.setDouble(4, product.getPurchasePrice());
            preparedStatement.setDouble(5, product.getRetailPrice());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateProduct(Product product) {
        try {
            String sql = "UPDATE products SET available_quantity = ?, unit_size = ?, purchase_price = ?, retail_price = ? WHERE name = ?";

            Connection dbConnection = getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setDouble(1, product.getAvailableQuantity());
            preparedStatement.setDouble(2, product.getUnitSize());
            preparedStatement.setDouble(3, product.getPurchasePrice());
            preparedStatement.setDouble(4, product.getRetailPrice());
            preparedStatement.setString(5, product.getName());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Product> getAllAvailableProducts() {
        try {
            String sql = "SELECT * FROM  products WHERE available_quantity > 0";

            Connection dbConnection = getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Product> availableProducts = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setAvailableQuantity(resultSet.getDouble("available_quantity"));
                product.setUnitSize(resultSet.getDouble("unit_size"));
                product.setPurchasePrice(resultSet.getDouble("purchase_price"));
                product.setRetailPrice(resultSet.getDouble("retail_price"));
                availableProducts.add(product);

            }
            return availableProducts;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void recordPurchase(PurchaseHistory purchaseHistory){
        try {
            String sql = "INSERT INTO purchase_history (customer_email, product_name, amount_sold, purchase_price, retail_price, total) VALUES (?,?,?,?,?,?)";

            Connection dbConnection = getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, purchaseHistory.getCustomerEmail());
            preparedStatement.setString(2, purchaseHistory.getProductName());
            preparedStatement.setDouble(3, purchaseHistory.getAmountSold());
            preparedStatement.setDouble(4, purchaseHistory.getPurchasePrice());
            preparedStatement.setDouble(5, purchaseHistory.getRetailPrice());
            preparedStatement.setDouble(6, purchaseHistory.getTotal());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



