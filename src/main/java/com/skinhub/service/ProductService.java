package com.skinhub.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.skinhub.config.DbConfig;
import com.skinhub.model.BrandModel;
import com.skinhub.model.CategoryModel;
import com.skinhub.model.ProductModel;

public class ProductService {
    private Connection dbConn;

    public ProductService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductModel> searchAndSortProducts(String search, String category, String sort) throws SQLException {
        List<ProductModel> products = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT p.*, b.brand_name, c.category_name FROM product p " +
            "JOIN brand b ON p.brand_id = b.brand_id " +
            "JOIN category c ON p.category_id = c.category_id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            query.append(" AND (p.product_name LIKE ? OR b.brand_name LIKE ? OR c.category_name LIKE ? OR p.ingredients LIKE ?)");
            String like = "%" + search.trim() + "%";
            params.add(like); params.add(like); params.add(like); params.add(like);
        }
        if (category != null && !category.trim().isEmpty() && !"all".equalsIgnoreCase(category)) {
            query.append(" AND c.category_name = ?");
            params.add(category);
        }

        // Sorting
        if (sort != null) {
            switch (sort) {
                case "price_asc":
                    query.append(" ORDER BY p.price ASC");
                    break;
                case "price_desc":
                    query.append(" ORDER BY p.price DESC");
                    break;
                case "name_asc":
                    query.append(" ORDER BY p.product_name ASC");
                    break;
                case "name_desc":
                    query.append(" ORDER BY p.product_name DESC");
                    break;
                default:
                    query.append(" ORDER BY p.product_id DESC");
            }
        } else {
            query.append(" ORDER BY p.product_id DESC");
        }

        try (PreparedStatement stmt = dbConn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
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