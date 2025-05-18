<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
    <footer class="app-footer">
        <div class="footer-container">
            <!-- Left Section - About Text -->
            <div class="footer-section about">
                <h3>About SkinHub</h3>
                <p>Your trusted destination for premium skincare products. We carefully curate our collection to bring you only the best formulations for your skin type and concerns.</p>
                <p>Founded in 2023 with a mission to make quality skincare accessible to everyone.</p>
            </div>
            
            <!-- Middle Section - Quick Links -->
            <div class="footer-section links">
                <h3>Quick Links</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/products">Products</a></li>
                    <li><a href="${pageContext.request.contextPath}/about">About Us</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/faq">FAQ</a></li>
                    <li><a href="${pageContext.request.contextPath}/privacy">Privacy Policy</a></li>
                </ul>
            </div>
            
            <!-- Right Section - Contact Info -->
            <div class="footer-section contact">
                <h3>Contact Us</h3>
                <p><i class="fas fa-map-marker-alt"></i> 123 Beauty St, Skincare City</p>
                <p><i class="fas fa-phone"></i> (123) 456-7890</p>
                <p><i class="fas fa-envelope"></i> info@skinhub.com</p>
                
                <div class="social-icons">
                    <a href="#" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
                    <a href="#" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                    <a href="#" aria-label="Pinterest"><i class="fab fa-pinterest"></i></a>
                </div>
            </div>
        </div>
        
        <!-- Bottom Section - Copyright -->
        <div class="footer-bottom">
            <p>&copy; 2023 SkinHub. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>