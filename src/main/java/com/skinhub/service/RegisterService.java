package com.skinhub.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.skinhub.config.DbConfig;
import com.skinhub.model.RoleModel;
import com.skinhub.model.UserModel;

/**
 * RegisterService handles the registration of new users in the SkinHub system.
 * It manages database interactions for user registration.
 */
public class RegisterService {

    private Connection dbConn;

    /**
     * Constructor initializes the database connection.
     */
    public RegisterService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Registers a new user in the database.
     *
     * @param userModel the user details to be registered
     * @return Boolean indicating the success of the operation
     */
    public Boolean registerUser(UserModel userModel) {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return null;
        }

        String roleQuery = "SELECT role_id FROM role WHERE role_type = ?";
        String insertQuery = "INSERT INTO user (first_name, last_name, user_name, dob, gender, email, contact, password, role_id, imageUrl) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement roleStmt = dbConn.prepareStatement(roleQuery);
             PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

            // Fetch role ID
            roleStmt.setString(1, userModel.getRole().getRole_type());
            ResultSet result = roleStmt.executeQuery();
            int roleId = result.next() ? result.getInt("role_id") : 2; // Default to CUSTOMER role ID (2)

            // Insert user details
            insertStmt.setString(1, userModel.getFirst_name());
            insertStmt.setString(2, userModel.getLast_name());
            insertStmt.setString(3, userModel.getUser_name());
            insertStmt.setDate(4, Date.valueOf(userModel.getDob()));
            insertStmt.setString(5, userModel.getGender());
            insertStmt.setString(6, userModel.getEmail());
            insertStmt.setString(7, userModel.getContact());
            insertStmt.setString(8, userModel.getPassword());
            insertStmt.setInt(9, roleId);
            insertStmt.setString(10, userModel.getImageUrl());

            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}