<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
        
        <div class="register-container">
            <a href="${pageContext.request.contextPath}/update" class="register-link">Reset Password</a>
            <a href="${pageContext.request.contextPath}/register" class="register-link">Create a new account</a>
        </div>
    </form>
</div>
</body>
</html>