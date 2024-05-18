
function enterDeleteMode() {
    const deleteButton = document.querySelector('.delete-product');

    if (deleteModeEnabled) { // Exit delete mode if already enabled
        console.log("Exiting Delete Mode");
        deleteButton.classList.remove('delete-mode');
        deleteButton.innerHTML = '<i class="fa fa-trash"></i> Delete';
    } else {
        console.log("Entering Delete Mode");
        deleteButton.classList.add('delete-mode');
        deleteButton.innerHTML = '<i class="fa fa-trash"></i> Delete';
        deleteButton.innerHTML = '<i class="fa fa-check"></i> Delete';
    }
    deleteModeEnabled = !deleteModeEnabled; // Toggle delete mode
}

function handleClickProduct(event) {
    if (deleteModeEnabled) { // Check if in delete mode
        const target = event.target;
        console.log(target);
        if (target.classList.contains('product-name')) {
            const productName = target.dataset.productId; // Get the product name
            if (confirm(`Would you like to delete ${productName}?`)) {
                handleDeleteProduct(productName); // Call function to delete product
            }
        }
    }
}


function handleDeleteProduct(productIdentifier) {
    fetch('http://localhost:8080/deleteProduct', {
        method: 'delete',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({productId:productIdentifier}) // Send either product ID or product name
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            console.log(data);
            // Fetch the updated product list after deletion
            fetch('http://localhost:8080/products')
                .then(response => response.json())
                .then(products => {
                    updateProductTable(products); // Update the product table after successful deletion
                })
                .catch(error => console.error('Error fetching products:', error));
        })
        .catch(error => {
            console.error('There was a problem with the delete request:', error);
        });
}

 //Function to update product table with filtered results
function updateProductTable(products) {
    const table = document.getElementById('product-table');
    table.innerHTML = ''; // Clear existing table rows

    // Loop through each product and append it to the table
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

    // Add event listener to handle product deletion
    const productNames = document.querySelectorAll('.product-name');
    productNames.forEach(name => {
        name.addEventListener('click', handleClickProduct);
    });
}

// Wait for the DOM content to be loaded
window.addEventListener('DOMContentLoaded', function () {
    fetch('http://localhost:8080/products')
        .then(response => response.json())
        .then(products => {
            updateProductTable(products);

            // Add event listener to handle cell input during edit mode
            const editableCells = document.querySelectorAll('.editable');
            editableCells.forEach(cell => {
                cell.addEventListener('input', handleCellInput);
            });
        })
        .catch(error => console.error('Error fetching products:', error));
});
