package TableDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StockDetails {
    private StringProperty sku;
    private StringProperty name;
    private StringProperty description;
    private StringProperty quantity;

    public StockDetails(String sku,String name, String quantity,String description) {
        this.sku = new SimpleStringProperty(sku);
        this.name = new SimpleStringProperty( name);
        this.quantity = new SimpleStringProperty(quantity);
        this.description = new SimpleStringProperty(description);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getSku() {
        return sku.get();
    }

    public StringProperty skuProperty() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku.set(sku);
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

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }
}
