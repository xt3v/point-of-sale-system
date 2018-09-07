package controllers;

import TableDetails.CustomerDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Customer;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class viewCustomersControllers implements Initializable {
    @FXML
    TableView<CustomerDetails> debtorsTable;
    @FXML
    TableColumn<CustomerDetails,String> idColumn;
    @FXML TableColumn<CustomerDetails,String> nameColumn;
    @FXML TableColumn<CustomerDetails,String> phoneColumn;
    @FXML TableColumn<CustomerDetails,String> balanceColumn;
    @FXML TableColumn<CustomerDetails,String> addressColumn;

    private ObservableList<CustomerDetails> data; //store all the debtros colmns
    private DatabaseService db ;
    private ViewService vs;

    @FXML
    void goToMenu(){
        ViewService.getViewService().goToMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set column data source
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_no"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));


        db = DatabaseService.getDatabaseService();
        vs = ViewService.getViewService();
        populateTable();
    }
    //get data from db and populate table
    @FXML void populateTable() {
        //initialize tabel
        data = FXCollections.observableArrayList();
        debtorsTable.setItems(null);
        debtorsTable.setItems(data);

        ArrayList<Customer> list = db.getCustomerDAO().queryList("SELECT * FROM customers");
        for(Customer c : list){
            CustomerDetails detail = new CustomerDetails(String.format("%d",c.getId_no()),c.getName(),c.getPhone(),String.format("%.3f",c.getBalance()),c.getAddress());
            data.add(detail);
        }
    }

   @FXML void delete() {
        int res = vs.showConfirmation("Delete customer !!");
        if(res == JOptionPane.YES_OPTION){
            CustomerDetails customerDetails = debtorsTable.getSelectionModel().getSelectedItem();
            db.getCustomerDAO().deleteByIdentifier(Integer.parseInt(customerDetails.getId_no()));
            populateTable();
        }
    }
}
