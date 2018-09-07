package TableDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SalesDetails {
    private StringProperty sale_id;
    private StringProperty time;
    private  StringProperty items;
    private StringProperty total;
    private StringProperty credited;

    public SalesDetails(String sale_id, String time, String items, String total, String credited) {
        this.sale_id = new SimpleStringProperty(sale_id);
        this.time = new SimpleStringProperty(time);
        this.items = new SimpleStringProperty(items);
        this.total = new SimpleStringProperty(total);
        this.credited = new SimpleStringProperty(credited);
    }

    public String getSale_id() {
        return sale_id.get();
    }

    public StringProperty sale_idProperty() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id.set(sale_id);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getItems() {
        return items.get();
    }

    public StringProperty itemsProperty() {
        return items;
    }

    public void setItems(String items) {
        this.items.set(items);
    }

    public String getTotal() {
        return total.get();
    }

    public StringProperty totalProperty() {
        return total;
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public String getCredited() {
        return credited.get();
    }

    public StringProperty creditedProperty() {
        return credited;
    }

    public void setCredited(String credited) {
        this.credited.set(credited);
    }
}
