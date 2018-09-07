package services;

import java.sql.*;
/*
  *This class houses the database connection and the crud methods to query
  * or update database
 */
public class DatabaseHandler {
    //The database handler instance
    private static DatabaseHandler databaseHandler = null;

    //The Database connection
   private Connection conn = null;

   //Sql statement
   private Statement statement = null;

   //Private constructor
   private DatabaseHandler(){
          createConnection();
    }

    //create a database connection
    private void createConnection() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shop","root","");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //public method to get the db instance
    public static  DatabaseHandler  getInstance(){
        if(databaseHandler == null){
            databaseHandler = new DatabaseHandler();
        }

        return databaseHandler;
    }


    //method to query the db for results
  public ResultSet executeQuery(String sql){
          ResultSet rs = null;
         try{
              statement = conn.createStatement();
              rs = statement.executeQuery(sql);

         } catch (SQLException e) {
             e.printStackTrace();
         }

         return rs;
  }

  //method to execute query
public int executeAction(String sql){
         int rs = 0;
         try{
             statement = conn.createStatement();
             rs = statement.executeUpdate(sql);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    return rs;
}

}
