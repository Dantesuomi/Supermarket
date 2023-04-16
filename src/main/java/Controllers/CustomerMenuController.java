package Controllers;

import DataBase.DataBase;
import DataBase.Sha256;
import DataBase.StringHelpers;
import Users.Customer;
import Users.Product;
import Users.PurchaseHistory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;



public class CustomerMenuController {

    public static void loginAsCustomer() {

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
            loginAsCustomerValidation(emailField.getText(), password);
        }
        else if (result == JOptionPane.NO_OPTION) {
            Customer registeredCustomer = registerNewCustomer();
            if (registeredCustomer != null) {
                generateCustomerMenu(registeredCustomer);
            }
            else {
                MainMenuController.start();
            }
        }
        else {
            MainMenuController.start();
        }
    }

    private static void loginAsCustomerValidation(String email, String password) {
        if (StringHelpers.isNullOrEmpty(email) || StringHelpers.isNullOrEmpty(password)) {
            JOptionPane.showMessageDialog(null, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
            loginAsCustomer();
        } else {
            String passwordHash = Sha256.generateSHA256(password);
            Customer loggedInUser = DataBase.loginCustomer(email, passwordHash);
            if (loggedInUser == null) {
                JOptionPane.showMessageDialog(null, "Incorrect email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                loginAsCustomer();
            } else {
                //TODO GENERATE CUSTOMER MENU
                generateCustomerMenu(loggedInUser);
            }
        }
    }

    private static Customer registerNewCustomer() {
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
                registerNewCustomer();
            } else {

                String passwordHash = Sha256.generateSHA256(password);
                double initialBalance = 0.0;
                Customer customer = new Customer(nameField.getText(), emailField.getText(), passwordHash, initialBalance);
                DataBase.registerCustomer(customer);
                return customer;

            }

        }
        return null; // if registration is cancelled
    }

    private static void generateCustomerMenu(Customer customer) {
        JFrame frame = new JFrame("Account");

        JButton buyProductButton = new JButton("Buy Product");
        JButton purchaseHistoryButton = new JButton("Purchase history");
        JButton addFundsButton = new JButton("Add funds");
        JButton logOutButton = new JButton("Log out");

        frame.setSize(400, 200);
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel customerName = new JLabel("Welcome: " + customer.getName());
        JLabel currentBalance = new JLabel("Your current balance is: " + customer.getBalance() + " €");
        panel.add(customerName);
        panel.add(currentBalance);
        panel.add(buyProductButton);
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

        buyProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyProduct(customer, currentBalance);
                }
            });

        purchaseHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPurchaseList(customer.getEmail());
            }
        });

        addFundsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFunds(customer, currentBalance);
            }
        });


    }

    private static void buyProduct(Customer customer, JLabel currentBalanceLabel) {
        ArrayList<Product> productList = DataBase.getAllAvailableProducts();
        Product[] products = productList.toArray(new Product[productList.size()]);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Select product to buy: "));
        JComboBox<Product> productBox = new JComboBox<>(products);
        myPanel.add(productBox);

        JTextField productAmountField = new JTextField(10);
        myPanel.add(new JLabel("Please, enter amount of product: "));
        myPanel.add(productAmountField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please select product to buy and amount", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Product selectedProduct = (Product) productBox.getSelectedItem();
            Double amount = Double.parseDouble(productAmountField.getText());
            if(selectedProduct.getAvailableQuantity() < amount){
                JOptionPane.showMessageDialog(null, "Sorry, this amount of product is not available!");
            } else if (amount * selectedProduct.getRetailPrice() > customer.getBalance()) {
                JOptionPane.showMessageDialog(null, "Insufficient balance to buy!");
            }
            else {
                processPurchase(selectedProduct, customer, amount, currentBalanceLabel);
            }
        }
    }

    private static void processPurchase(Product selectedProduct, Customer customer, Double amount, JLabel currentBalanceLabel) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setCustomerEmail(customer.getEmail());
        purchaseHistory.setProductName(selectedProduct.getName());
        purchaseHistory.setAmountSold(amount);
        purchaseHistory.setPurchasePrice(selectedProduct.getPurchasePrice());
        purchaseHistory.setRetailPrice(selectedProduct.getRetailPrice());
        purchaseHistory.setTotal(selectedProduct.getRetailPrice() * amount);

        DataBase.recordPurchase(purchaseHistory);

        Product updatedProduct = new Product(selectedProduct);
        updatedProduct.setAvailableQuantity(selectedProduct.getAvailableQuantity() - amount);
        DataBase.updateProduct(updatedProduct);

        double productPurchasePrice = selectedProduct.getRetailPrice() * amount;
        double newBalance = customer.getBalance() - productPurchasePrice;
        currentBalanceLabel.setText("Your current balance is: " + newBalance + " €");
        DataBase.updateCustomerBalance(newBalance, customer.getEmail());
    }

    private static void showPurchaseList(String customerEmail) {
        ArrayList<PurchaseHistory> purchaseHistory = DataBase.getPurchasedProducts(customerEmail);
        JPanel panel = generateProductPanel(purchaseHistory);

        JOptionPane.showMessageDialog(null, panel, "Your purchase history", JOptionPane.INFORMATION_MESSAGE);

    }

    private static JPanel generateProductPanel(ArrayList<PurchaseHistory> purchaseHistory){

        Vector<Vector<String>> dataVector = new Vector<>();
        for (PurchaseHistory item : purchaseHistory) {
            Vector<String> rowVector = new Vector<>();
            rowVector.add(item.getProductName());
            rowVector.add(Double.toString(item.getAmountSold()));
            rowVector.add(Double.toString(item.getRetailPrice()));
            rowVector.add(Double.toString(item.getTotal()));
            dataVector.add(rowVector);
        }
            Vector<String> columnNamesVector = new Vector<>();
            columnNamesVector.add("Product name");
            columnNamesVector.add("Amount purchased");
            columnNamesVector.add("Single item price");
            columnNamesVector.add("Total price");

            DefaultTableModel tableModel = new DefaultTableModel(dataVector, columnNamesVector);
            JTable table = new JTable(tableModel);
            JPanel panel = new JPanel();
            panel.add(new JScrollPane(table));
            return panel;
        }

    public static void addFunds(Customer customer, JLabel currentBalanceLabel) {

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
            Double amount = Double.parseDouble(textField.getText());
            try {
                double newBalance = amount + customer.getBalance();
                DataBase.updateCustomerBalance(newBalance, customer.getEmail());
                customer.setBalance(newBalance);
                currentBalanceLabel.setText("Your current balance is: " + customer.getBalance() + " €");
                JOptionPane.showMessageDialog(addFundsFrame, "Funds added successfully.");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(addFundsFrame, "Failed to add funds: " + exception.getMessage());
            }
            addFundsFrame.dispose();
        });

    }

}

