package models;


import java.util.Random;

public class Product{
     private String name;
     private String description;
     private float price;
     private int quantity;
     private String sku;

    public Product(String name, String description, float price, int quantity,boolean exists) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        if(!exists)createSku();
    }

    private void createSku() {
         StringBuilder sb = new StringBuilder();
         for (int i = 0;i<3;i++){
             char c = name.charAt(i);
             sb.append(Character.toUpperCase(c));
         }
         int random = new Random().nextInt(999);
         sb.append(random);
        for (int i = 0;i<3;i++){
            char c = name.charAt(i);
            sb.append(Character.toUpperCase(c));
        }

        sku = sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

}
