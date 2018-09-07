package controllers;

import TableDetails.CustomerDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Customer;
import services.DatabaseService;
import services.ViewService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewDebtorsController implements Initializable {
    @FXML TableView<CustomerDetails> debtorsTable;
    @FXML TableColumn<CustomerDetails,String> idColumn;
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


   @FXML public void reduceDebt(ActionEvent actionEvent) {
        CustomerDetails debtor = debtorsTable.getSelectionModel().getSelectedItem(); //get selected column

       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/changecredit.fxml"));   //set up add stock fxml
           Parent root = loader.load();   //load the fxml to root
           ChangeCreditController con = loader.getController();     //get instance of controller
           Customer cus = (Customer) db.getCustomerDAO().getByIdentifier(debtor.getId_no());  //get product from db
           Stage stage = new Stage();
           con.setCustomer(cus,stage);  //set the customer on the pop up

           Scene scene = new Scene(root);    //set up pop up stage
           stage.setScene(scene);
           stage.initModality(Modality.APPLICATION_MODAL);   //set only one active stage
           stage.showAndWait();  //show stage

           populateTable(); //refresh list
       } catch (IOException e) {
           e.printStackTrace();
       }


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

        ArrayList<Customer> list = db.getCustomerDAO().queryList("SELECT * FROM customers WHERE balance > 0");
        for(Customer c : list){
            CustomerDetails detail = new CustomerDetails(String.format("%d",c.getId_no()),c.getName(),c.getPhone(),String.format("%.3f",c.getBalance()),c.getAddress());
            data.add(detail);
        }
    }
}
