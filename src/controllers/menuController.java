package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Admin;
import services.LoginService;
import services.ViewService;

import java.net.URL;
import java.util.ResourceBundle;

public class menuController  implements Initializable{
    @FXML Button addProdBtn;
    @FXML Button addCusBtn;
    @FXML Button  posBtn;
    @FXML Button viewSalesBtn;
    @FXML Button  stockBtn;
    @FXML Button  viewDebtorsBtn;
    @FXML Button viewCashierBtn;
    @FXML Button addCashierBtn;
    @FXML Button viewCustomer;

    @FXML void goToView(Event e){
        ViewService vs = ViewService.getViewService();

         if(e.getSource().equals(addProdBtn)){
               vs.changeScene("../views/addproduct.fxml");
         }else if(e.getSource().equals(addCusBtn)){
             vs.changeScene("../views/addcustomer.fxml");
         }else if(e.getSource().equals(posBtn)){
             vs.changeScene("../views/pos.fxml");
         }else if(e.getSource().equals(viewSalesBtn)){
             vs.changeScene("../views/viewsales.fxml");
         }else if(e.getSource().equals(stockBtn)){
             vs.changeScene("../views/displaystock.fxml");
         }else if(e.getSource().equals(viewCashierBtn)){
             vs.changeScene("../views/viewcashiers.fxml");
         }else if(e.getSource().equals(addCashierBtn)){
             vs.changeScene("../views/addcashier.fxml");
         }else if(e.getSource().equals(viewCustomer)){
             vs.changeScene("../views/viewcustomers.fxml");
         }else{
             vs.changeScene("../views/viewdebtors.fxml");
         }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set only admin to set and view cahsiers
        if(!(LoginService.getLoginSerice().getCashier() instanceof Admin)){
            addCashierBtn.visibleProperty().setValue(false);
            viewCashierBtn.visibleProperty().setValue(false);
        }
    }

    @FXML  void gotToUserView() {
       try{
           //setup up user view
           FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/userview.fxml"));
           Parent root = loader.load();
           UserViewController con = loader.getController();
           Stage stage = new Stage();
           con.setCashier(LoginService.getLoginSerice().getCashier(),stage);
           stage.setTitle("User Settings");
          stage.setScene(new Scene(root));
          stage.initModality(Modality.APPLICATION_MODAL);
          stage.showAndWait();

       }catch (Exception e){
           System.out.println(e.getMessage());
       }

    }

    public void logout() {
        LoginService.getLoginSerice().logout();
    }
}
