package TableDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerDetails {
    private StringProperty id_no;
    private StringProperty name;
    private StringProperty phone;
    private StringProperty balance;
    private SimpleStringProperty address;

    public CustomerDetails(String id_no, String name, String phone, String balance, String address) {
        this.id_no = new SimpleStringProperty(id_no);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.balance = new SimpleStringProperty(balance);
        this.address = new SimpleStringProperty(address);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getId_no() {
        return id_no.get();
    }

    public StringProperty id_noProperty() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no.set(id_no);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getBalance() {
        return balance.get();
    }

    public StringProperty balanceProperty() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance.set(balance);
    }
}
