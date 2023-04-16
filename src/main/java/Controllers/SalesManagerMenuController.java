package Controllers;

import DataBase.DataBase;
import DataBase.Sha256;
import DataBase.StringHelpers;
import Users.Product;
import Users.PurchaseHistory;
import Users.SalesManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class SalesManagerMenuController {
    public static void loginAsSalesManager() {

        JTextField emailField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Email: "));
        myPanel.add(emailField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Password: "));
        myPanel.add(passwordField);

        Object[] loginMenuOptions = {"Login", "Register"};

        int result = JOptionPane.showOptionDialog(null,
                myPanel,
                "Supermarket",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,  //do not use a custom Icon
                loginMenuOptions,  //the titles of buttons
                loginMenuOptions[0]); //default button title
        if (result == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            loginAsAsSalesManagerValidation(emailField.getText(), password);
        } else if (result == JOptionPane.NO_OPTION) {
            SalesManager registeredSalesManager = registerNewSalesManager();
            if (registeredSalesManager != null) {
                generateSalesManagerMenu(registeredSalesManager);
            }
            else {
                MainMenuController.start();
            }
        }
        else {
            MainMenuController.start();
        }

    }

    private static void loginAsAsSalesManagerValidation(String email, String password) {
        if (StringHelpers.isNullOrEmpty(email) || StringHelpers.isNullOrEmpty(password)) {
            JOptionPane.showMessageDialog(null, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
            loginAsSalesManager();
        } else {
            String passwordHash = Sha256.generateSHA256(password);
            SalesManager loggedInManager = DataBase.loginAsSalesManager(email, passwordHash);
            if (loggedInManager == null) {
                JOptionPane.showMessageDialog(null, "Incorrect email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                loginAsSalesManager();
            } else {
                generateSalesManagerMenu(loggedInManager);
            }
        }
    }

    private static void generateSalesManagerMenu(SalesManager salesManager) {
        JFrame frame = new JFrame("Account");

        JButton addProductButton = new JButton("Add Product");
        JButton purchaseHistoryButton = new JButton("Sales history");
        JButton addFundsButton = new JButton("Add funds");
        JButton logOutButton = new JButton("Log out");

        frame.setSize(400, 200);
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel customerName = new JLabel("Welcome: " + salesManager.getName());
        JLabel currentBalance = new JLabel("Your current balance is: " + salesManager.getShopBalance() + " €");
        panel.add(customerName);
        panel.add(currentBalance);
        panel.add(addProductButton);
        panel.add(purchaseHistoryButton);
        panel.add(addFundsButton);
        panel.add(logOutButton);
        frame.add(panel);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                frame.dispose();
                System.exit(0);
            }
        });

        addFundsButton.addActionListener(e -> {
            addFunds(salesManager, currentBalance);
        });

        addProductButton.addActionListener(e -> {
            addProduct(salesManager, currentBalance);
        });

        purchaseHistoryButton.addActionListener(e -> {
            showSalesList();
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code for logout functionality
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose(); // close the current JFrame
                    MainMenuController.start();// show the login screen or exit the application
                }
            }
        });


    }


    private static SalesManager registerNewSalesManager() {
        JTextField nameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name: "));
        myPanel.add(nameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Email: "));
        myPanel.add(emailField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Password: "));
        myPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter contact details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if (StringHelpers.isNullOrEmpty(emailField.getText()) ||
                    StringHelpers.isNullOrEmpty(password) ||
                    StringHelpers.isNullOrEmpty(nameField.getText())) {

                JOptionPane.showMessageDialog(null, "Please provide all fields!");
                registerNewSalesManager();
            } else {

                String passwordHash = Sha256.generateSHA256(password);
                double initialBalance = 0.0;
                SalesManager salesManager = new SalesManager(nameField.getText(), emailField.getText(), passwordHash, initialBalance);
                DataBase.registerSalesManager(salesManager);
                return salesManager;

            }

        }
        return null; // if registration is cancelled
    }

    public static void addFunds(SalesManager salesManager, JLabel currentBalanceLabel) {

        JFrame addFundsFrame = new JFrame("Add funds");
        addFundsFrame.setSize(300, 100);

        JLabel label = new JLabel("Enter amount to add:");
        JTextField textField = new JTextField(10);
        JButton addButton = new JButton("Add");

        JPanel panel = new JPanel(new GridLayout(2, 3));
        panel.add(label);
        panel.add(textField);
        panel.add(addButton);

        addFundsFrame.add(panel);
        addFundsFrame.setVisible(true);

        addButton.addActionListener(e -> {
            double amount = Double.parseDouble(textField.getText());
            try {
                double newBalance = amount + salesManager.getShopBalance();
                DataBase.updateSalesManagerBalance(newBalance, salesManager.getEmail());
                salesManager.setShopBalance(newBalance);
                currentBalanceLabel.setText("Your current balance is: " + salesManager.getShopBalance() + " €");
                JOptionPane.showMessageDialog(addFundsFrame, "Funds added successfully.");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(addFundsFrame, "Failed to add funds: " + exception.getMessage());
            }
            addFundsFrame.dispose();
        });
    }

    private static void addProduct(SalesManager salesManager, JLabel currentBalanceLabel) {

        JFrame frame = new JFrame("What do you want to add?");

        JTextField productNameField = new JTextField(10);
        JTextField amountToPurchaseField = new JTextField(10);
        JTextField unitSizeField = new JTextField(10);
        JTextField purchasePriceField = new JTextField(10);
        JTextField retailPriceField = new JTextField(10);

        frame.setSize(400, 200);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name of product: "));
        myPanel.add(productNameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Amount to purchase : "));
        myPanel.add(amountToPurchaseField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Set unit size: "));
        myPanel.add(unitSizeField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Enter purchase price: "));
        myPanel.add(purchasePriceField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Enter retail price: "));
        myPanel.add(retailPriceField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter product details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Product product = new Product();
            product.setName(productNameField.getText());
            product.setAvailableQuantity(Double.parseDouble(amountToPurchaseField.getText()));
            product.setUnitSize(Double.parseDouble(unitSizeField.getText()));
            product.setPurchasePrice(Double.parseDouble(purchasePriceField.getText()));
            product.setRetailPrice(Double.parseDouble(retailPriceField.getText()));
            processPurchaseOrder(salesManager, product, currentBalanceLabel);
        }
    }

    private static void processPurchaseOrder(SalesManager salesManager, Product product, JLabel currentBalanceLabel) {
        if (salesManager.getShopBalance() < product.getPurchasePrice() * product.getAvailableQuantity()) {
            JOptionPane.showMessageDialog(null, "Insufficient funds to execute purchase!");
        } else {
            // Check if product exists in db
            Product existingProduct = DataBase.getProduct(product.getName());
            // Update Product
            if (existingProduct != null) {
                Product updatedProduct = new Product();
                updatedProduct.setName(existingProduct.getName());
                updatedProduct.setRetailPrice(product.getRetailPrice());
                updatedProduct.setAvailableQuantity(existingProduct.getAvailableQuantity() + product.getAvailableQuantity());
                updatedProduct.setPurchasePrice(product.getPurchasePrice());
                updatedProduct.setUnitSize(product.getUnitSize());
                DataBase.updateProduct(updatedProduct);
            }
            // Create new product
            else {
                DataBase.addProduct(product);
            }
            double productPurchasePrice = product.getPurchasePrice() * product.getAvailableQuantity();
            double newBalance = salesManager.getShopBalance() - productPurchasePrice;
            DataBase.updateSalesManagerBalance(newBalance, salesManager.getEmail());
            currentBalanceLabel.setText("Your current balance is: " + newBalance + " €");
            JOptionPane.showMessageDialog(null, "Product Added successfully!");
        }
    }

    private static void showSalesList() {
        ArrayList<PurchaseHistory> purchaseHistory = DataBase.getPurchasedProducts();
        JPanel panel = generateSalesHistoryPanel(purchaseHistory);

        JOptionPane.showMessageDialog(null, panel, "Shop purchase history", JOptionPane.INFORMATION_MESSAGE);

    }

    private static JPanel generateSalesHistoryPanel(ArrayList<PurchaseHistory> purchaseHistory){
        double totalProfit = 0;
        Vector<Vector<String>> dataVector = new Vector<>();
        for (PurchaseHistory item : purchaseHistory) {
            Vector<String> rowVector = new Vector<>();
            rowVector.add(item.getProductName());
            rowVector.add(Double.toString(item.getAmountSold()));
            rowVector.add(Double.toString(item.getRetailPrice()));
            rowVector.add(Double.toString(item.getPurchasePrice()));
            rowVector.add(Double.toString(item.getTotal()));
            double shopPrice = item.getAmountSold() * item.getPurchasePrice();
            double customerPrice = item.getAmountSold() * item.getRetailPrice();
            double netProfit = Math.round((customerPrice - shopPrice) * 100.0) / 100.0;
            totalProfit += netProfit;
            rowVector.add(Double.toString(netProfit));
            dataVector.add(rowVector);
        }
        Vector<String> columnNamesVector = new Vector<>();
        columnNamesVector.add("Product name");
        columnNamesVector.add("Amount purchased");
        columnNamesVector.add("Single item price");
        columnNamesVector.add("Shop price");
        columnNamesVector.add("Total price");
        columnNamesVector.add("Net profit");

        JLabel totalProfitLabel = new JLabel("Total profit: " + totalProfit + "€");

        DefaultTableModel tableModel = new DefaultTableModel(dataVector, columnNamesVector);
        JTable table = new JTable(tableModel);
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(table));
        panel.add(totalProfitLabel);
        return panel;
    }
}
