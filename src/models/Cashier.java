package models;



public class Cashier {
    private String name;
    private  String username;
    private String employee_id;
    private String password;

    public Cashier(String name, String employee_id, String username, String password) {
        this.name = name;
        this.employee_id = employee_id;
        this.username = username;
        this.password =password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
