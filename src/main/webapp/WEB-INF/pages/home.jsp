<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Skin Care Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <main class="container">
        <!-- Search, Filter, Sort Bar -->
        <form class="search-filter-bar" action="${pageContext.request.contextPath}/home" method="get">
            <input type="text" name="search" placeholder="Search here" value="${param.search}" />
            <select name="category">
                <option value="all">All Categories</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.category_name}" ${param.category == cat.category_name ? 'selected' : ''}>${cat.category_name}</option>
                </c:forEach>
            </select>
            <select name="sort">
                <option value="">Sort By</option>
                <option value="price_asc" ${param.sort == 'price_asc' ? 'selected' : ''}>Price: Low to High</option>
                <option value="price_desc" ${param.sort == 'price_desc' ? 'selected' : ''}>Price: High to Low</option>
                <option value="name_asc" ${param.sort == 'name_asc' ? 'selected' : ''}>Name: A-Z</option>
                <option value="name_desc" ${param.sort == 'name_desc' ? 'selected' : ''}>Name: Z-A</option>
            </select>
            <button type="submit">Search</button>
        </form>

        <!-- Product Grid -->
        <section class="products-section">
            <h2 class="section-title">Products</h2>
            <div class="product-grid">
                <c:choose>
                    <c:when test="${not empty products}">
                        <c:forEach var="product" items="${products}">
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="${pageContext.request.contextPath}/resources/images/products/${product.image}" alt="${product.product_name}">
                                </div>
                                <div class="product-info">
                                    <h3 class="product-name">${product.product_name}</h3>
                                    <p class="product-brand">${product.brand.brand_name}</p>
                                    <p class="product-category">${product.category.category_name}</p>
                                    <p class="product-price">$${product.price}</p>
                                    <a href="${pageContext.request.contextPath}/product?id=${product.product_id}" class="view-details">View Details</a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-products">
                            <p>No products found.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>