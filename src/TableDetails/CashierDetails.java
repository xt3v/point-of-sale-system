package TableDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CashierDetails {
    private StringProperty name;
    private  StringProperty user;
    private  StringProperty id;
    private StringProperty role;


    public CashierDetails(String name, String user, String  id, String role) {
        this.name = new SimpleStringProperty(name);
        this.user = new SimpleStringProperty(user);
        this.id = new SimpleStringProperty(id);
        this.role = new SimpleStringProperty(role);
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

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }
}
