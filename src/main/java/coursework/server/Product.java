package coursework.server;

import com.google.gson.annotations.SerializedName;

//{"id":"664378d5f2415","productName":"Shirt","inventoryNumber":"77","category":"Shirt","type":"Clothes","color":"Red","price":"66","manufacturerPrice":"66","manufacturer":"Adidas","photo":"uploads\/apple14.jpg"}]
public class Product {
    private String id;
    private String productName;
    private String price;
    private String inventoryNumber;
    private String category;
    private String type;
    private String color;
    private String manufacturerPrice;
    private String manufacturer;
    private String photo;

    public Product() {

    }

    public Product(String id, String productName, String price, String inventoryNumber, String category,String type,String color, String manufacturerPrice, String manufacturer,String photo)
    {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.inventoryNumber = inventoryNumber;
        this.category = category;
        this.type = type;
        this.color = color;
        this.manufacturerPrice = manufacturerPrice;
        this.manufacturer = manufacturer;
        this.photo = photo;
    }
    @SerializedName("id")
    public String getId(){ return id;}
    @SerializedName("productName")
    public String getProductName(){ return productName;}
    @SerializedName("price")
    public String getPrice(){ return price;}
    @SerializedName("inventoryNumber")
    public String getInventoryNumber(){ return inventoryNumber;}
    @SerializedName("category")
    public String getCategory(){ return category;}
    @SerializedName("type")
    public String getType(){ return type;}
    @SerializedName("manufacturerPrice")
    public String getManufacturerPrice(){ return manufacturerPrice;}
    @SerializedName("manufacturer")
    public String getManufacturer(){ return manufacturer;}
    @SerializedName("photo")
    public String getPhoto(){ return photo;}
    @SerializedName("color")
    public String getColor(){ return color;}

}

