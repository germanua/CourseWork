function updateProduct(productId, field, value) {
    fetch('http://localhost:8080/updateProduct', {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json",
        },
        body:  JSON.stringify({productId:productId,field:field,value:value})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the update request:', error);
        });
}

// Function to handle cell input during edit mode
function handleCellInput(event) {
    console.log("Cell input detected"); // Add this line
    if (editModeEnabled) {
        const cell = event.target;
        console.log(cell)
        const productId = cell.dataset.productId;
        const field = cell.dataset.field; // Get the field name from the dataset
        const value = cell.innerText.trim(); // Get the trimmed text content of the cell
        updateProduct(productId, field, value);
    }
}


// Function to toggle edit mode
function toggleEditMode() {
    const editButton = document.getElementById('edit-button');
    const editableCells = document.querySelectorAll('.editable');

    editModeEnabled = !editModeEnabled;
console.log(editModeEnabled)
    if (editModeEnabled) {
        // Add event listener to handle cell input during edit mode
        editableCells.forEach(cell => {
            cell.addEventListener('input', handleCellInput);
        });

        editButton.classList.add('edit-mode');
        editButton.innerHTML = '<i class="fa fa-check"></i> Save';
        // Log the number of editable cells when entering edit mode
        console.log("Number of editable cells:", editableCells.length);
    } else {
        // Add event listener to handle cell input during edit mode
        editableCells.forEach(cell => {
            cell.removeEventListener('input', handleCellInput);
        });
        editButton.classList.remove('edit-mode');
        editButton.innerHTML = '<i class="fa fa-pencil"></i> Edit';
    }

    editableCells.forEach(cell => {
        cell.contentEditable = editModeEnabled;
    });
}


// Function to update product table with filtered results
function updateProductTable(products) {
    const table = document.getElementById('product-table');
    table.innerHTML = ''; // Clear existing table rows

    // Loop through each product and append it to the table
    products.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td><img src="${product.photo}" alt="Product Photo" style="width: 100px;"></td>
            <td class="product-name editable"  data-product-id="${product.id}" data-field="productName">${product.productName}</td>
            <td class="editable" data-product-id="${product.id}" data-field="price">${product.price}</td>
            <td class="editable" data-product-id="${product.id}" data-field="inventoryNumber">${product.inventoryNumber}</td>
            <td class="editable" data-product-id="${product.id}" data-field="category">${product.category}</td>
            <td class="editable" data-product-id="${product.id}" data-field="type">${product.type}</td>
            <td class="editable" data-product-id="${product.id}" data-field="color">${product.color}</td>
            <td class="editable" data-product-id="${product.id}" data-field="manufacturerPrice">${product.manufacturerPrice}</td>
            <td class="editable" data-product-id="${product.id}" data-field="manufacturer">${product.manufacturer}</td>
            <!-- Add more cells as needed -->
        `;
        table.appendChild(row);
    });
    // If edit mode was enabled before filtering, re-enable it
    if (editModeEnabled) {
        toggleEditMode();
    }

}



// Wait for the DOM content to be loaded
window.addEventListener('DOMContentLoaded', function () {
    fetch('http://localhost:8080/products')
        .then(response => response.json())
        .then(products => {
            updateProductTable(products);
        })
        .catch(error => console.error('Error fetching products:', error));
});