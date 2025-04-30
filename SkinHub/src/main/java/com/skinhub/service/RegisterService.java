package com.skinhub.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.skinhub.config.DbConfig;
import com.skinhub.model.UserModel;
import com.skinhub.util.PasswordUtil; // May not be needed if password strength checked elsewhere

/**
 * RegisterService handles DB interactions for SkinHub user registration.
 * (Following College style: receives model with encrypted password, executes direct SQL)
 */
public class RegisterService {

    private Connection dbConn;
    private String lastError; // To communicate specific DB-related errors back

     // Role ID for CUSTOMER (Verify this matches your 'role' table)
    private static final int CUSTOMER_ROLE_ID = 2;
     // Table name (Verify this matches your database)
    private static final String USER_TABLE_NAME = "user"; // CHANGE to "user" IF NEEDED

    /**
     * Constructor initializes the database connection.
     */
    public RegisterService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
             if (this.dbConn == null) {
                 System.err.println("RegisterService: FATAL - DbConfig returned null connection.");
                 // No dbConn means subsequent operations will fail
             }
        } catch (Exception ex) {
            System.err.println("RegisterService: FATAL - Database connection error during initialization: " + ex.getMessage());
            ex.printStackTrace();
            this.dbConn = null; // Ensure dbConn is null if init fails
        }
    }

    public String getLastError(){
        return this.lastError;
    }

    /**
     * Registers a new user using direct SQL.
     * Assumes userModel contains validated data and an ENCRYPTED password.
     *
     * @param userModel the user details with encrypted password
     * @return true if registration successful, false if duplicates found or DB error, null if connection failed initially.
     */
    public Boolean registerUser(UserModel userModel) {
        this.lastError = null; // Reset error

        if (dbConn == null) {
            System.err.println("RegisterService: Cannot register user - Database connection is not available.");
            this.lastError = "Database service unavailable.";
            // Return null to indicate connection failure, distinguishing it from logical failure (false)
            // Although the controller treats null/false similarly in the provided College code.
            // Returning null requires the controller to check specifically: if (isAdded == null) ...
            // Let's stick to boolean as per original college example for simplicity now.
             return false; // Indicate failure
        }

        // --- Direct DB Checks (College Style - No DAO) ---
        try {
            // 1. Check for duplicate username
            if (!isUsernameAvailable(userModel.getUser_name())) {
                 this.lastError = "Username '" + userModel.getUser_name() + "' is already taken.";
                return false;
            }

            // 2. Check for duplicate email
            if (!isEmailAvailable(userModel.getEmail())) {
                 this.lastError = "Email '" + userModel.getEmail() + "' is already registered.";
                return false;
            }

        } catch (SQLException e) {
             System.err.println("RegisterService: Database error during duplicate check: " + e.getMessage());
             e.printStackTrace();
             this.lastError = "Database error during validation.";
             return false;
        }

        // --- Direct INSERT  ---
        String insertQuery = "INSERT INTO " + USER_TABLE_NAME + " (first_name, last_name, user_name, email, contact, gender, dob, password, role_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

            // Password is ALREADY encrypted (done in Controller)
            insertStmt.setString(1, userModel.getFirst_name());
            insertStmt.setString(2, userModel.getLast_name());
            insertStmt.setString(3, userModel.getUser_name());
            insertStmt.setString(4, userModel.getEmail());
            insertStmt.setString(5, userModel.getContact());
            insertStmt.setString(6, userModel.getGender());
            insertStmt.setDate(7, java.sql.Date.valueOf(userModel.getDob()));
            insertStmt.setString(8, userModel.getPassword()); // The encrypted password
            insertStmt.setInt(9, CUSTOMER_ROLE_ID); // Set default role ID

            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0; // Return true if insert was successful

        } catch (SQLException e) {
            System.err.println("RegisterService: Error during user insertion: " + e.getMessage());
            e.printStackTrace();
            // Provide a generic error for database issues during insert
            this.lastError = "Failed to save registration details.";
            return false;
        }
    }

    /**
     * Checks if username is already taken using direct SQL.
     * @param username the username to check
     * @return true if username is available, false if taken or error occurs
     * @throws SQLException if a database access error occurs
     */
    private boolean isUsernameAvailable(String username) throws SQLException {
        // Note: This check might be better combined with the insert using UNIQUE constraints
        // but following the provided service structure.
         if (dbConn == null) throw new SQLException("Connection is null in isUsernameAvailable"); // Guard clause

        String query = "SELECT 1 FROM " + USER_TABLE_NAME + " WHERE user_name = ? LIMIT 1"; // More efficient check
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, username);
            try(ResultSet rs = stmt.executeQuery()){
                return !rs.next(); // Return true if no row found (available)
            }
        }
        // SQLException propagates up
    }

    /**
     * Checks if email is already registered using direct SQL.
     * @param email the email to check
     * @return true if email is available, false if registered or error occurs
     * @throws SQLException if a database access error occurs
     */
    private boolean isEmailAvailable(String email) throws SQLException {
         if (dbConn == null) throw new SQLException("Connection is null in isEmailAvailable"); // Guard clause

        String query = "SELECT 1 FROM " + USER_TABLE_NAME + " WHERE email = ? LIMIT 1"; // More efficient check
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
             try(ResultSet rs = stmt.executeQuery()){
                return !rs.next(); // Return true if no row found (available)
            }
        }
         // SQLException propagates up
    }


    /**
     * Closes the database connection. Should be called when the servlet is destroyed.
     */
    public void close() {
        try {
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
                System.out.println("RegisterService: Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("RegisterService: Error closing database connection: " + e.getMessage());
        }
    }
}