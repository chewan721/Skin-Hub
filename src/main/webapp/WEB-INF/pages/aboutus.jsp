<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>About Us - Skin Hub</title>
    <!-- Favicon -->
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/system/favicon.ico" type="image/x-icon">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/aboutus.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

</head>
<body>
	<%@ include file="header.jsp" %>
   
    <main class="about-container">
        <section class="hero-banner">
            <div class="hero-content">
                <h1>About Skin Hub</h1>
                <p class="tagline">A trusted site for skin care products</p>
            </div>
        </section>

        <section class="mission-section">
            <div class="section-content">
                <h2><i class="fas fa-bullseye"></i> Our Mission</h2>
                <p>Providing original and branded items in city.</p>
                
                <div class="stats-grid">
                    <div class="stat-card">
                        <i class="fas fa-clock"></i>
                        <h3>Fast delivery</h3>
                        <p>Delivery within hours.</p>
                    </div>
                    <div class="stat-card">
						<i class="fas fa-leaf"></i>
                        <h3>Trusted</h3>
                        <p>Trusted by many customers</p>
                    </div>
                    
                </div>
            </div>
        </section>

        <section class="features-highlight">
            <h2><i class="fas fa-star"></i> Key Features</h2>
            <div class="features-grid">
                <div class="feature-card">
                    <i class="fas fa-tachometer-alt"></i>
                    <h3>Real-Time Experience</h3>
                    <p>Explore variety of products in our store.</p>
                </div>
                <div class="feature-card">
                    <i class="fas fa-search"></i>
                    <h3>Instant Search</h3>
                    <p>Find any product by name, brand, or category in seconds</p>
                </div>
                <div class="feature-card">
                    <i class="fas fa-user-tag"></i>
                    <h3>Role-Based Access</h3>
                    <p>Custom permissions for admins and customers</p>
                </div>
                <div class="feature-card">
                    <i class="fas fa-history"></i>
                    <h3>Complete History</h3>
                    <p>Detailed logs of all search </p>
                </div>
                <div class="feature-card">
                    <i class="fas fa-lock"></i>
                    <h3>Security</h3>
                    <p>Secure data and information system</p>
                </div>
                <div class="feature-card">
                    <i class="fas fa-mobile-alt"></i>
                    <h3>Mobile Ready</h3>
                    <p>Full functionality on any device, anywhere</p>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
    
    <!-- JavaScript -->
    <script src="${pageContext.request.contextPath}/js/animations.js"></script>
</body>
</html>