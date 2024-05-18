// Function to fetch and display categories
function fetchCategories() {
    // Send AJAX request to fetch categories
    fetch('http://localhost:8080/categories')
        .then(response => response.json())
        .then(categories => {
            // Populate category tree/hierarchy
            const categoryTree = document.getElementById('category-tree');
            categoryTree.innerHTML = buildCategoryTree(categories);
        })
        .catch(error => console.error('Error fetching categories:', error));
}

// Function to build category tree/hierarchy
function buildCategoryTree(categories) {
    const buildTree = (categories, parentId = null) => {
        let tree = '';
        const filteredCategories = categories.filter(category => category.parentId === parentId);

        if (filteredCategories.length > 0) {
            tree += '<ul>';
            filteredCategories.forEach(category => {
                tree += `<li>${category.name}`;
                tree += buildTree(categories, category.id);
                tree += '</li>';
            });
            tree += '</ul>';
        }

        return tree;
    };

    return buildTree(categories);
}



// Function to delete products with the specified category name
function deleteCategory(categoryName) {
    if (categoryName) {
        // Send AJAX request to delete products with the specified category
        fetch('http://localhost:8080/deleteCategory', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({categoryName:(categoryName)}),
        })
            .then(response => response.text())
            .then(data => {
                // Handle success message or error
                console.log(data);
            })
            .catch(error => {
                console.error('Error deleting products:', error);
            });
    }
}

