package com.skinhub.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.skinhub.model.BrandModel;
import com.skinhub.model.CategoryModel;
import com.skinhub.model.ProductModel;
import com.skinhub.model.UserModel;
import com.skinhub.service.AdminDashboardService;
import com.skinhub.util.ValidationUtil;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(asyncSupported = true, urlPatterns = "/adminDashboard")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AdminDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDashboardService adminDashboardService = new AdminDashboardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int totalUsers = adminDashboardService.getTotalUsers();
            int totalProducts = adminDashboardService.getTotalProducts();
            int totalBrands = adminDashboardService.getTotalBrands();
            int totalCategories = adminDashboardService.getTotalCategories();

            List<ProductModel> products = adminDashboardService.getAllProducts();
            List<UserModel> users = adminDashboardService.getAllUsers();
            List<BrandModel> brands = adminDashboardService.getAllBrands();
            List<CategoryModel> categories = adminDashboardService.getAllCategories();

            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("totalBrands", totalBrands);
            request.setAttribute("totalCategories", totalCategories);
            request.setAttribute("products", products);
            request.setAttribute("users", users);
            request.setAttribute("brands", brands);
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/WEB-INF/pages/adminDashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching dashboard data.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
        	if ("addProduct".equals(action)) {
                ProductModel product = extractProductFromRequest(request, true);
                adminDashboardService.addProduct(product);
            } else if ("updateProduct".equals(action)) {
                try {
                    ProductModel product = extractProductFromRequest(request, false);
                    adminDashboardService.updateProduct(product);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating product.");
                    return;
                }
            } else if ("deleteProduct".equals(action)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                adminDashboardService.deleteProduct(productId);
            }
            response.sendRedirect(request.getContextPath() + "/adminDashboard");
        } catch (ServletException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing product operation.");
        }
    }

    
    
    private ProductModel extractProductFromRequest(HttpServletRequest request, boolean isAdd) throws ServletException, IOException {
        String productName = request.getParameter("product_name");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");
        String manufactureDate = request.getParameter("manufacture_date");
        String expiryDate = request.getParameter("expiry_date");
        String ingredients = request.getParameter("ingredients");
        String brandIdStr = request.getParameter("brand_id");
        String categoryIdStr = request.getParameter("category_id");

        // Image upload handling
        Part imagePart = request.getPart("image");
        String imageFileName = null;
        if (isAdd) {
            if (imagePart == null || imagePart.getSize() == 0) {
                throw new ServletException("Product image is required.");
            }
            if (!ValidationUtil.isValidImageExtension(imagePart)) {
                throw new ServletException("Invalid image format. Only JPG, JPEG, PNG, GIF allowed.");
            }
            imageFileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName();
            String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/products";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            imagePart.write(uploadPath + File.separator + imageFileName);
        }else {
            // For update: if a new image is uploaded, use it; otherwise, keep the old image
            if (imagePart != null && imagePart.getSize() > 0) {
                if (!ValidationUtil.isValidImageExtension(imagePart)) {
                    throw new ServletException("Invalid image format. Only JPG, JPEG, PNG, GIF allowed.");
                }
                imageFileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName();
                String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/products";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                imagePart.write(uploadPath + File.separator + imageFileName);
            } else {
                imageFileName = request.getParameter("current_image");
            }
            
        }
            

        // Validate required fields
        if (productName == null || productName.trim().isEmpty() ||
            priceStr == null || priceStr.trim().isEmpty() ||
            quantityStr == null || quantityStr.trim().isEmpty() ||
            manufactureDate == null || manufactureDate.trim().isEmpty() ||
            expiryDate == null || expiryDate.trim().isEmpty() ||
            brandIdStr == null || brandIdStr.trim().isEmpty() ||
            categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            throw new ServletException("All fields except ingredients are required.");
        }

        double price;
        int quantity;
        int brandId;
        int categoryId;
        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
            brandId = Integer.parseInt(brandIdStr);
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format for price, quantity, brand, or category.");
        }

        if (price < 0) throw new ServletException("Price cannot be negative.");
        if (quantity < 0) throw new ServletException("Quantity cannot be negative.");

        LocalDate mDate, eDate;
        try {
            mDate = LocalDate.parse(manufactureDate);
            eDate = LocalDate.parse(expiryDate);
        } catch (Exception e) {
            throw new ServletException("Invalid date format.");
        }
        if (eDate.isBefore(mDate)) throw new ServletException("Expiry date cannot be before manufacture date.");

        ProductModel product = new ProductModel();
        product.setProduct_name(productName);
        product.setPrice(price);
        product.setQuantity(quantity);
        if (imageFileName != null) product.setImage(imageFileName);
        product.setManufacture_date(mDate);
        product.setExpiry_date(eDate);
        product.setIngredients(ingredients);
        product.setBrand(new BrandModel(brandId, null));
        product.setCategory(new CategoryModel(categoryId, null));

        // For update, set product_id if present
        String productIdStr = request.getParameter("product_id");
        if (!isAdd && productIdStr != null && !productIdStr.trim().isEmpty()) {
            try {
                product.setProduct_id(Integer.parseInt(productIdStr));
            } catch (NumberFormatException ignored) {}
        }
        return product;
    }
}