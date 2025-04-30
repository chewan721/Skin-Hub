<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminDashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="dashboard-container">
        <!-- Analytics Section -->
        <section class="analytics-section">
            <div class="analytics-card">
                <h3>Total Brands</h3>
                <p>5</p>
            </div>
            <div class="analytics-card">
                <h3>Total Categories</h3>
                <p>5</p>
            </div>
            <div class="analytics-card">
                <h3>Total Products</h3>
                <p>8</p>
            </div>
            <div class="analytics-card">
                <h3>Total Users</h3>
                <p>4</p>
            </div>
        </section>

        <!-- Product Management Section -->
        <section class="product-management">
            <div class="product-header">
                <h2>Product List</h2>
                <button class="add-product-button">Add Product</button>
            </div>
            <table class="product-table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>Anua Heartleaf Pore Control Cleansing Oil</td>
                        <td>Cleanser</td>
                        <td>$20.00</td>
                        <td>
                            <button class="edit-button">Edit</button>
                            <button class="delete-button">Delete</button>
                        </td>
                    </tr>
                    <!-- Add more rows dynamically -->
                </tbody>
            </table>
        </section>

        <!-- User Table Section -->
        <section class="user-management">
            <h2>User List</h2>
            <table class="user-table">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Contact</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>Sabin Shrestha</td>
                        <td>sabin.shrestha@email.com</td>
                        <td>9841234567</td>
                    </tr>
                    <!-- Add more rows dynamically -->
                </tbody>
            </table>
        </section>
    </main>
        <%@ include file="footer.jsp" %>
    
</body>
</html>