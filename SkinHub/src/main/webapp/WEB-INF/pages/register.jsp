<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix ="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Form</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/register.css" />
</head>
<body>
	<div class="container">
		<h1>Registration Form</h1>
		<%
		String errorMessage = (String) request.getAttribute("error");
		String successMessage = (String) request.getAttribute("success");

		if (errorMessage != null && !errorMessage.isEmpty()) {
			out.println("<p class=\"error-message\">" + errorMessage + "</p>");
		}

		if (successMessage != null && !successMessage.isEmpty()) {
		%>
		<p class="success-message"><%=successMessage%></p>
		<%
		}
		%>

		<form action="${pageContext.request.contextPath}/register"
			method="post" enctype="multipart/form-data">
			<div class="row">
				<div class="col">
					<label for="first_Name">First Name:</label> <input type="text"
						id="first_Name" name="first_Name" required>
				</div>
				<div class="col">
					<label for="last_Name">Last Name:</label> <input type="text"
						id="last_Name" name="last_Name" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="user_name">Username:</label> <input type="text"
						id="user_name" name="user_name" required>
				</div>
				<div class="col">
					<label for="dob">Date of Birth:</label> <input type="date"
						id="birthday" name="dob" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="gender">Gender:</label> <select id="gender"
						name="gender" required>
						<option value="male">Male</option>
						<option value="female">Female</option>
					</select>
				</div>
				<div class="col">
					<label for="email">Email:</label> <input type="email" id="email"
						name="email" required>
				</div>
			</div>
			
			<div class="row">
				<div class="col">
					<label for="password">Password:</label> <input type="password"
						id="password" name="password" required>
				</div>
				<div class="col">
					<label for="retypePassword">Retype Password:</label> <input
						type="password" id="retypePassword" name="retypePassword" required>
				</div>
			</div>
			<div class="row">
				<div >
					<label for="contact">Phone Number:</label> <input type="tel"
						id="phoneNumber" name="phoneNumber" required>
				</div>
				
			</div>
			
			 <div class ="button">
                <button type="submit">Register</button>
            </div>	
		</form>
		<a href="${pageContext.request.contextPath}/login" class="login-button">Login If You Have Already An Account </a>
		
	</div>
</body>
</html>