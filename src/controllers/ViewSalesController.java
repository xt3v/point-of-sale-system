package controllers;

import TableDetails.SalesDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Sale;
import services.DatabaseService;
import services.ViewService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewSalesController  implements Initializable{
    @FXML DatePicker dateselect;
    @FXML TableView<SalesDetails> salesTable;
    @FXML TableColumn<SalesDetails,String> saleIdColumn;
    @FXML TableColumn<SalesDetails,String> totalColumn;
    @FXML TableColumn<SalesDetails,String> creditColumn;
    @FXML TableColumn<SalesDetails,String> itemColumn;
    @FXML TableColumn<SalesDetails,String> timeColumn;

    @FXML Label grandtotal;
    @FXML Label totalcredited;
    @FXML Label totalcash;

    private ObservableList<SalesDetails> data;  //to store table data
    private DatabaseService db;

    @FXML
    void goToMenu(){
        ViewService.getViewService().goToMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set column data source
        saleIdColumn.setCellValueFactory(new PropertyValueFactory<>("sale_id"));
       totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
       creditColumn.setCellValueFactory(new PropertyValueFactory<>("credited"));
       itemColumn.setCellValueFactory(new PropertyValueFactory<>("items"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        db  = DatabaseService.getDatabaseService();

        //set labels
        grandtotal.setText("0");
        totalcash.setText("0");
        totalcredited.setText("0");

    }


    //searches for
    @FXML void getSalesByDate(){
        //set labels
        grandtotal.setText("0");
        totalcash.setText("0");
        totalcredited.setText("0");

        //setup table and data
        data = FXCollections.observableArrayList();
        salesTable.setItems(null);
        salesTable.setItems(data);

        //get items and format
         String dateString = dateselect.editorProperty().get().getText();
         ArrayList<Sale> sales = db.getSaleDAO().queryList("SELECT * FROM sales WHERE date = '"+dateString+"'");
         if(sales.size() < 1){
                ViewService.getViewService().showMessage("No sales on that day");
         }else{
             for(Sale s :  sales){
                 String items = String.format("%d",s.getProducts().size());
                 SalesDetails details = new SalesDetails(s.getSale_id(),s.getTime().toLocalTime().toString(),items,String.format("%.3f",s.getTotal()),String.format("%.3f",s.getCredited()));
                 data.add(details);
                 calculateTotals(s);  //set labels
             }
         }


      }

      @FXML void viewSale(){
          //get selected item and get sell object
           SalesDetails details = salesTable.getSelectionModel().getSelectedItem();
           Sale sale = (Sale)db.getSaleDAO().getByIdentifier(details.getSale_id());

           //show show sale modal
          try{
              FXMLLoader loader = new  FXMLLoader(getClass().getResource("../views/viewsale.fxml"));
              Parent root = loader.load();
              ViewSaleController con = loader.getController();
              Stage stage = new Stage();
              con.setSale(sale,stage);
              stage.setTitle("View Sale");
              stage.setScene(new Scene(root));
              stage.initModality(Modality.APPLICATION_MODAL);
              stage.showAndWait();

          }catch (Exception e){
             e.printStackTrace();
          }
      }

    private void calculateTotals(Sale s) {
        float grand = Float.parseFloat(grandtotal.getText().trim());
        float credited = Float.parseFloat(totalcredited.getText().trim());

        grand+= s.getTotal();      //increment grand with new total
        credited+=s.getCredited();  //increment credited with new

        float cash = grand - credited;

        grandtotal.setText(String.format("%.3f",grand));
        totalcredited.setText(String.format("%.3f",credited));
        totalcash.setText(String.format("%.3f",cash));
    }

}
