package models;

public class Customer {
    private int id_no;
    private String name;
    private String address;
    private String phone;
    private float balance;

    public Customer(int id_no, String name, String address, String phone, float balance) {
        this.id_no = id_no;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.balance = balance;


    }

    public int getId_no() {
        return id_no;
    }

    public void setId_no(int id_no) {
        this.id_no = id_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

}
