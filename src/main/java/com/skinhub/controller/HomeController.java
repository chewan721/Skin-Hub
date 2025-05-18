package com.skinhub.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.skinhub.model.CategoryModel;
import com.skinhub.model.ProductModel;
import com.skinhub.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = {"/home", "/"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        String category = request.getParameter("category");
        String sort = request.getParameter("sort");

        try {
            List<ProductModel> products = productService.searchAndSortProducts(search, category, sort);
            List<CategoryModel> categories = productService.getAllCategories();
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("products", null);
            request.setAttribute("categories", null);
        }
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}