package models;
/*
 *A shopkeeper with more previledges
 */
public class Admin extends Cashier {

    public Admin(String name, String employee_id, String username, String password) {
        super(name, employee_id, username, password);
    }
}
