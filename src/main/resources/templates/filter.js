// Function to apply filters
function applyFilters() {
    // Get selected filter values
    const categoryFilter = document.getElementById('category-filter').value;
    const typeFilter = document.getElementById('type-filter').value;
    const priceFilter = document.getElementById('price-filter').value;
    const colorFilter = document.getElementById('color-filter').value;
    const manufacturerFilter = document.getElementById('manufacturer-filter').value;
    const manufacturerPriceFilter = document.getElementById('manufacturer-price-filter').value;
    const searchTerm = document.getElementById('searchInput').value;

    // Construct the query string with all filters
    let queryString = `http://localhost:8080/filteredProducts?category=${encodeURIComponent(categoryFilter)}`;
    queryString += `&type=${encodeURIComponent(typeFilter)}`;
    queryString += `&price=${encodeURIComponent(priceFilter)}`;
    queryString += `&color=${encodeURIComponent(colorFilter)}`;
    queryString += `&manufacturer=${encodeURIComponent(manufacturerFilter)}`;
    queryString += `&manufacturerPrice=${encodeURIComponent(manufacturerPriceFilter)}`;
    queryString += `&searchTerm=${encodeURIComponent(searchTerm)}`;

    // Send AJAX request to fetch filtered products based on the selected filter(s)
    fetch(queryString)
        .then(response => response.json())
        .then(products => {
            // Update the product table with filtered results
            updateProductTable(products);
            // Toggle edit mode if it was previously enabled
            if (editModeEnabled) {
                toggleEditMode();
            }
        })
        .catch(error => console.error('Error applying filters:', error));

    // Hide filter popup after applying filters
    document.getElementById('filter-popup').style.display = 'none';
}
