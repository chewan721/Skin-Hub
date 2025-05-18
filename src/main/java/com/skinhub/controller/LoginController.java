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
@WebServlet(asyncSupported = true, urlPatterns = {"/login"})
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
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user login. Validates credentials and stores
     * user_name and role in the session.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("user_name");
        String password = req.getParameter("password");

        // Validate credentials
        if (userName == null || userName.trim().isEmpty() || password == null || password.isEmpty()) {
            handleLoginFailure(req, resp, "Username and password are required.");
            return;
        }

        UserModel userToValidate = new UserModel(userName.trim(), password);
        UserModel validatedUser = loginService.loginUser(userToValidate);

        if (validatedUser != null && validatedUser.getRole() != null && validatedUser.getRole().getRole_type() != null) {
            // Store user details in session
            SessionUtil.setAttribute(req, "user_id", validatedUser.getUser_id());
            SessionUtil.setAttribute(req, "user_name", validatedUser.getUser_name());
            SessionUtil.setAttribute(req, "role", validatedUser.getRole().getRole_type());
            
         
            if ("ADMIN".equalsIgnoreCase(validatedUser.getRole().getRole_type())) {
                resp.sendRedirect(req.getContextPath() + "/adminDashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
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