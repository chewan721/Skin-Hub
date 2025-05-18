<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SkinHub - Contact Us</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    
    <script>
        function handleSubmit(event) {
            event.preventDefault();
            alert("Thank you for your feedback! We'll get back to you soon.");
            event.target.reset();
        }
    </script>
</head>
<body>
   	<%@ include file="header.jsp" %>


    <div class="container">
        <div class="contact-section">
            <h2>Contact Us</h2>
            <div class="contact-grid">
                <div class="contact-details">
                    <h3>Get in Touch</h3>
                    <p><strong>Address:</strong> 123 Glow Avenue, Skincare City, SC 12345</p>
                    <p><strong>Phone:</strong> (555) 123-4567</p>
                    <p><strong>Email:</strong> support@skinhub.com</p>
                    <p><strong>Hours:</strong> Mon-Fri: 9 AM - 6 PM, Sat: 10 AM - 4 PM</p>
                    <div class="social-links">
                        <h4>Follow Us</h4>
                        <a href="https://twitter.com/skinhub" target="_blank">Twitter</a>
                        <a href="https://instagram.com/skinhub" target="_blank">Instagram</a>
                        <a href="https://facebook.com/skinhub" target="_blank">Facebook</a>
                    </div>
                </div>
                <div class="feedback-form">
                    <h3>Send Us Your Feedback</h3>
                    <form action="contact?action=submit" method="post" onsubmit="handleSubmit(event)">
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="name">Name:</label>
                                <input type="text" id="name" name="name" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" id="email" name="email" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="subject">Subject:</label>
                            <input type="text" id="subject" name="subject" required>
                        </div>
                        <div class="form-group">
                            <label for="message">Message:</label>
                            <textarea id="message" name="message" rows="4" required></textarea>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-submit">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>