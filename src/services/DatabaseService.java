package services;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import DAO.SaleDAO;
import DAO.CashierDAO;

/*
 * This service acts as a house for all the Database objects
 */
public class DatabaseService {
    //to enable only one instance of object for whole system
     private static DatabaseService databaseService = null;
     private CustomerDAO customerDAO;
     private ProductDAO productDAO;
     private CashierDAO cashierDAO;
     private SaleDAO saleDAO;

     //Constructor for internal call
     private DatabaseService(){
         customerDAO = new CustomerDAO();
         productDAO = new ProductDAO();
         cashierDAO = new CashierDAO();
         saleDAO = new SaleDAO();
     }

    public static DatabaseService getDatabaseService() {
        return databaseService;
    }

    public SaleDAO getSaleDAO() {
        return saleDAO;
    }

    //to be called to give an instance of the serice
    public static DatabaseService getInstance(){
       if (databaseService == null )
           databaseService = new DatabaseService();

       return databaseService;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public CashierDAO getCashierDAO() {
        return cashierDAO;
    }
}
