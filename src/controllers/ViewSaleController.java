package controllers;

import TableDetails.ProductDetails;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dataFormats.ProductsJson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Product;
import models.Sale;
import services.DatabaseService;


public class ViewSaleController {

    @FXML TableView<ProductDetails> saleTable;
    @FXML TableColumn<ProductDetails, String> skuColumn;
    @FXML TableColumn<ProductDetails, String> nameColumn;
    @FXML TableColumn<ProductDetails, String> priceColumn;
    @FXML TableColumn<ProductDetails, String> subTotalColumn;
    @FXML TableColumn<ProductDetails, String> piecesColumn;

    @FXML Label saleid;
    @FXML Label date;
    @FXML Label time;
    @FXML Label total;
    @FXML Label credit;
    @FXML Label cash;

    private ObservableList<ProductDetails> data;  //store the producst to show on db
    private Sale sale;  //Set the selected product used to populate table
    private DatabaseService db = null;  //handle db transactions
    private Stage stage;

    public void setSale(Sale sale,Stage stage){
        this.stage = stage;
        this.sale = sale;

        //set labels
        saleid.setText(sale.getSale_id());
        date.setText(sale.getDate().toString());
        time.setText(sale.getTime().toLocalTime().toString());
        total.setText(String.format("%.3f",sale.getTotal()));
        credit.setText(String.format("%.3f",sale.getCredited()));
        cash.setText(String.format("%.3f",sale.getTotal()-sale.getCredited()));

        populateTable();
    }

    private void populateTable() {
        //set the columns data source
        skuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        piecesColumn.setCellValueFactory(new PropertyValueFactory<>("pieces"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        DatabaseService db = DatabaseService.getDatabaseService();
        data = FXCollections.observableArrayList();


        for(JsonElement json : sale.getProducts()){  //get json elemts in array
            ProductsJson pj = new Gson().fromJson(json,ProductsJson.class);   //convert in ProductJson class
            Product  p =   (Product)db.getProductDAO().getByIdentifier(pj.sku);//get the product from db
            float total = p.getPrice() * pj.amount;
            ProductDetails details = new ProductDetails(pj.sku,p.getName(),String.format("%.3f",p.getPrice()),String.format("%d",pj.amount)
                    ,String.format("%.3f",total));    //details to store on table
            data.add(details);
            System.out.println(p.getSku());
        }
        saleTable.setItems(null);
        saleTable.setItems(data);
    }


}
