package com.skinhub.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.skinhub.config.DbConfig;
import com.skinhub.model.BrandModel;
import com.skinhub.model.CategoryModel;
import com.skinhub.model.ProductModel;
import com.skinhub.model.UserModel;

public class AdminDashboardService {
    private Connection dbConn;

    public AdminDashboardService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public int getTotalUsers() throws SQLException {
        String query = "SELECT COUNT(user_id) AS total_users FROM user";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total_users");
        }
        return 0;
    }


    // NEW: Get total quantity (sum of all product quantities)
    public int getTotalProducts() throws SQLException {
        String query = "SELECT SUM(quantity) AS total_quantity FROM product";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total_quantity");
        }
        return 0;
    }

    public int getTotalBrands() throws SQLException {
        String query = "SELECT COUNT(brand_id) AS total_brands FROM brand";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total_brands");
        }
        return 0;
    }

    public int getTotalCategories() throws SQLException {
        String query = "SELECT COUNT(category_id) AS total_categories FROM category";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total_categories");
        }
        return 0;
    }

    // Fetch products with brand and category info
    public List<ProductModel> getAllProducts() throws SQLException {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT p.*, b.brand_name, c.category_name FROM product p " +
                       "JOIN brand b ON p.brand_id = b.brand_id " +
                       "JOIN category c ON p.category_id = c.category_id";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setImage(rs.getString("image"));
                product.setManufacture_date(rs.getDate("manufacture_date").toLocalDate());
                product.setExpiry_date(rs.getDate("expiry_date").toLocalDate());
                product.setIngredients(rs.getString("ingredients"));
                product.setBrand(new BrandModel(rs.getInt("brand_id"), rs.getString("brand_name")));
                product.setCategory(new CategoryModel(rs.getInt("category_id"), rs.getString("category_name")));
                products.add(product);
            }
        }
        return products;
    }

    public List<UserModel> getAllUsers() throws SQLException {
        List<UserModel> users = new ArrayList<>();
        String query = "SELECT u.*, r.role_type FROM user u LEFT JOIN role r ON u.role_id = r.role_id";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UserModel user = new UserModel(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("user_name"),
                    rs.getString("email"),
                    rs.getString("contact"),
                    rs.getString("gender"),
                    rs.getDate("dob").toLocalDate(),
                    rs.getString("imageUrl")
                );
                if (rs.getString("role_type") != null) {
                    user.setRole(new com.skinhub.model.RoleModel(rs.getString("role_type")));
                }
                users.add(user);
            }
        }
        return users;
    }

    // CREATE
    public void addProduct(ProductModel product) throws SQLException {
        String query = "INSERT INTO product (product_name, price, quantity, image, manufacture_date, expiry_date, ingredients, brand_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, product.getProduct_name());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, product.getImage());
            stmt.setDate(5, java.sql.Date.valueOf(product.getManufacture_date()));
            stmt.setDate(6, java.sql.Date.valueOf(product.getExpiry_date()));
            stmt.setString(7, product.getIngredients());
            stmt.setInt(8, product.getBrand().getBrand_id());
            stmt.setInt(9, product.getCategory().getCategory_id());
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new SQLException("Product insert failed, no rows affected.");
        }
    }

    // UPDATE
    public boolean updateProduct(ProductModel product) throws SQLException, ClassNotFoundException {
        String sql;
        boolean hasImage = product.getImage() != null && !product.getImage().isEmpty();
        if (hasImage) {
            sql = "UPDATE product SET product_name=?, price=?, quantity=?, manufacture_date=?, expiry_date=?, ingredients=?, brand_id=?, category_id=?, image=? WHERE product_id=?";
        } else {
            sql = "UPDATE product SET product_name=?, price=?, quantity=?, manufacture_date=?, expiry_date=?, ingredients=?, brand_id=?, category_id=? WHERE product_id=?";
        }

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProduct_name());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setDate(4, java.sql.Date.valueOf(product.getManufacture_date()));
            stmt.setDate(5, java.sql.Date.valueOf(product.getExpiry_date()));
            stmt.setString(6, product.getIngredients());
            stmt.setInt(7, product.getBrand().getBrand_id());
            stmt.setInt(8, product.getCategory().getCategory_id());
            if (hasImage) {
                stmt.setString(9, product.getImage());
                stmt.setInt(10, product.getProduct_id());
            } else {
                stmt.setInt(9, product.getProduct_id());
            }
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public void deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM product WHERE product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }

    // Fetch all brands for dropdown
    public List<BrandModel> getAllBrands() throws SQLException {
        List<BrandModel> brands = new ArrayList<>();
        String query = "SELECT * FROM brand";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                brands.add(new BrandModel(rs.getInt("brand_id"), rs.getString("brand_name")));
            }
        }
        return brands;
    }

    // Fetch all categories for dropdown
    public List<CategoryModel> getAllCategories() throws SQLException {
        List<CategoryModel> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(new CategoryModel(rs.getInt("category_id"), rs.getString("category_name")));
            }
        }
        return categories;
    }
}