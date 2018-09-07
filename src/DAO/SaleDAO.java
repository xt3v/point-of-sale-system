package DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import models.Customer;
import models.Sale;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class SaleDAO implements DAO {
    @Override
    public Object getByIdentifier(Object identifier) {
        Sale sale = null;
        try{
            String  id = (String) identifier;  //type cast to datatype of identifier
            //query db
            ResultSet rs = db.executeQuery("SELECT  * FROM sales WHERE sale_id = '"+id+"'");

            //if result has been gotten create customer
            if(rs.next()){
                JsonArray ray = (JsonArray) new JsonParser().parse(rs.getString("products"));

                sale = new Sale(getLocalDate(rs.getString("date")),ray,rs.getTime("time"),rs.getFloat("total"),rs.getFloat("credited"),true);
                sale.setSale_id(rs.getString("sale_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }


    @Override
    public ArrayList getAll() {
        ArrayList<Sale> list  = new ArrayList<>();
        try{
            ResultSet rs = db.executeQuery("SELECT  * FROM products");
            //for each row create customer and add to list

            while (rs.next()){
                JsonArray ray = (JsonArray) new JsonParser().parse(rs.getString("products"));
                Sale sale = new Sale(getLocalDate(rs.getString("date")),ray,rs.getTime("time"),
                        rs.getFloat("total"),rs.getFloat("credited"),true);
                sale.setSale_id(rs.getString("sale_id"));
                list.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList queryList(String sql) {
        ArrayList<Sale> list  = new ArrayList<>();
        try{
            ResultSet rs = db.executeQuery(sql);
            //for each row create customer and add to list
            while (rs.next()){
                JsonArray ray = (JsonArray) new JsonParser().parse(rs.getString("products"));
                Sale sale = new Sale(getLocalDate(rs.getString("date")),ray,rs.getTime("time"),rs.getFloat("total"),rs.getFloat("credited"),true);
                sale.setSale_id(rs.getString("sale_id"));
                list.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ResultSet querySet(String sql) {
        //queries and returns resulset as is
        return db.executeQuery(sql);
    }

    @Override
    public boolean deleteByIdentifier(Object identifier) {
        String  id = (String)identifier;
        //delet entry with the identifier given
        int rs =  db.executeAction("DELETE FROM sales WHERE sale_id = '"+id+"'");

        //check if successfull
        if (rs == 1) return true;
        return false;
    }

    @Override
    public boolean save(Object entry) {
        Sale sale  = (Sale) entry; //type cast object to customer

        String prods = new Gson().toJson(sale.getProducts());  //convert JSONArray products to String format
        String time = sale.getTime().toLocalTime().toString();  //convert time to string
      String date =  String.format("%d/%d/%d",sale.getDate().getMonth().getValue(),sale.getDate().getDayOfMonth(),sale.getDate().getYear() );//set date format

        //check if already exists and delete to update
         Sale  oldsale  = (Sale) getByIdentifier(sale.getSale_id());
        if(null != oldsale){
            deleteByIdentifier(oldsale.getSale_id());
        }

        //sql to be executed
        String sql = "INSERT INTO sales(sale_id,products,time,date,total,credited)"+
                "VALUES('"+sale.getSale_id()+"','"+prods+"','"+time+" ', '"+date+" ' ,'"+sale.getTotal()+"','"+sale.getCredited()+"') ";
        int rs = db.executeAction(sql);

        //check if successfull
        if(rs == 1)return true;
        return false;
    }

    //change a date 8/9/218 to LocalDate
    private LocalDate getLocalDate(String date) {
         String[] nums = date.split("/");
         int day = Integer.parseInt(nums[1]);
         int month = Integer.parseInt(nums[0]);
         int year = Integer.parseInt(nums[2].trim());
         return LocalDate.of(year,month,day);
    }

}
