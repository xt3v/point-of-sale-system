package DAO;

import services.DatabaseHandler;
import java.sql.ResultSet;
import java.util.ArrayList;

/*
 *This interface defines a template for all Data Access Objects
 */
public interface DAO <S,T>{
    
    //object for interacting with db
    DatabaseHandler db = DatabaseHandler.getInstance();
    
    //get object by identifier
      S getByIdentifier(T identifier);
    
     //get all objects
       ArrayList<S> getAll();

       //enter raw sql to get object
      ArrayList<S> queryList(String sql);

      //enter row sql to get ResultSet
      ResultSet querySet(String sql);
    
    //delete from db3
     boolean deleteByIdentifier(T identifier);

     //add to db
    boolean save(S entry);
    
}
