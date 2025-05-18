package com.skinhub.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.skinhub.config.DbConfig;
import com.skinhub.model.RoleModel;
import com.skinhub.model.UserModel;

public class PortfolioService {
    private Connection dbConn;
    private boolean isConnectionError = false;

    public PortfolioService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    // Fetch user by ID
    public UserModel getUserById(int userId) {
        if (isConnectionError) return null;
        String query = "SELECT u.*, r.role_type FROM user u LEFT JOIN role r ON u.role_id = r.role_id WHERE u.user_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RoleModel role = new RoleModel(rs.getString("role_type"));
                return new UserModel(
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("user_name"),
                    rs.getString("email"),
                    rs.getString("contact"),
                    rs.getString("gender"),
                    rs.getDate("dob").toLocalDate(),
                    null, // password not fetched
                    role,
                    rs.getString("imageUrl")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user profile
    public Boolean updateUserProfile(UserModel user) {
        if (isConnectionError) return null;
        int roleId = getRoleId(user.getRole() != null ? user.getRole().getRole_type() : null);
        if (roleId == 0) {
            System.out.println("Invalid role: " + (user.getRole() != null ? user.getRole().getRole_type() : "null"));
            return false;
        }
        String updateSQL = "UPDATE user SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, contact = ?, imageUrl = ?, role_id = ? WHERE user_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(updateSQL)) {
            stmt.setString(1, user.getFirst_name());
            stmt.setString(2, user.getLast_name());
            stmt.setDate(3, user.getDob() != null ? java.sql.Date.valueOf(user.getDob()) : null);
            stmt.setString(4, user.getGender());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getContact());
            stmt.setString(7, user.getImageUrl());
            stmt.setInt(8, roleId);
            stmt.setInt(9, user.getUser_id());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper: get role_id from role_type
    private int getRoleId(String roleType) {
        if (roleType == null) return 0;
        String selectSQL = "SELECT role_id FROM role WHERE role_type = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(selectSQL)) {
            stmt.setString(1, roleType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("role_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}