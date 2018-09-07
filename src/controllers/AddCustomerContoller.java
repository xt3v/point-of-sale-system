package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Customer;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerContoller implements Initializable{
    @FXML TextField name;
    @FXML TextField id_no;
    @FXML TextField phone;
    @FXML TextField address;
    @FXML Label errorlabel;

    @FXML void goToMenu(){
        ViewService.getViewService().goToMenu();
    }

    //clears all the input fields
    @FXML void clear(){
         name.setText("");
         id_no.setText("");
         phone.setText("");
         address.setText("");
    }

    @FXML void save(){
        int res = ViewService.getViewService().showConfirmation("Add customer ?");
         if(res == JOptionPane.YES_OPTION){
             if(!validate())return;   //validate if fails skip all
             DatabaseService db = DatabaseService.getInstance();
             db.getCustomerDAO().save(new Customer(Integer.parseInt(id_no.getText()),name.getText(),address.getText(),phone.getText(),0));
             clear();
         }
    }

    private boolean validate() {
        boolean res = true;
        errorlabel.setText("");  //clear text
        if(name.getText().trim().equals("") || id_no.getText().trim().equals("") || phone.getText().trim().equals("") ||
                address.getText().trim().equals("")){    //check if any filed is empty
            res = false;
            errorlabel.setText("Please Fill All Fields !!");  //set error prompt
        }
        return res;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set inputs not to focus
        id_no.focusTraversableProperty().setValue(false);
        name.focusTraversableProperty().setValue(false);
        phone.focusTraversableProperty().setValue(false);
        address.focusTraversableProperty().setValue(false);
    }
}
