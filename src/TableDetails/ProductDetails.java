package TableDetails;

import javafx.beans.property.SimpleStringProperty;

public class ProductDetails {
    private SimpleStringProperty sku;
    private SimpleStringProperty name;
    private SimpleStringProperty price;
    private SimpleStringProperty pieces;
    private SimpleStringProperty subtotal;


    public ProductDetails(String sku,String name, String price, String pieces,String subtotal) {
        this.sku = new SimpleStringProperty(sku);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.pieces = new SimpleStringProperty(pieces);
        this.subtotal = new SimpleStringProperty(subtotal);

    }


    public String getPieces() {
        return pieces.get();
    }

    public SimpleStringProperty piecesProperty() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces.set(pieces);
    }

    public String getSku() {
        return sku.get();
    }

    public SimpleStringProperty skuProperty() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku.set(sku);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getSubtotal() {
        return subtotal.get();
    }

    public SimpleStringProperty subtotalProperty() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal.set(subtotal);
    }
}
