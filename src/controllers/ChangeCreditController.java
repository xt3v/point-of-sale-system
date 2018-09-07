package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Customer;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;

public class ChangeCreditController {
   @FXML Label customername;
    @FXML Label currentcredit;
    @FXML TextField paidcredit;
    @FXML Label newcredit;
    @FXML Label id_no;

    private Customer customer;
    private Stage stage;

    public void setCustomer(Customer cus, Stage stage) {
        customer = cus;
        this.stage = stage;

        //set the labels
        customername.setText(cus.getName());
        currentcredit.setText(String.format("%.3f",cus.getBalance()));
        id_no.setText(String.format("%d",cus.getId_no()));
        paidcredit.setText("0");
        newcredit.setText("0");

    }


  @FXML void reduceCredit(ActionEvent actionEvent) {
      int res = ViewService.getViewService().showConfirmation("REDUCE CREDIT ");
      if(res == JOptionPane.YES_OPTION){
          customer.setBalance(Float.parseFloat(newcredit.getText()));
          DatabaseService db = DatabaseService.getDatabaseService();
          db.getCustomerDAO().save(customer);
      }
      exit();
    }

    //update the newcredit label
   @FXML void calculateNewCredit(){
     try {
         float curr = customer.getBalance();
         float paid = Float.parseFloat(paidcredit.getText().trim());
         float nwcred = curr - paid;
         newcredit.setText(String.format("%.3f",nwcred));
     }catch (Exception e){
         System.out.println(e.getMessage());
     }
   }
   @FXML void exit() {
        stage.close();
    }
}
