package pl.qaaacademy.restassured.shop_api.models;

public class Product {

    private String description;
    private String id;
    private int manufacturer;
    private float price;

    public Product() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", manufacturer=" + manufacturer +
                ", price=" + price +
                '}';
    }
}
