package controllers;

import dataFormats.MessageToPOS;
import dataFormats.ProductsJson;
import TableDetails.ProductDetails;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Product;
import models.Sale;
import services.DatabaseService;
import services.ViewService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class PosController implements Initializable {
    @FXML TextField sku;
    @FXML Label productname;
    @FXML Label productprice;
    @FXML TableView<ProductDetails> posTable;
    @FXML TableColumn<ProductDetails, String> skuColumn;
    @FXML TableColumn<ProductDetails, String> nameColumn;
    @FXML TableColumn<ProductDetails, String> priceColumn;
    @FXML TableColumn<ProductDetails, String> subTotalColumn;
    @FXML TableColumn<ProductDetails, String> piecesColumn;
    @FXML TextField piecesSelected;
    @FXML Button addToCartBtn;
    @FXML Label grandtotal;
    @FXML Label balance;
    @FXML TextField cashgiven;
    @FXML Button checkoutBtn;

    private ObservableList<ProductDetails> data;  //store the producst to show on db
    private Product selectedProduct;  //Set the selected product used to populate table
    private DatabaseService db = null;  //handle db transactions
    private ViewService  vs;
    private Sale selectedSale;

    //changes to main menu
    @FXML void goToMenu() {
        vs.goToMenu();
    }

    //search for a product using sku
    @FXML void searchSku() {

           //search for the product by sku and populate labels
           if(!sku.getText().trim().equals("")){
               //Enable add button and pieces fieled
               piecesSelected.setDisable(false);
               addToCartBtn.setDisable(false);

               Product product = (Product) db.getProductDAO().getByIdentifier(sku.getText()); //get product
                if(null == product){
                    vs.showMessage("No such Product !!");
                }else{
                    productname.setText(product.getName());   //set product name to label
                    productprice.setText(String.format("%.3f", product.getPrice()));        //set product price to label
                    selectedProduct = product;  //set selected product

                    //check if product is availabel
                    if(product.getQuantity() < 1){
                        //if not available disable addtocart and pieces field
                        piecesSelected.setDisable(true);
                        addToCartBtn.setDisable(true);
                    }
                }

           }

    }

    /*
      *init method for the controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set the columns data source
        skuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        piecesColumn.setCellValueFactory(new PropertyValueFactory<>("pieces"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        //initialize observable list
        data = FXCollections.observableArrayList();
        posTable.setItems(null);
        posTable.setItems(data);   //add list to table

        db = DatabaseService.getInstance();   //get instance of database service
        vs = ViewService.getViewService();  //get instance of view service
    }

    /*
      called when want to add item to cart
     */
    @FXML void addProduct() {
        if(!piecesSelected.getText().trim().equals("") || !piecesSelected.getText().trim().equals("0")){    //check if pieces not empty
            int pieces = Integer.parseInt(piecesSelected.getText());  //get selected pieces

            //check if pieces selected are available
            if (null == selectedProduct)return;
             if(pieces <= selectedProduct.getQuantity()){
                float price = selectedProduct.getPrice();   ////get price
                float subtotal = price * pieces;  //calculate the subtotal
                data.add(new ProductDetails(selectedProduct.getSku(), selectedProduct.getName(), String.format("%.3f", price),
                        String.format("%d", pieces), String.format("%.3f", subtotal)));  //add product details to data list
                 calculateTotal(subtotal);   //calculate GrandTotal
                 clearSelectedItem();
            }else{
                 vs.showMessage("Only "+selectedProduct.getQuantity()+" pieces available !!");
             }
        }
    }

    //find balance
    @FXML void calculateBalance(){
        try{
            checkoutBtn.setDisable(false);
            Float total =  Float.parseFloat(grandtotal.getText());   //get total from label
            Float amountgiven = Float.parseFloat(cashgiven.getText()); //get cashgiven
            Float bal = amountgiven - total;    //calculate balance
            balance.setText(String.format("%.3f",bal));     //update label
        }catch (Exception e){
            System.out.println("Error !!");
            vs.showMessage("Please Enter A valid Numer !!");
            checkoutBtn.setDisable(true);
        }

    }


    //resets selected item to null and clears its data
    private void clearSelectedItem() {
        productname.setText("");   //clear product name to label
        productprice.setText("");        //clear  product price to label
        selectedProduct = null;  //clear selected product
        piecesSelected.setText("");  //clear selected pieces
    }

    //Gets the grand total of products
    private void calculateTotal(float subtotal) {
        Float total = Float.parseFloat(grandtotal.getText());   //get total from label
        total+=subtotal;       //add new subtotal
        grandtotal.setText(String.format("%.3f",total));  //update label
    }

    //Action for check out button
    //also checks if user is to pay with credit
    @FXML void checkOut(ActionEvent actionEvent) {
        if(posTable.getItems().size() > 0){
            int    confirm = vs.showConfirmation("Check Out !!");  //show confrim box
            if(confirm == JOptionPane.YES_OPTION){      //checkout if true
                float bal = Float.parseFloat(balance.getText());

                if(bal < 0){   //pay with credit

                    try {
                        //show pay with credit modal
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/paybycredit.fxml"));
                        Parent root =  loader.load();
                        PayByCreditController con = loader.getController();
                        Stage stage = new Stage();
                        con.setTotal(bal,stage);
                        stage.setTitle("ADD STOCK");
                        stage.setScene(new Scene(root));
                        stage.showAndWait();

                        //get message from modal
                        MessageToPOS pos = (MessageToPOS) vs.getMessage("pos");
                        if(null != pos){
                            if(pos.isSuccess()){
                                addSaleToDB(pos.getCredit());
                                showReceipt();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    //pay normal
                    addSaleToDB(0);
                    showReceipt();  //show receipt
                }

            }
        }else{
            ViewService.getViewService().showMessage("PLEASE ADD ITEMS");
        }

    }

    private void showReceipt() {
     try{
         //show pay with credit modal
         FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/receipt.fxml"));
         Parent root =  loader.load();
         ReceiptController con = loader.getController();
         Stage stage = new Stage();
         con.setSale(selectedSale);
         stage.setTitle("Receipt");
         stage.setScene(new Scene(root));
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.showAndWait();
     } catch (IOException e) {
         e.printStackTrace();
     }
    }


    //add sale to db
    private void addSaleToDB(float credit) {
        createSale(credit);
        db.getSaleDAO().save(selectedSale);
         reduceStoc();  //reduce stock of bought

        //clear fields
         clearFields();
    }

    //educes the stock of procuts bought
    private void reduceStoc() {
        Gson gson = new Gson();
        for(JsonElement j : selectedSale.getProducts()){
            ProductsJson pj = gson.fromJson(j,ProductsJson.class);
            Product product = (Product)db.getProductDAO().getByIdentifier(pj.sku);
            product.setQuantity(product.getQuantity()- pj.amount);
            db.getProductDAO().save(product);
        }
    }

    private void createSale(float credit) {
        LocalDate date = LocalDate.now();
        Time time = new Time(System.nanoTime());

        JsonArray json = createProductJson();    //create the json product array
        selectedSale = new Sale(date,json,time,Float.parseFloat(grandtotal.getText()),credit,false);

    }


    //creates the prodcurs json Array
    private JsonArray createProductJson() {
        JsonArray jsonRay = new JsonArray();
        for (ProductDetails details : data){
              ProductsJson p = new ProductsJson();
              p.sku = details.getSku();
              p.amount =  Integer.parseInt(details.getPieces());
              JsonElement json = new JsonParser().parse(new Gson().toJson(p));
              jsonRay.add(json);

              //reduce from stock
               reduceStock(details.getSku(),Integer.parseInt(details.getPieces()));
        }
        return jsonRay;
    }

    private void reduceStock(String sku, int pieces) {
        Product product = (Product) db.getProductDAO().getByIdentifier(sku);
         db.getProductDAO().save(product);
    }


    //clear the inut fileds
    private void clearFields() {
        sku.setText("");
        productprice.setText("");
        productname.setText("");
        posTable.setItems(null);
        data = FXCollections.observableArrayList();
        posTable.setItems(data);

         grandtotal.setText("0");
       balance.setText("0");
       cashgiven.setText("0");

       piecesSelected.setText("0");

    }

   //remove item from cart
    @FXML void  removeItem(ActionEvent actionEvent) {

        ProductDetails prod = posTable.getSelectionModel().getSelectedItem();  //get selected product
        data.remove(prod);

        posTable.setItems(null);
        posTable.setItems(data);   //update table

        //recalculate total
        grandtotal.setText(String.format("%.3f",Float.parseFloat(grandtotal.getText()) - (Float.parseFloat(prod.getPrice()) * Float.parseFloat(prod.getPieces()))));
    }
}
