
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>

<!-- Set contextPath variable -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

<div id="header">
	<header class="header">
		<h2>Skin Hub</h2>
		<ul class="main-nav">
			<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
			<li><a href="${pageContext.request.contextPath}/aboutus">About</a></li>
			<li><a href="${pageContext.request.contextPath}/portfolio">Portfolio</a></li>
			<li><a href="${pageContext.request.contextPath}/contactus">Contact</a></li>
			<c:if test="${empty currentUser}">
				<li><a href="${pageContext.request.contextPath}/register">Register</a></li>
			</c:if>
			<li><c:choose>
					<c:when test="${not empty currentUser}">
						<form action="${contextPath}/logout" method="post">
							<input type="submit" class="nav-button" value="Logout" />
						</form>
					</c:when>
					<c:otherwise>
					
						<a href="${pageContext.request.contextPath}/login">Login</a>
					</c:otherwise>
				</c:choose></li>
		</ul>
		
		<!-- Search Bar -->
        <form class="search-bar" action="${contextPath}/search" method="get">
            <input type="text" name="query" placeholder="Search for products..." class="search-input" aria-label="Search">
            <button type="submit" class="search-button">Search</button>
        </form>
	</header>
</div>
