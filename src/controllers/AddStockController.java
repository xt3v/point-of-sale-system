package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Product;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;

public class AddStockController {
    @FXML
    TextField addstock;
    @FXML Label productname;
    @FXML Label sku;
    @FXML Label currentstock;
    @FXML Label newstock;


    private Product product;  //holds the product worked on
    private Stage stage;   //this current stage;

    //function to set product to be worked on
    public void  setProduct(Product product,Stage stage){
             this.product = product;
             //set labels
             productname.setText(product.getName());
             sku.setText(product.getSku());
              currentstock.setText(String.format("%s",product.getQuantity()));

              this.stage  = stage; //set stage
    }

    //adds the stock to the product and saves to db
    @FXML void  addStock(){
          int res = ViewService.getViewService().showConfirmation("ADD STOCK !"); //show confirmation
          if(res == JOptionPane.YES_OPTION){ //add stock if true
              product.setQuantity(Integer.parseInt(newstock.getText()));
              DatabaseService db = DatabaseService.getDatabaseService();
              db.getProductDAO().save(product);

          }
           exit();  //exit modal popup
    }

    //to be called when value entered in add field
    @FXML void calculate(){
         try {
             //get current plus addition stock and find new the update label
             int old = Integer.parseInt(currentstock.getText());
             int add = Integer.parseInt(addstock.getText());
             int nwStock = old+add;
             newstock.setText(String.format("%d",nwStock));
         }catch (Exception e){
             System.out.println(e.getMessage());
         }

    }

    @FXML void exit(){
       stage.close();
    }

}
