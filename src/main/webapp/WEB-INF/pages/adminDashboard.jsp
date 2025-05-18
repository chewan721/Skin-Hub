<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | SkinHub</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script>
        function showPopup() {
            const popup = document.getElementById('addProductPopup');
            popup.style.display = 'block';
            popup.classList.add('fade-in');
        }

        function hidePopup() {
            const popup = document.getElementById('addProductPopup');
            popup.classList.remove('fade-in');
            popup.classList.add('fade-out');
            setTimeout(() => {
                popup.style.display = 'none';
                popup.classList.remove('fade-out');
            }, 300);
        }

        function handleSubmit(event) {
            event.preventDefault();
            alert("Product added successfully!");
            event.target.reset();
            hidePopup();
        }
    </script>
</head>
<body>
<div class="header">
    <h1>SkinHub</h1>
    <div class="nav">
        <a href="${pageContext.request.contextPath}/portfolio">Profile</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>
<main class="admin-dashboard-container">
    <h1 class="admin-title">Admin Dashboard</h1>
    <!-- Analytics Cards -->
    <div class="analytics-cards">
        <div class="analytics-card users">
            <i class="fa fa-users"></i>
            <div class="analytics-value">${totalUsers}</div>
            <div class="analytics-label">Total Users</div>
        </div>
        <div class="analytics-card products">
            <i class="fa fa-box"></i>
            <div class="analytics-value">${totalProducts}</div>
            <div class="analytics-label">Total Products</div>
        </div>
        <div class="analytics-card categories">
            <i class="fa fa-list"></i>
            <div class="analytics-value">${totalCategories}</div>
            <div class="analytics-label">Categories</div>
        </div>
        <div class="analytics-card brands">
            <i class="fa fa-tags"></i>
            <div class="analytics-value">${totalBrands}</div>
            <div class="analytics-label">Brands</div>
        </div>
    </div>

    <!-- Product Table -->
    <section class="dashboard-section">
        <div class="section-header">
            <h2>Products</h2>
            <button class="add-btn" onclick="showPopup()">
                <i class="fa fa-plus"></i> Add Product
            </button>
        </div>
        <div class="table-responsive">
            <table class="dashboard-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Brand</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.product_id}</td>
                        <td>
                            <img src="${pageContext.request.contextPath}/resources/images/products/${product.image}" alt="${product.product_name}" class="product-thumb">
                        </td>
                        <td>${product.product_name}</td>
                        <td>${product.brand.brand_name}</td>
                        <td>${product.category.category_name}</td>
                        <td>$${product.price}</td>
                        <td>${product.quantity}</td>
                        
                        <td>
                            <a href="${pageContext.request.contextPath}/editproduct?id=${product.product_id}" class="icon-btn" title="Edit">
                                <i class="fa fa-edit"></i>
                            </a>
                            <form action="${pageContext.request.contextPath}/adminDashboard" method="post" style="display:inline;">
							    <input type="hidden" name="action" value="deleteProduct">
							    <input type="hidden" name="product_id" value="${product.product_id}">
							    <button type="submit" class="icon-btn" title="Delete" onclick="return confirm('Delete this product?');">
							        <i class="fa fa-trash"></i>
							    </button>
							</form>
                            
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <!-- Add Product Popup -->
	<div id="addProductPopup" class="popup">
	    <div class="popup-content">
	        <h2>Add New Product</h2>
	        <form action="${pageContext.request.contextPath}/adminDashboard" method="post" enctype="multipart/form-data">
	            <input type="hidden" name="action" value="addProduct">
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="product_name">Product Name:</label>
	                    <input type="text" id="product_name" name="product_name" required>
	                </div>
	                <div class="form-group">
	                    <label for="image">Image:</label>
	                    <input type="file" id="image" name="image" accept="image/*" required>
	                </div>
	            </div>
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="price">Price:</label>
	                    <input type="number" id="price" name="price" step="0.01" min="0" required>
	                </div>
	                <div class="form-group">
	                    <label for="quantity">Quantity:</label>
	                    <input type="number" id="quantity" name="quantity" min="0" required>
	                </div>
	            </div>
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="manufacture_date">Manufacture Date</label>
	                    <input type="date" id="manufacture_date" name="manufacture_date" required>
	                </div>
	                <div class="form-group">
	                    <label for="expiry_date">Expiry Date</label>
	                    <input type="date" id="expiry_date" name="expiry_date" required>
	                </div>
	            </div>
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="brand_id">Brand:</label>
	                    <select id="brand_id" name="brand_id" required>
	                        <option value="">Select Brand</option>
	                        <c:forEach var="brand" items="${brands}">
	                            <option value="${brand.brand_id}">${brand.brand_name}</option>
	                        </c:forEach>
	                    </select>
	                </div>
	                <div class="form-group">
	                    <label for="category_id">Category:</label>
	                    <select id="category_id" name="category_id" required>
	                        <option value="">Select Category</option>
	                        <c:forEach var="category" items="${categories}">
	                            <option value="${category.category_id}">${category.category_name}</option>
	                        </c:forEach>
	                    </select>
	                </div>
	            </div>
	            <div class="form-group">
	                <label for="ingredients">Ingredients:</label>
	                <textarea id="ingredients" name="ingredients" rows="3"></textarea>
	            </div>
	            <div class="form-actions">
	                <button type="submit" class="btn btn-submit">Add Product</button>
	                <button type="button" class="btn btn-cancel" onclick="hidePopup()">Cancel</button>
	            </div>
	        </form>
	    </div>
	</div>

	<!-- Edit Product Popup -->
	<div id="editProductPopup" class="popup" style="display:none;">
	    <div class="popup-content">
	        <h2>Edit Product</h2>
	        <form id="editProductForm" action="${pageContext.request.contextPath}/adminDashboard" method="post" enctype="multipart/form-data">
	            <input type="hidden" name="action" value="updateProduct">
	            <input type="hidden" id="edit_product_id" name="product_id">
	            <input type="hidden" id="edit_current_image" name="current_image">
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="edit_product_name">Product Name:</label>
	                    <input type="text" id="edit_product_name" name="product_name" required>
	                </div>
	                <div class="form-group">
	                    <label for="edit_image">Image:</label>
	                    <input type="file" id="edit_image" name="image" accept="image/*">
	                    <img id="edit_image_preview" src="" alt="Current Image" style="max-width:60px;display:block;margin-top:5px;">
	                </div>
	            </div>
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="edit_price">Price:</label>
	                    <input type="number" id="edit_price" name="price" step="0.01" min="0" required>
	                </div>
	                <div class="form-group">
	                    <label for="edit_quantity">Quantity:</label>
	                    <input type="number" id="edit_quantity" name="quantity" min="0" required>
	                </div>
	            </div>
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="edit_manufacture_date">Manufacture Date</label>
	                    <input type="date" id="edit_manufacture_date" name="manufacture_date" required>
	                </div>
	                <div class="form-group">
	                    <label for="edit_expiry_date">Expiry Date</label>
	                    <input type="date" id="edit_expiry_date" name="expiry_date" required>
	                </div>
	            </div>
	            <div class="form-grid">
	                <div class="form-group">
	                    <label for="edit_brand_id">Brand:</label>
	                    <select id="edit_brand_id" name="brand_id" required>
	                        <option value="">Select Brand</option>
	                        <c:forEach var="brand" items="${brands}">
	                            <option value="${brand.brand_id}">${brand.brand_name}</option>
	                        </c:forEach>
	                    </select>
	                </div>
	                <div class="form-group">
	                    <label for="edit_category_id">Category:</label>
	                    <select id="edit_category_id" name="category_id" required>
	                        <option value="">Select Category</option>
	                        <c:forEach var="category" items="${categories}">
	                            <option value="${category.category_id}">${category.category_name}</option>
	                        </c:forEach>
	                    </select>
	                </div>
	            </div>
	            <div class="form-group">
	                <label for="edit_ingredients">Ingredients:</label>
	                <textarea id="edit_ingredients" name="ingredients" rows="3"></textarea>
	            </div>
	            <div class="form-actions">
	                <button type="submit" class="btn btn-submit">Update Product</button>
	                <button type="button" class="btn btn-cancel" onclick="closeEditProductPopup()">Cancel</button>
	            </div>
	        </form>
	    </div>
	</div>


    <!-- User Table -->
    <section class="dashboard-section">
        <div class="section-header">
            <h2>Users</h2>
        </div>
        <div class="table-responsive">
            <table class="dashboard-table">
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Gender</th>
                        <th>Date of Birth</th>
                        
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.first_name}</td>
                        <td>${user.last_name}</td>
                        <td>${user.email}</td>
                        <td>${user.gender}</td>
                        <td>${user.dob}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
</main>
<%@ include file="footer.jsp" %>
</body>
</html>