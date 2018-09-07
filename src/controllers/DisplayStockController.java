package controllers;

import TableDetails.StockDetails;
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
import models.Product;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DisplayStockController  implements Initializable{

    @FXML TableView<StockDetails> stockTable;
    @FXML TableColumn<StockDetails,String>skuColumn;
    @FXML TableColumn<StockDetails,String> nameColumn;
    @FXML TableColumn<StockDetails,String>  quantityColumn;
    @FXML TableColumn<StockDetails,String> descriptionColumn;

    private ObservableList<StockDetails> data; //store all product details
    private DatabaseService db = null;

    @FXML
    void goToMenu(){
        ViewService.getViewService().goToMenu();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set column source
        skuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
       descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));



        db = DatabaseService.getInstance();

        populateList();
    }

    //fetches data from db add adds to table
    @FXML void populateList(){
        //initialize observable list
        data = FXCollections.observableArrayList();
        stockTable.setItems(null); //clear table

        db = DatabaseService.getInstance(); //get instance of db service
       ArrayList<Product> list = db.getProductDAO().getAll();  //get all prodtcs

        for (Product p : list) { //add each product list in data list
            data.add(new StockDetails(p.getSku(),p.getName(),String.format("%s",p.getQuantity()),p.getDescription()));
        }
        stockTable.setItems(data);  //add data to table
    }

    //open up addStock popup
    @FXML void addStock(){
        StockDetails st = stockTable.getFocusModel().getFocusedItem(); //get selected column
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/addstock.fxml"));   //set up add stock fxml
            Parent root = loader.load();   //load the fxml to root
            AddStockController con = loader.getController();     //get instance of controller
            Product product = (Product)db.getProductDAO().getByIdentifier(st.getSku());  //get product from db
            Stage stage = new Stage();
            con.setProduct(product,stage);  //set the Product on the pop up

            Scene scene = new Scene(root);    //set up pop up stage
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);   //set only one active stage
            stage.showAndWait();  //show stage

            populateList();  //refresh list
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public void deleteProduct(ActionEvent actionEvent) {
        try{
                int res = ViewService.getViewService().showConfirmation("Delete Product !");
            if(res == JOptionPane.YES_OPTION){
                StockDetails prod = stockTable.getSelectionModel().getSelectedItem();
                Product product = (Product) db.getProductDAO().getByIdentifier(prod.getSku());
                db.getProductDAO().deleteByIdentifier(prod.getSku());
                populateList();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void editProduct(ActionEvent actionEvent) {

        try{
            StockDetails prod = stockTable.getSelectionModel().getSelectedItem();
            Product product = (Product) db.getProductDAO().getByIdentifier(prod.getSku());
            FXMLLoader loader = new  FXMLLoader(getClass().getResource("../views/editproduct.fxml"));
            Parent root = loader.getRoot();
            EditProductController con = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            con.setProduct(stage,product);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            populateList();

         }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
