package com.skinhub.controller;

import java.io.IOException;

import com.skinhub.model.UserModel;
import com.skinhub.service.LoginService;
// Removed CookieUtil import as it's no longer needed here for user_name
import com.skinhub.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * LoginController handles login requests for the SkinHub e-commerce application.
 * It interacts with the LoginService to authenticate customers and admins.
 * Stores user_name and role securely in the session upon successful login.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LoginService loginService;

    /**
     * Constructor initializes the LoginService.
     */
    public LoginController() {
        this.loginService = new LoginService();
    }

    /**
     * Handles GET requests to display the login page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in based on session, redirect if necessary
        if (SessionUtil.getAttribute(request, "user_name") != null) {
             String role = (String) SessionUtil.getAttribute(request, "role");
             if ("ADMIN".equalsIgnoreCase(role)) {
                 response.sendRedirect(request.getContextPath() + "/admin/dashboard"); // Adjusted path
                 return;
             } else if ("CUSTOMER".equalsIgnoreCase(role)) {
                 response.sendRedirect(request.getContextPath() + "/customer/home"); // Adjusted path
                 return;
             }
        }
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user login. Validates credentials and stores
     * user_name and role in the session.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("user_name"); // Make sure form name matches
        String password = req.getParameter("password");

        // Basic validation (consider adding more robust validation)
        if (userName == null || userName.trim().isEmpty() || password == null || password.isEmpty()) {
             handleLoginFailure(req, resp, "Username and password are required.");
             return;
        }

        UserModel userToValidate = new UserModel();
        userToValidate.setUser_name(userName.trim());
        userToValidate.setPassword(password); // Keep plain password for service validation

        UserModel validatedUser = loginService.loginUser(userToValidate);

        if (validatedUser != null && validatedUser.getRole() != null && validatedUser.getRole().getRole_type() != null) {
            // --- Store BOTH user_name and role in the SESSION ---
            SessionUtil.setAttribute(req, "user_name", validatedUser.getUser_name());
            SessionUtil.setAttribute(req, "role", validatedUser.getRole().getRole_type());

            // --- Removed CookieUtil call for user_name ---

            // Redirect based on role stored in session
            if ("CUSTOMER".equalsIgnoreCase(validatedUser.getRole().getRole_type())) {
                // Redirect to a customer-specific landing page
                resp.sendRedirect(req.getContextPath() + "/customer/home"); // Example path
            } else if ("ADMIN".equalsIgnoreCase(validatedUser.getRole().getRole_type())) {
                 // Redirect to an admin-specific landing page
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard"); // Example path
            } else {
                // Should not happen if validation is correct, but handle defensively
                handleLoginFailure(req, resp, "Login successful but role is invalid.");
            }
        } else {
             // Determine if the failure was due to connection error hinted by service (optional)
             // Or just provide a generic message
            handleLoginFailure(req, resp, "Invalid username or password.");
        }
    }

    /**
     * Handles login failures by setting error attributes and forwarding to the login page.
     */
    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws ServletException, IOException {
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    // Optional: Override destroy method to close LoginService connection
    @Override
    public void destroy() {
        if (loginService != null) {
            loginService.close(); // Add a close method to LoginService if needed
        }
        super.destroy();
    }
}