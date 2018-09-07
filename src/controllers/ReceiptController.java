package controllers;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dataFormats.ProductsJson;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import models.Product;
import models.Sale;
import services.DatabaseService;


public class ReceiptController {
    
    @FXML TextArea receiptarea;

    private Sale sale;
    

    public void  setSale(Sale sale){
        this.sale = sale;
        printArea();
    }

    private void printArea() {
        receiptarea.appendText("                       "+sale.getSale_id()+"\n\n");
        receiptarea.appendText("Name            Pieces          Total\n");
        JsonArray ray = sale.getProducts();
        for(JsonElement pj:ray){
            ProductsJson p = new Gson().fromJson(pj,ProductsJson.class);
            Product po = (Product) DatabaseService.getDatabaseService().getProductDAO().getByIdentifier(p.sku);
            receiptarea.appendText(po.getName()+"                   "+p.amount+"                 "+(p.amount*po.getPrice())+" \n");
        }
        float total = sale.getTotal();
        float credited = sale.getCredited();
        float cash = total - credited;
        receiptarea.appendText("\n\n");
        receiptarea.appendText("Total  : "+total+ "  Cash :  "+cash+"\n");
        receiptarea.appendText("Credited : "+credited+"\n\n");
        receiptarea.appendText("DATE :  "+sale.getDate().toString()+"   "+sale.getTime().toLocalTime().toString());

    }

}
