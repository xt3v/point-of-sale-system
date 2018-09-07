package DAO;

import models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 Data Access Object for Product class
 */
public class ProductDAO implements DAO {
    @Override
    public Object getByIdentifier(Object identifier) {
        Product product = null;
        try{
            String  id = (String)identifier;  //type cast to datatype of identifier
            //query db
            ResultSet rs = db.executeQuery("SELECT  * FROM products WHERE sku = '"+id+"'");

            //if result has been gotten create customer
            if(rs.next()){
                product = new Product(rs.getString("name"),rs.getString("description"),
                  rs.getFloat("price"),rs.getInt("quantity"),true);
                product.setSku(rs.getString("sku"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public ArrayList getAll() {
        ArrayList<Product> list  = new ArrayList<>();
        try{
            ResultSet rs = db.executeQuery("SELECT  * FROM products");
            //for each row create customer and add to list
            while (rs.next()){
              Product  product = new Product(rs.getString("name"),rs.getString("description"),
                        rs.getFloat("price"),rs.getInt("quantity"),true);
                product.setSku(rs.getString("sku"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList queryList(String sql) {
        ArrayList<Product> list  = new ArrayList<>();
        try{
            ResultSet rs = db.executeQuery(sql);
            //for each row create customer and add to list
            while (rs.next()){
                Product  product = new Product(rs.getString("name"),rs.getString("description"),
                        rs.getFloat("price"),rs.getInt("quantity"),true);
                product.setSku(rs.getString("sku"));
                list.add(product);
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
        int rs =  db.executeAction("DELETE FROM products WHERE sku = '"+id+"'");

        //check if successfull
        if (rs == 1) return true;
        return false;
    }

    @Override
    public boolean save(Object entry) {
        Product product = (Product) entry; //type cast object to customer

        //check if already exists and delete to update
        Product oldproduct = (Product) getByIdentifier(product.getSku());
        if(null != oldproduct){
               deleteByIdentifier(product.getSku());
        }
        //sql to be executed
        String sql = "INSERT INTO products(sku,description,name,price,quantity)"+
                "VALUES('"+product.getSku()+"','"+product.getDescription()+"','"+product.getName()+"',"+
                "'"+product.getPrice()+"','"+product.getQuantity()+"') ";
        int rs = db.executeAction(sql);

        //check if successfull
        if(rs == 1)return true;
        return false;
    }
}
