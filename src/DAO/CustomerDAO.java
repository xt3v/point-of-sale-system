package DAO;

import models.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO implements DAO {

    @Override
    public Object getByIdentifier(Object identifier) {
        Customer customer = null;
        try{
            int id ;
        if(identifier instanceof Integer) {
            id = (int)identifier;
            //type cast to datatype of identifier
        }else{
            id =  Integer.parseInt((String)identifier);
        }
            //query db
           ResultSet rs = db.executeQuery("SELECT  * FROM customers WHERE id_no = '"+id+"'");

           //if result has been gotten create customer
           if(rs.next()){
               customer = new Customer(rs.getInt("id_no"),rs.getString("name"),
                       rs.getString("address"),rs.getString("phone"),rs.getFloat("balance"));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return customer;
    }

    @Override
    public ArrayList getAll() {
        ArrayList<Customer> list  = new ArrayList<>();
        try{
            ResultSet rs = db.executeQuery("SELECT  * FROM customers ");
           //for each row create customer and add to list
            while (rs.next()){
                Customer customer = new Customer(rs.getInt("id_no"),rs.getString("name"),
                        rs.getString("address"),rs.getString("phone"),rs.getFloat("balance"));
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList queryList(String sql) {
        ArrayList<Customer> list  = new ArrayList<>();
        try{
            ResultSet rs = db.executeQuery(sql);
            //for each row create customer and add to list
            while (rs.next()){
                Customer customer = new Customer(rs.getInt("id_no"),rs.getString("name"),
                        rs.getString("address"),rs.getString("phone"),rs.getFloat("balance"));
                list.add(customer);
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
        int id = (int)identifier;
        //delet entry with the identifier given
        int rs =  db.executeAction("DELETE FROM customers WHERE id_no = '"+id+"'");

        //check if successfull
        if (rs == 1) return true;
        return false;
    }

    @Override
    public boolean save(Object entry) {
        //create object
        Customer customer = (Customer) entry;

        //check if already exists and delete to update
        Customer oldcus = (Customer) getByIdentifier(customer.getId_no());
        if(null != oldcus){
            deleteByIdentifier(oldcus.getId_no());
        }

        String sql = "INSERT INTO customers(name,address,phone,balance,id_no)"+
                              "VALUES('"+customer.getName()+"','"+customer.getAddress()+"','"+customer.getPhone()+"',"+
                               "'"+customer.getBalance()+"','"+customer.getId_no()+"') ";
        int rs = db.executeAction(sql);
        if(rs == 1)return true;
        return false;
    }
}
