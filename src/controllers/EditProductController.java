package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Product;
import models.Sale;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;
import javax.swing.text.View;

public class EditProductController {
    private Product product;
    private Stage stage;
    @FXML TextField name;
    @FXML TextField description;
    @FXML TextField price;

    public void setProduct(Stage stage,Product product){
        System.out.printf(product.getName());
       this.product = product;
       this.stage = stage;

       name.setText(product.getName());
       description.setText(product.getDescription());
       price.setText(String.format("%.3f",product.getPrice()));
    }

    @FXML void save() {
      try {
          int res = ViewService.getViewService().showConfirmation("Edit product");
          if(res == JOptionPane.YES_OPTION){
              if(name.getText().equals("") || price.getText().equals("") || description.getText().equals("")){
                ViewService.getViewService().showMessage("Fill All fields");
              }else{
                  product.setName(name.getText());
                  product.setPrice(Float.parseFloat(price.getText()));
                  product.setDescription(description.getText());
                  DatabaseService.getInstance().getProductDAO().save(product);
                  exit();
              }
          }
      }catch (Exception e){
          System.out.println(e.getMessage());
      }
    }

  @FXML void exit() {
        stage.close();
    }
}
