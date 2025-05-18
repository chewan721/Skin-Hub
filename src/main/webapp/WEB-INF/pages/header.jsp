<!-- filepath: src/main/webapp/WEB-INF/pages/header.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String currentUser = (String) session.getAttribute("user_name");
    String currentRole = (String) session.getAttribute("role"); // "admin" or "customer"
    pageContext.setAttribute("currentUser", currentUser);
    pageContext.setAttribute("currentRole", currentRole);
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="header">
    <h1>SkinHub</h1>
    <div class="nav">
        <c:choose>
            
            <c:when test="${not empty currentUser && currentRole eq 'admin'}">
                <a href="${contextPath}/adminDashboard">Home</a>
                <a href="${contextPath}/portfolio">Profile</a>
                <form action="${contextPath}/logout" method="post" style="display:inline;">
                    <button type="submit" class="nav-button" style="background:none;border:none;color:inherit;cursor:pointer;">Logout</button>
                </form>
            </c:when>
           
            <c:when test="${not empty currentUser}">
                <a href="${contextPath}/home">Home</a>
                <a href="${contextPath}/aboutus">About Us</a>
                <a href="${contextPath}/contactus">Contact Us</a>
                <a href="${contextPath}/portfolio">Profile</a>
                <form action="${contextPath}/logout" method="post" style="display:inline;">
                    <button type="submit" class="nav-button" style="background:none;border:none;color:inherit;cursor:pointer;">Logout</button>
                </form>
            </c:when>

            <c:otherwise>
                <a href="${contextPath}/home">Home</a>
                <a href="${contextPath}/aboutus">About Us</a>
                <a href="${contextPath}/contactus">Contact Us</a>
                <a href="${contextPath}/login">Log In</a>
                <a href="${contextPath}/register">Register</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>