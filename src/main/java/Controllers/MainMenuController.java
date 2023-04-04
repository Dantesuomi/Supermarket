package Controllers;

import javax.swing.*;

public class MainMenuController {
    public static void start(){
        Object[] mainMenuOptions = {"Customer", "Sales Manager"};
        JOptionPane optionPane = new JOptionPane();
        optionPane.setMinimumSize(new java.awt.Dimension(300, 300));
        int result = optionPane.showOptionDialog(null,
                "Welcome to the supermarket!\nChoose your option:",
                "Supermarket",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                mainMenuOptions,  //the titles of buttons
                mainMenuOptions[0]); //default button title
        if(result == JOptionPane.OK_OPTION){
            CustomerMenuController.loginAsCustomer();

        }
        else {

        }
    }
}
