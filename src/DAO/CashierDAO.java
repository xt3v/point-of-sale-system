package DAO;


import models.Admin;
import models.Cashier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 Data Access Object for ShopKeeper  class
 */
public class CashierDAO implements DAO {

    @Override
    public Object getByIdentifier(Object identifier) {
        Cashier cashier = null;
        try{
            String  id = (String)identifier;  //type cast to datatype of identifier
            //query db
            ResultSet rs = db.executeQuery("SELECT  * FROM cashiers WHERE employee_id = '"+id+"'");

            //if result has been gotten create customer
            if(rs.next()){
               if(rs.getBoolean("admin") ){

                   cashier = new Admin(rs.getString("name"),rs.getString("employee_id"),
                           rs.getString("username"),rs.getString("password"));
               }else{

                   cashier = new Cashier(rs.getString("name"),rs.getString("employee_id"),
                           rs.getString("username"),rs.getString("password"));
               }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cashier;
    }

    @Override
    public ArrayList<Cashier> getAll(){
        ResultSet rs = db.executeQuery("SELECT  * FROM cashiers");
        return createList(rs);
    }


    @Override
    public ArrayList<Cashier> queryList(String sql) {
            ResultSet rs = db.executeQuery(sql);
           return  createList(rs);
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
        int rs =  db.executeAction("DELETE FROM cashiers WHERE employee_id  = '"+id+"'");

        //check if successfull
        if (rs == 1) return true;
        return false;
    }

    @Override
    public boolean save(Object entry) {
      Cashier cashier = (Cashier) entry;
      int isAdmin = 0;
      if(cashier instanceof Admin){
          isAdmin = 1;
      }
        //check if already exists and delete to update
        Cashier  oldcashier = (Cashier) getByIdentifier(cashier.getEmployee_id());
        if(null != oldcashier){
            deleteByIdentifier(cashier.getEmployee_id());
        }
        //sql to be executed
        String sql = "INSERT INTO cashiers(name,username,password,employee_id,admin)"+
                "VALUES('"+ cashier.getName()+"','"+ cashier.getUsername()+"','"+ cashier.getPassword()+"',"+
                "'"+ cashier.getEmployee_id()+"','"+isAdmin+"') ";
        int rs = db.executeAction(sql);

        //check if successfull
        if(rs == 1)return true;
        return false;
    }

    //loops throught resultset and creates arraylist
    private ArrayList<Cashier> createList(ResultSet rs) {
        ArrayList<Cashier> list  = new ArrayList<>();
        try{
            while (rs.next()){
                Cashier cashier;
                if(rs.getBoolean("admin") ){
                    cashier = new Admin(rs.getString("name"),rs.getString("employee_id"),
                            rs.getString("username"),rs.getString("password"));
                }else{
                    cashier = new Cashier(rs.getString("name"),rs.getString("employee_id"),
                            rs.getString("username"),rs.getString("password"));
                }

                list.add(cashier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
