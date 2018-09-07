package controllers;

import TableDetails.CashierDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Admin;
import models.Cashier;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewCashiersController implements Initializable{
    @FXML TableView<CashierDetails> cashierTable;
    @FXML TableColumn<CashierDetails,String> nameColumn;
    @FXML TableColumn<CashierDetails,String> idColumn;
    @FXML TableColumn<CashierDetails,String> userColumn;
    @FXML TableColumn<CashierDetails,String> roleColumn;

    private  DatabaseService db;
    private ObservableList<CashierDetails> data; //to store table data

    @FXML
    void goToMenu(){
        ViewService.getViewService().goToMenu();
    }


    //delete the cashier from the db
   @FXML void deleteCashier() {
     int res =  ViewService.getViewService().showConfirmation("Delete User !!");
     if(res == JOptionPane.YES_OPTION){
         CashierDetails details = cashierTable.getSelectionModel().getSelectedItem();
         db.getCashierDAO().deleteByIdentifier(details.getId());   //delete cashier
         populate();
     }
    }

    //change the users role
    @FXML void changeRole(ActionEvent actionEvent) {
        int res =  ViewService.getViewService().showConfirmation("Change user User !!");
        if(res == JOptionPane.YES_OPTION) {
            CashierDetails details = cashierTable.getSelectionModel().getSelectedItem();  //get all cashier details
            Cashier cashier = (Cashier) db.getCashierDAO().getByIdentifier(details.getId());  //crea
            Cashier  cashiernew ;  //create new cashier
            if(details.getRole().equals("admin")){  //if cashier is admin change
                cashiernew = new Cashier(cashier.getName(),cashier.getEmployee_id(),cashier.getUsername(),cashier.getPassword());
            }else{  //if normal create admin
                cashiernew =  new  Admin(cashier.getName(),cashier.getEmployee_id(),cashier.getUsername(),cashier.getPassword());
            }
            db.getCashierDAO().save(cashiernew);  //update cashier
            populate();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set column data source
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        db = DatabaseService.getDatabaseService();

        populate();
    }

    private void populate() {
        //set table and list
        data = FXCollections.observableArrayList();
        cashierTable.setItems(null);
        cashierTable.setItems(data);

        ArrayList<Cashier> list = db.getCashierDAO().getAll(); //get cashiers

        for(Cashier c : list){  //for all cashier add to table
            String role = "normal";
            if(c instanceof Admin){  //check if cahsier is admin
                role = "admin";
            }
            CashierDetails details = new CashierDetails(c.getName(),c.getUsername(),c.getEmployee_id(),role);
            data.add(details);
        }
    }
}
