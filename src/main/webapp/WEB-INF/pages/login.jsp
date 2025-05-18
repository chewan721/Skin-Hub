<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login to your account</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>
	<div class="login-box">
		<h2>Login</h2>
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="row">
				<div class="col">
					<label for="user_name">Username:</label>
				</div>
				<div class="col input-field">
					<input type="text" id="user_name" name="user_name" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="password">Password:</label>
				</div>
				<div class="col input-field">
					<input type="password" id="password" name="password" required>
				</div>
			</div>
			<button type="submit">Login</button>
		</form>
		<a href="${pageContext.request.contextPath}/register">Register if
			you don't have an Account!</a>

	</div>
</body>
</html>