package  nau.coursework4.server;
public class ProductBuilder {
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

    public ProductBuilder() {

    }

    public ProductBuilder(String id, String productName, String price, String inventoryNumber, String category,String type,String color, String manufacturerPrice, String manufacturer,String photo)
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

    public ProductBuilder(Product product)
    {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.inventoryNumber = product.getInventoryNumber();
        this.category = product.getCategory();
        this.type = product.getType();
        this.color = product.getColor();
        this.manufacturerPrice = product.getManufacturerPrice();
        this.manufacturer = product.getManufacturer();
        this.photo = product.getPhoto();
    }
public Product build(){
        return new Product(id, productName,price, inventoryNumber, category, type, color, manufacturerPrice, manufacturer, photo);}

    public ProductBuilder setId(String id){
            this.id = id;
            return this;
    }

    public ProductBuilder setProductName(String productName){
        this.productName = productName;
        return this;
    }
    public ProductBuilder setPrice(String price){
        this.price = price;
        return this;
    }
    public ProductBuilder setInventoryNumber(String inventoryNumber){
        this.inventoryNumber = inventoryNumber;
        return this;
    }
    public ProductBuilder setCategory(String category){
        this.category = category;
        return this;
    }
    public ProductBuilder setType(String type){
        this.type = type;
        return this;
    }
    public ProductBuilder setColor(String color){
        this.color = color;
        return this;
    }
    public ProductBuilder setManufacturerPrice(String manufacturerPrice){
        this.manufacturerPrice = manufacturerPrice;
        return this;
    }
    public ProductBuilder setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
        return this;
    }
    public ProductBuilder setPhoto(String photo){
        this.photo = photo;
        return this;
    }



}





