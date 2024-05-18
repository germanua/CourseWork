package nau.coursework4.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {
    private Products products;

    @BeforeEach
    void setUp() {
        products = new Products();
    }

    @Test
    void testGetProducts() {
        List<Product> productList = products.getProducts();
        assertNotNull(productList);
        assertFalse(productList.isEmpty());
    }

    @Test
    void testGetProductById() {
        // Test retrieving an existing product by ID
        Product existingProduct = products.getProductById("1");
        assertNotNull(existingProduct);
        assertEquals("1", existingProduct.getId());

        // Test retrieving a non-existing product by ID
        Product nonExistingProduct = products.getProductById("100");
        assertNull(nonExistingProduct);
    }

    @Test
    void testAddProduct() {
        Product newProduct = new ProductBuilder()
                .setId("1")
                .setProductName("New Product")
                .setCategory("Category")
                .setType("Type")
                .setPrice("Price")
                .setColor("Color")
                .setManufacturer("Manufacturer")
                .setManufacturerPrice("Manufacturer Price")
                .setPhoto("Photo") // Assuming you have a photo field in your Product class
                .build();

        products.addProduct(newProduct);
        assertNotNull(products.getProductById("1"));
    }

    @Test
    public void testUpdateProduct() {
        // Prepare test data
        Product product = new ProductBuilder()
                .setId("123")
                .setProductName("Test Product")
                .setPrice("10")
                .setInventoryNumber("123456")
                .setCategory("TestCategory")
                .setType("TestType")
                .setColor("Red")
                .setManufacturerPrice("15")
                .setManufacturer("Test Manufacturer")
                .setPhoto("test.jpg")
                .build();

        // Update the product
        products.updateProduct(product);

        // Retrieve the updated product
        Product updatedProduct = products.getProductById(product.getId());

        // Assertion
        assertNotNull(updatedProduct);
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getProductName(), updatedProduct.getProductName());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        assertEquals(product.getInventoryNumber(), updatedProduct.getInventoryNumber());
        assertEquals(product.getCategory(), updatedProduct.getCategory());
        assertEquals(product.getType(), updatedProduct.getType());
        assertEquals(product.getColor(), updatedProduct.getColor());
        assertEquals(product.getManufacturerPrice(), updatedProduct.getManufacturerPrice());
        assertEquals(product.getManufacturer(), updatedProduct.getManufacturer());
        assertEquals(product.getPhoto(), updatedProduct.getPhoto());
    }

    @Test
    void testRemoveProduct() {
        products.removeProduct("1");
        assertNull(products.getProductById("1"));
    }

    @Test
    void testSearchTerm() {
        List<Product> searchResult = products.searchTerm("product");
        assertFalse(searchResult.isEmpty());
    }

    @Test
    void testRemoveProductsByCategory() {
        // Count the number of products before removal
        int initialProductCount = products.getProducts().size();

        // Remove products with the specified category
        products.removeProductsByCategory("Category");

        // Count the number of products after removal
        int finalProductCount = products.getProducts().size();

        // Ensure that no products with the specified category exist after removal
        assertTrue(products.getProducts().stream().noneMatch(product -> product.getCategory().equals("Category")));

        // Ensure that the number of products has decreased after removal
        assertEquals(initialProductCount - finalProductCount, 1);
    }

    @Test
    void testGetCategories() {
        List<String> categories = products.getCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
    }

    @Test
    void testApplyFilters() {
        List<Product> filteredProducts = products.applyFilters(products.getProducts(), null, null, null, null, null, null);
        assertNotNull(filteredProducts);
        assertFalse(filteredProducts.isEmpty());
    }
}
