package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Product;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable{

    @FXML TextField name;
    @FXML TextField price;
    @FXML TextField description;
    @FXML Label  errorlabel;

    @FXML
    void goToMenu(){
        ViewService.getViewService().goToMenu();
    }

    //clears all the input fields
    @FXML void clear(){
        name.setText("");
        price.setText("");
        description.setText("");
    }

    @FXML void save(){
             int res = ViewService.getViewService().showConfirmation("Add Product");
             if(res == JOptionPane.YES_OPTION){
                 try{
                     if(!validate())return;   //validate if fails skip all
                     DatabaseService db = DatabaseService.getInstance();
                     db.getProductDAO().save(new Product(name.getText(),description.getText(),Float.parseFloat(price.getText()),0,false)); //save to db
                     clear();
                 }catch (Exception e){
                     System.out.println(e.getMessage());
                 }
             }
    }

    private boolean validate() {
        boolean res = true;
        errorlabel.setText("");  //clear text
        if(name.getText().trim().equals("") || price .getText().trim().equals("") || description.getText().trim().equals("") ){    //check if any filed is empty
            res = false;
            errorlabel.setText("Please Fill All Fields !!");  //set error prompt
        }
        return res;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set inputs not to focus
         price.focusTraversableProperty().setValue(false);
        name.focusTraversableProperty().setValue(false);
        description.focusTraversableProperty().setValue(false);
    }


}
