package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Admin;
import models.Cashier;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;


public class AddCashierController {
    @FXML TextField name;
    @FXML TextField id;
    @FXML TextField user;
    @FXML CheckBox admin;

    @FXML
    void goToMenu(){
        ViewService.getViewService().goToMenu();
    }
    @FXML void clear() {
        name.setText("");
        id.setText("");
        user.setText("");
        admin.setSelected(false);
    }

    @FXML void addEmployee() {
       int res = ViewService.getViewService().showConfirmation("Add cashier !");
       if(res == JOptionPane.YES_OPTION){
           DatabaseService db = DatabaseService.getDatabaseService();
           Cashier cashier ;
           if(admin.isSelected()){
               cashier = new Admin(name.getText(),id.getText(),user.getText(),"password");
           }else{
               cashier = new Cashier(name.getText(),id.getText(),user.getText(),"password");
           }
           db.getCashierDAO().save(cashier);
           clear();
       }
    }
}
