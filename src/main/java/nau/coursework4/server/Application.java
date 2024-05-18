package nau.coursework4.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Controller
public class Application {
    private final Products products = ProductsStoreSingleton.getInstance();
    public final static MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");


    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/login.html")
    public String welcome() {
        return "login";
    }

    @RequestMapping("/inventory.html")
    public String inventar() {
        return "inventory";
    }
    @RequestMapping("/Categories.js")
    public String categorii() {
        return "Categories.js";
    }
    @RequestMapping("/delete.js")
    public String del() {
        return "delete.js";
    }
    @RequestMapping("/filter.js")
    public String filtr() {
        return "filter.js";
    }
    @RequestMapping("/searchProducts.js")
    public String searchProd() {
        return "searchProducts.js";
    }
    @RequestMapping("/updateProductTable.js")
    public String prodtable() {
        return "updateProductTable.js";
    }

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCatalog(@RequestParam(required = false) String searchTerm) {
        List<Product> filteredProducts = searchTerm != null ? products.searchTerm(searchTerm) : products.getProducts();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return new ResponseEntity<>(gson.toJson(filteredProducts), HttpStatus.OK);
    }

    @GetMapping(path = "/filteredProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getFilteredProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String manufacturerPrice,
            @RequestParam(required = false) String searchTerm) {


        List<Product> filteredProducts = products.getProducts();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            filteredProducts = products.searchTerm(searchTerm);
        }

        filteredProducts = products.applyFilters(filteredProducts, category, type, price, color, manufacturer, manufacturerPrice);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return new ResponseEntity<>(gson.toJson(filteredProducts), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteProduct",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteProduct(@RequestBody Map<String, Object> datamap) {
        products.removeProduct(datamap.get("productId").toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(path = "/deleteCategory", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteProductsByCategory(@RequestBody Map<String, Object> datamap) {
        products.removeProductsByCategory(datamap.get("categoryName").toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> fetchCategories() {
        List<String> categories = products.getCategories();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return new ResponseEntity<>(gson.toJson(categories), HttpStatus.OK);
    }

    @GetMapping(path = "/downloadCsv", produces = "text/csv")
    public ResponseEntity<Object> CSVExport (){
        return new ResponseEntity<>( products.data.stream()
                .map(p -> {
                    return p.getId() + "," + p.getProductName() + "," + p.getPrice() + "," + p.getInventoryNumber() + "," + p.getCategory()+ "," + p.getType()+ "," + p.getColor()+ "," + p.getManufacturerPrice()+ "," + p.getManufacturer();
                })
                .collect(Collectors.joining("\n")), HttpStatus.OK);
    }



    @PatchMapping(path= "/updateProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProduct(@RequestBody Map<String, Object> datamap){
        ProductBuilder productBuilder = new ProductBuilder(products.getProductById(datamap.get("productId").toString()));
        switch (datamap.get("field").toString())
        {
            case "photo":
                productBuilder.setPhoto(datamap.get("value").toString());
                break;
            case "productName":
                productBuilder.setProductName(datamap.get("value").toString());
                break;
            case "price":
                productBuilder.setPrice(datamap.get("value").toString());
                break;
            case "inventoryNumber":
                productBuilder.setInventoryNumber(datamap.get("value").toString());
                break;
            case "category":
                productBuilder.setCategory(datamap.get("value").toString());
                break;
            case "type":
                productBuilder.setType(datamap.get("value").toString());
                break;
            case "color":
                productBuilder.setColor(datamap.get("value").toString());
                break;
            case "manufacturer":
                productBuilder.setManufacturer(datamap.get("value").toString());
                break;
            case "manufacturerPrice":
                productBuilder.setManufacturerPrice(datamap.get("value").toString());
                break;
        }
        products.updateProduct(productBuilder.build());
        return new ResponseEntity<Object>(HttpStatus.OK);
    }



    @PostMapping(path = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addProduct(@RequestBody Map<String, Object> datamap) {
        ProductBuilder productBuilder = new ProductBuilder();

        productBuilder.setId(UUID.randomUUID().toString());
        productBuilder.setProductName(datamap.get("productName").toString());
        productBuilder.setPrice(datamap.get("price").toString());
        productBuilder.setInventoryNumber(datamap.get("inventoryNumber").toString());
        productBuilder.setType(datamap.get("type").toString());
        productBuilder.setCategory(datamap.get("category").toString());
        productBuilder.setManufacturer(datamap.get("manufacturer").toString());
        productBuilder.setManufacturerPrice(datamap.get("manufacturerPrice").toString());
        productBuilder.setColor(datamap.get("color").toString());
        productBuilder.setPhoto(datamap.get("photo").toString());

        Product product = productBuilder.build();

        products.addProduct(product);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }




}
