<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Skincare Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
    <%@ include file="header.jsp" %>  
    
    <main class="container">
        <!-- Hero Section -->
		<section class="hero-section">
		    <div class="hero-container">
		        <!-- Hero Text -->
		        <div class="hero-text">
		            <h1 class="hero-title">
		                <c:choose>
		                    <c:when test="${not empty sessionScope.user}">
		                        Welcome back, ${sessionScope.user.firstName}!
		                    </c:when>
		                    <c:otherwise>
		                        Welcome to Skincare Hub
		                    </c:otherwise>
		                </c:choose>
		            </h1>
		            <p class="hero-tagline">Discover your perfect skincare routine with our curated collection</p>
		        </div>
		
		        <!-- Hero Image -->
		        <div class="hero-image">
		            <img src="${pageContext.request.contextPath}/resources/images/system/sample .png" alt="Skincare products" loading="lazy">
		        </div>
		    </div>
		</section>


		<!-- Category Navigation -->
		<section class="category-section">
		    <h2 class="section-title">Shop by Category</h2>
		    
		    <div class="category-tabs">
		        <a href="?category=all" class="category-tab ${empty param.category or param.category eq 'all' ? 'active' : ''}">All Products</a>
		        <a href="?category=cleanser" class="category-tab ${param.category eq 'cleanser' ? 'active' : ''}">Cleansers</a>
		        <a href="?category=toner" class="category-tab ${param.category eq 'toner' ? 'active' : ''}">Toners</a>
		        <a href="?category=moisturizer" class="category-tab ${param.category eq 'moisturizer' ? 'active' : ''}">Moisturizers</a>
		        <a href="?category=serum" class="category-tab ${param.category eq 'serum' ? 'active' : ''}">Serums</a>
		        <a href="?category=sunscreen" class="category-tab ${param.category eq 'sunscreen' ? 'active' : ''}">Sunscreens</a>
		    </div>
		
		    <div class="product-grid">
		        <c:choose>
		            <%-- Show all products when no category or 'all' is selected --%>
		            <c:when test="${empty param.category or param.category eq 'all'}">
		                <c:forEach var="product" items="${allProducts}">
		                    <div class="product-card">
		                        <div class="product-image">
		                            <img src="${pageContext.request.contextPath}/resources/images/products/${product.imageName}" alt="${product.name}">
		                            <c:if test="${product.isNew}">
		                                <span class="product-badge">NEW</span>
		                            </c:if>
		                        </div>
		                        <div class="product-info">
		                            <h3 class="product-name">${product.name}</h3>
		                            <p class="product-brand">${product.brand}</p>
		                            <p class="product-price">$${product.price}</p>
		                            <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="view-details">View Details</a>
		                        </div>
		                    </div>
		                </c:forEach>
		            </c:when>
		            
		            <%-- Show filtered products when a category is selected --%>
		            <c:otherwise>
		                <c:set var="filteredProducts" value="${requestScope[param.category.concat('Products')]}" />
		                <c:choose>
		                    <c:when test="${not empty filteredProducts}">
		                        <c:forEach var="product" items="${filteredProducts}">
		                            <div class="product-card">
		                                <div class="product-image">
		                                    <img src="${pageContext.request.contextPath}/resources/images/products/${product.imageName}" alt="${product.name}">
		                                    <c:if test="${product.isNew}">
		                                        <span class="product-badge">NEW</span>
		                                    </c:if>
		                                </div>
		                                <div class="product-info">
		                                    <h3 class="product-name">${product.name}</h3>
		                                    <p class="product-brand">${product.brand}</p>
		                                    <p class="product-price">$${product.price}</p>
		                                    <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="view-details">View Details</a>
		                                </div>
		                            </div>
		                        </c:forEach>
		                    </c:when>
		                    <c:otherwise>
		                        <div class="no-products">
		                            <p>No products found in this category.</p>
		                        </div>
		                    </c:otherwise>
		                </c:choose>
		            </c:otherwise>
		        </c:choose>
		    </div>
		</section>

		        <!-- Featured Products Section -->
		<section class="products-section">
		    <h2 class="section-title">Featured Products</h2>
		    <div class="product-grid">
		        <!-- Product 1 -->
		        <div class="product-card">
		            <div class="product-image">
					<img src="${pageContext.request.contextPath}/resources/images/system/Anua Heartleaf Pore Control Cleansing Oil.png" alt="Anua Heartleaf Pore Control Cleansing Oil">		                <span class="product-badge">BESTSELLER</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">Anua Heartleaf Pore Control Cleansing Oil</h3>
		                <p class="product-brand">Anua</p>
		                <p class="product-price">$24.00</p>
		                
		                <a href="${pageContext.request.contextPath}/product?id=1" class="view-details">View Details</a>
		            </div>
		        </div>
		
		        <!-- Product 2 -->
		        <div class="product-card">
		            <div class="product-image">
		                <img src="${pageContext.request.contextPath}/resources/images/system/Laneige Water Bank Hydro Cream Moisturizer.png" alt="Laneige Water Bank Hydro Cream">
		                <span class="product-badge">NEW</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">Laneige Water Bank Hydro Cream</h3>
		                <p class="product-brand">Laneige</p>
		                <p class="product-price">$38.00</p>
		               
		                <a href="${pageContext.request.contextPath}/product?id=3" class="view-details">View Details</a>
		            </div>
		        </div>
		
		        <!-- Product 3 -->
		        <div class="product-card">
		            <div class="product-image">
						<img src="${pageContext.request.contextPath}/resources/images/system/COSRX Propolis Light Ampoule Serum.png" alt="COSRX Propolis Light Ampoule">		            </div>
		            <div class="product-info">
		                <h3 class="product-name">COSRX Propolis Light Ampoule</h3>
		                <p class="product-brand">COSRX</p>
		                <p class="product-price">$28.00</p>
		               
		                <a href="${pageContext.request.contextPath}/product?id=6" class="view-details">View Details</a>
		            </div>
		        </div>
		
		        <!-- Product 4 -->
		        <div class="product-card">
		            <div class="product-image">
						<img src="${pageContext.request.contextPath}/resources/images/system/Anua Birch Moisture Sunscreen SPF50 Sunscreen.png" alt="Anua Birch Moisture Sunscreen SPF50">		                <span class="product-badge">BESTSELLER</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">Anua Birch Moisture Sunscreen SPF50</h3>
		                <p class="product-brand">Anua</p>
		                <p class="product-price">$22.00</p>
		               
		                <a href="${pageContext.request.contextPath}/product?id=5" class="view-details">View Details</a>
		            </div>
		        </div>
		        <!-- Product 5 -->
		        <div class="product-card">
		            <div class="product-image">
						<img src="${pageContext.request.contextPath}/resources/images/system/COSRX Low pH Good Morning Gel Cleanser .png" alt="COSRX Low pH Good Morning Gel Cleanser">		                
						<span class="product-badge">BESTSELLER</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">COSRX Low pH Good Morning Gel Cleanser</h3>
		                <p class="product-brand">COSRX</p>
		                <p class="product-price">$16.00</p>
		               
		                <a href="${pageContext.request.contextPath}/product?id=2" class="view-details">View Details</a>
		            </div>
		        </div>
		    </div>
		</section>
		
		<!-- New Arrivals Section -->
		<section class="products-section">
		    <h2 class="section-title">New Arrivals</h2>
		    <div class="product-grid">
		        <!-- Product 1 -->
		        <div class="product-card">
		            <div class="product-image">
						<img src="${pageContext.request.contextPath}/resources/images/system/COSRX Low pH Good Morning Gel Cleanser .png" alt="COSRX Low pH Good Morning Gel Cleanser">
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">COSRX Low pH Good Morning Gel Cleanser</h3>
		                <p class="product-brand">COSRX</p>
		                <p class="product-price">$16.00</p>
		                <a href="${pageContext.request.contextPath}/product?id=2" class="view-details">View Details</a>
		            </div>
		        </div>
		
		        <!-- Product 2 -->
		        <div class="product-card">
		            <div class="product-image">
						<img src="${pageContext.request.contextPath}/resources/images/system/Centella Poremizing Deep Cleansing Foam.png" alt="Centella pH 6.5 Whip Cleanser">
		                <span class="product-badge">NEW</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">Centella pH 6.5 Whip Cleanser</h3>
		                <p class="product-brand">Centella</p>
		                <p class="product-price">$18.00</p>
		                <a href="${pageContext.request.contextPath}/product?id=4" class="view-details">View Details</a>
		            </div>
		        </div>
		
		        <!-- Product 3 -->
		        <div class="product-card">
		            <div class="product-image">
						<img src="${pageContext.request.contextPath}/resources/images/system/Laneige Water Sleeping Mask.png" alt="Laneige Water Sleeping Mask">
		                <span class="product-badge">NEW</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">Laneige Water Sleeping Mask</h3>
		                <p class="product-brand">Laneige</p>
		                <p class="product-price">$32.00</p>
		                <a href="${pageContext.request.contextPath}/product?id=7" class="view-details">View Details</a>
		            </div>
		        </div>
		
		        <!-- Product 4 -->
		        <div class="product-card">
		            <div class="product-image">
						<img src="${pageContext.request.contextPath}/resources/images/system/Centella Calming Toner.png" alt="Centella Calming Toner">		                <span class="product-badge">NEW</span>
		                <span class="product-badge">NEW</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">Centella Calming Toner</h3>
		                <p></p>
		                <p class="product-brand">Centella</p>
		                <p></p>
		                <p class="product-price">$20.00</p>
		                <a href="${pageContext.request.contextPath}/product?id=8" class="view-details">View Details</a>
		            </div>
		        </div>
		        
		        <!-- Product 5 -->
		        <div class="product-card">
		            <div class="product-image">
		                <img src="${pageContext.request.contextPath}/resources/images/system/Laneige Water Bank Hydro Cream Moisturizer.png" alt="Laneige Water Bank Hydro Cream">
		                <span class="product-badge">NEW</span>
		            </div>
		            <div class="product-info">
		                <h3 class="product-name">Laneige Water Bank Hydro Cream</h3>
		                <p class="product-brand">Laneige</p>
		                <p class="product-price">$38.00</p>
		               
		                <a href="${pageContext.request.contextPath}/product?id=3" class="view-details">View Details</a>
		            </div>
		        </div>
		    </div>
		</section>
    </main>
    
    <%@ include file="footer.jsp" %>
</body>
</html>