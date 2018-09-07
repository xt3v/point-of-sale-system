package models;

import com.google.gson.JsonArray;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;


public class Sale
{
    private LocalDate date;
    private JsonArray products;
    private Time time;
    private float total;
    private float credited;
    private String sale_id;

    public Sale(LocalDate date, JsonArray products, Time time, float total, float credited,boolean exists){
        this.date = date;
        this.products = products;
        this.time = time;
        this.total = total;
        this.credited = credited;
        if(!exists)createSaleId();

    }
//
//    public Sale(String date, JsonArray products, Time time, float total, float credited,boolean exists){
//        this.date = LocalDate.parse(date);
//        this.products = products;
//        this.time = time;
//        this.total = total;
//        this.credited = credited;
//        if(!exists)createSaleId();
//    }

    //generate random saleid
    private void createSaleId() {
       StringBuilder sb = new StringBuilder();
        sb.append('T');
        int random = new Random().nextInt(999);
        sb.append(random);

         random =  new Random().nextInt(9);
         sb.append('C');
         sb.append(random);
         sale_id = sb.toString();
    }


    public JsonArray getProducts() {
        return products;
    }

    public void setProducts(JsonArray products) {
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getCredited() {
        return credited;
    }

    public void setCredited(float credited) {
        this.credited = credited;
    }

    public String getSale_id() {
        return sale_id;
    }
}
