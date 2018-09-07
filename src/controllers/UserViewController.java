package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Cashier;
import models.Customer;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;

public class UserViewController {
    @FXML TextField user;
    @FXML PasswordField password;
    @FXML Label id;
    @FXML Label errorlabel;

    private Cashier cashier;
    private Stage stage;
    private DatabaseService db;

    //called to pass user details
    public void setCashier(Cashier cashier,Stage stage){
        this.cashier = cashier;
        this.stage = stage;

        //set up labels
        id.setText(cashier.getEmployee_id());
        user.setText(cashier.getUsername());

        db = DatabaseService.getDatabaseService();
    }

    @FXML void exit(){
        stage.close();
    }

    @FXML  void changeDetails() {
        if(!user.getText().trim().equals("")){
             int dialogResult = ViewService.getViewService().showConfirmation("Change Settings"); //show confirm dialog
            if(dialogResult == JOptionPane.YES_OPTION){ //check if YES confirmed
                if(validate(false)){ //validate user
                    cashier.setUsername(user.getText().trim());  //set username to new
                }
                 if(!password.getText().trim().equals("")){   //check if password set to change
                     if(validate(true)){   //if password is valid
                           cashier.setPassword(password.getText().trim());    //set new password
                           db.getCashierDAO().save(cashier);        //save user
                     }
                 }
            }
        }else{
            errorlabel.setText("Username Cannot be empty !");
        }
    }

    //validate length of password
    private boolean validate(boolean isPass) {
        String text;
       if(isPass){
           text  = password.getText().trim();
       }else{
           text = user.getText().trim();
       }
        boolean res = true;
        if(text.length() < 5){
            errorlabel.setText("Password should be more than 5 characters !");
            return false;
        }

        //check if password has spaces
        for(Character c : text.toCharArray()){
            if(Character.isWhitespace(c)){
                errorlabel.setText("NO spaces in password");
                return false;
            }
        }
        return res;
    }

    @FXML void clear(ActionEvent actionEvent) {
    }
}
