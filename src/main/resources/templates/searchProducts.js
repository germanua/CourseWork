
// Function to search products by name
function searchProducts() {
    console.log('searchProducts function called');
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();

    // Send AJAX request to fetch filtered products
    fetch('http://localhost:8080/products?searchTerm=' + encodeURIComponent(searchTerm))
        .then(response => response.json())
        .then(products => {
            const table = document.getElementById('product-table');
            table.innerHTML = ''; // Clear existing table rows

            // Loop through filtered products and append to the table
            products.forEach(product => {
                const row = document.createElement('tr');
                row.innerHTML = `
            <td><img src="${product.photo}" alt="Product Photo" style="width: 100px;"></td>
            <td class="product-name editable" data-product-id="${product.id}" data-field="productName">${product.productName}</td>
            <td class="editable" data-product-id="${product.id}" data-field="price">${product.price}</td>
            <td class="editable" data-product-id="${product.id}" data-field="inventoryNumber">${product.inventoryNumber}</td>
            <td class="editable" data-product-id="${product.id}" data-field="category">${product.category}</td>
            <td class="editable" data-product-id="${product.id}" data-field="type">${product.type}</td>
            <td class="editable" data-product-id="${product.id}" data-field="color">${product.color}</td>
            <td class="editable" data-product-id="${product.id}" data-field="manufacturerPrice">${product.manufacturerPrice}</td>
            <td class="editable" data-product-id="${product.id}" data-field="manufacturer">${product.manufacturer}</td>
                `;
                table.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching filtered products:', error));

    // Show search results popup
    document.getElementById('search-popup').style.display = 'block';

// Add event listener to handle cell input during edit mode
    const editableCells = document.querySelectorAll('.editable');
    editableCells.forEach(cell => {
        cell.addEventListener('input', handleCellInput);
    });

}


