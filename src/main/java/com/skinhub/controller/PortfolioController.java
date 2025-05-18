package com.skinhub.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.skinhub.model.RoleModel;
import com.skinhub.model.UserModel;
import com.skinhub.service.PortfolioService;
import com.skinhub.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = {"/portfolio"})
public class PortfolioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PortfolioService portfolioService = new PortfolioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) SessionUtil.getAttribute(req, "user_id");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        UserModel user = portfolioService.getUserById(userId);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) SessionUtil.getAttribute(req, "user_id");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");
        String gender = req.getParameter("gender");
        LocalDate dob = LocalDate.parse(req.getParameter("dob"));
        String imageUrl = req.getParameter("imageUrl");
        String roleType = req.getParameter("role_type"); // Optional, if you allow role change

        RoleModel role = new RoleModel(roleType != null ? roleType : "CUSTOMER");
        UserModel user = new UserModel(userId, firstName, lastName, null, email, contact, gender, dob, null, role, imageUrl);

        Boolean result = portfolioService.updateUserProfile(user);
        if (result != null && result) {
            req.setAttribute("success", "Profile updated successfully!");
        } else {
            req.setAttribute("error", "Failed to update profile. Please try again.");
        }
        req.setAttribute("user", portfolioService.getUserById(userId));
        req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
    }
}