package Controllers;

import DataBase.DataBase;
import DataBase.Sha256;
import DataBase.StringHelpers;
import Users.Customer;

import javax.swing.*;

public class CustomerMenuController {

    public static void loginAsCustomer(){

        JTextField emailField = new JTextField(10);
        JPasswordField  passwordField = new JPasswordField (10);

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
        } else {
            registerNewCustomer();
        }

    }

    private static void loginAsCustomerValidation(String email, String password){
        if(StringHelpers.isNullOrEmpty(email) || StringHelpers.isNullOrEmpty(password) ) {
            JOptionPane.showMessageDialog(null, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
            loginAsCustomer();
        } else {
            String passwordHash = Sha256.generateSHA256(password);
            Customer loggedInUser = DataBase.loginCustomer(email, passwordHash);
            if (loggedInUser == null){
                JOptionPane.showMessageDialog(null, "Incorrect email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                loginAsCustomer();
            }else {
                //TODO GENERATE CUSTOMER MENU
            }
        }
    }

    private static Customer registerNewCustomer(){
        JTextField nameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JPasswordField  passwordField = new JPasswordField (10);

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
                return  customer;

            }

        }
        return null; // if registration is cancelled
    }
}
