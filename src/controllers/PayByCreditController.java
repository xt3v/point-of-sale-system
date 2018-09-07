package controllers;

import dataFormats.MessageToPOS;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Customer;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;

public class PayByCreditController {
    @FXML TextField  id_no;
    @FXML Label name;
    @FXML Label currentcredit;
    @FXML Label addcredit;
    @FXML Label totalcredit;
    @FXML Label errorlabel;

    private Stage stage;
    private float credit;   //holds the total to be credited
    private DatabaseService db ;
    private Customer customer;

    public void setTotal(float credit, Stage stage){  //get the total to be credited from POS
        this.credit = credit*-1;
       this.stage = stage;
        addcredit.setText(String.format("%.3f",this.credit));  //set label to show credit
        db = DatabaseService.getDatabaseService();
    }

    //search the db for the Customeer using ID
   @FXML void searchId() {
        customer = (Customer)db.getCustomerDAO().getByIdentifier(id_no.getText());

       if(null != customer){  //if customer exists
           //set up labels
           name.setText(customer.getName());
           currentcredit.setText(String.format("%.3f",customer.getBalance()));
           totalcredit.setText(String.format("%.3f",credit+customer.getBalance()));
       }
    }

  @FXML void exit() {
       stage.close(); ///close the stage
    }

    @FXML void addCredit() {  //check if credit is viable and add to database
       if(id_no.getText().equals("")){
           ViewService.getViewService().showMessage("ENTER ID");
       }else{
           float totalcredit = Float.parseFloat(this.totalcredit.getText());

           if(totalcredit < 5000){  //credit must not exceed 5000ksh
              int res = ViewService.getViewService().showConfirmation("Pay By Credit");
              if(res == JOptionPane.YES_OPTION){
                  //Change customer credit
                  customer.setBalance(totalcredit);
                  db.getCustomerDAO().save(customer);

                  ///pass message to POS and close
                  MessageToPOS mess = new MessageToPOS(true,credit);
                  ViewService.getViewService().passMessage("pos",mess);

                  exit();
              }
           }else{
               errorlabel.setText("Cannot award credit more than 5000 !");
           }
       }

    }
}
