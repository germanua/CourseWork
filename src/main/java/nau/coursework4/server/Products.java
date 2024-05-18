package nau.coursework4.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
public class Products {
    List<Product> data = new ArrayList<Product>();
    public Products() {
        data = readFromFile("products.json");
    }

    public List<Product> getProducts() {
        return data;
    }
    public Product getProductById(String id) {
        for (Product product : data) {
            if (Objects.equals(product.getId(), id)) {
                return product;
            }
        }
        return null;
    }
    public void addProduct(Product product) {
        data.add(product);
        saveListToFile(data, "products.json");
    }
    public void updateProduct(Product product) {
        data.removeIf(p -> Objects.equals(p.getId(), product.getId()));
        data.add(product);
        saveListToFile(data, "products.json");
    }
    public void removeProduct(String id) {
        data.removeIf(product -> Objects.equals(product.getId(), id));
        saveListToFile(data, "products.json");
    }

    public List<Product> searchTerm(String term) {
        return data.stream()
                .filter(product -> product.getProductName().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }
    public void removeProductsByCategory(String category) {
        data.removeIf(product -> product.getCategory().equals(category));
        saveListToFile(data, "products.json");
    }

    public List<String> getCategories() {
        return new ArrayList<String>(new HashSet<String>(data.stream()
                .map(p -> {
                    return p.getCategory();
                })
                .collect(Collectors.toList())));

    }

    public List<Product> applyFilters(List<Product> products, String category, String type, String price, String color, String manufacturer, String manufacturerPrice) {
        return products.stream()
                .filter(product -> category == null || category.isEmpty() || product.getCategory().equalsIgnoreCase(category))
                .filter(product -> type == null || type.isEmpty() || product.getType().equalsIgnoreCase(type))
                .filter(product -> price == null || price.isEmpty() || product.getPrice().equalsIgnoreCase(price))
                .filter(product -> color == null || color.isEmpty() || product.getColor().equalsIgnoreCase(color))
                .filter(product -> manufacturer == null || manufacturer.isEmpty() || product.getManufacturer().equalsIgnoreCase(manufacturer))
                .filter(product -> manufacturerPrice == null || manufacturerPrice.isEmpty() || product.getManufacturerPrice().equalsIgnoreCase(manufacturerPrice))
                .collect(Collectors.toList());
    }


    private List<Product> readFromFile(String fileName) {
        Type REVIEW_TYPE = new TypeToken<List<Product>>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            saveListToFile(data, fileName);
            return data;
        }
        return gson.fromJson(reader, REVIEW_TYPE);
    }
    private void saveListToFile(List<Product> list, String fileName) {
        try (Writer writer = new FileWriter("products.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(list, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
