<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SkinHub - Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    
    <style>
        .profile-view label { font-weight: bold; }
        .profile-view .profile-row { margin-bottom: 10px; }
        .profile-edit { display: none; }
        .profile-actions { margin-top: 20px; }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <h2>Your Profile</h2>
        <c:if test="${not empty success}">
            <p class="success-message">${success}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <!-- View Mode -->
        <div id="profileView" class="profile-view">
            <div class="profile-row"><label>First Name:</label> ${user.first_name}</div>
            <div class="profile-row"><label>Last Name:</label> ${user.last_name}</div>
            <div class="profile-row"><label>Email:</label> ${user.email}</div>
            <div class="profile-row"><label>Contact:</label> ${user.contact}</div>
            <div class="profile-row"><label>Gender:</label> ${user.gender}</div>
            <div class="profile-row"><label>Date of Birth:</label> ${user.dob}</div>
            <div class="profile-row"><label>Profile Image URL:</label> ${user.imageUrl}</div>
            <div class="profile-row">
                <label>Profile Image:</label>
                <c:if test="${not empty user.imageUrl}">
                    <img src="${pageContext.request.contextPath}/resources/images/user/${user.imageUrl}" alt="Profile Image" style="max-width:80px;">
                </c:if>
            </div>
            <div class="profile-actions">
                <button type="button" onclick="switchToEdit()">Edit Profile</button>
            </div>
        </div>

        <!-- Edit Mode -->
        <div id="profileEdit" class="profile-edit">
            <form id="editProfileForm" action="${pageContext.request.contextPath}/portfolio" method="post">
                <div>
                    <label>First Name:</label>
                    <input type="text" name="first_name" value="${user.first_name}" required />
                </div>
                <div>
                    <label>Last Name:</label>
                    <input type="text" name="last_name" value="${user.last_name}" required />
                </div>
                <div>
                    <label>Email:</label>
                    <input type="email" name="email" value="${user.email}" required />
                </div>
                <div>
                    <label>Contact:</label>
                    <input type="text" name="contact" value="${user.contact}" required />
                </div>
                <div>
                    <label>Gender:</label>
                    <input type="text" name="gender" value="${user.gender}" required />
                </div>
                <div>
                    <label>Date of Birth:</label>
                    <input type="date" name="dob" value="${user.dob}" required />
                </div>
                <div>
                    <label>Profile Image URL:</label>
                    <input type="text" name="imageUrl" value="${user.imageUrl}" />
                </div>
                <div class="profile-actions">
                    <button type="submit">Save</button>
                    <button type="button" onclick="switchToView()">Cancel</button>
                </div>
            </form>
        </div>
    </div>
    <script>
        function switchToEdit() {
            document.getElementById('profileView').style.display = 'none';
            document.getElementById('profileEdit').style.display = 'block';
        }
        function switchToView() {
            document.getElementById('profileEdit').style.display = 'none';
            document.getElementById('profileView').style.display = 'block';
        }
    </script>
</body>
</html>