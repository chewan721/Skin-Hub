package com.skinhub.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.skinhub.config.DbConfig;
import com.skinhub.model.RoleModel;
import com.skinhub.model.UserModel;
import com.skinhub.util.PasswordUtil; // Assuming this utility exists and has decrypt

/**
 * Service class for handling login operations in the SkinHub application.
 * Verifies user credentials against the database using secure password practices.
 */
public class LoginService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor initializes the database connection.
     */
    public LoginService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("FATAL: Database connection error during LoginService initialization: " + ex.getMessage());
            ex.printStackTrace();
            isConnectionError = true; // Mark service as unusable
        }
    }

    /**
     * Validates user credentials.
     *
     * @param userModel Contains the user_name and plain text password entered by the user.
     * @return UserModel with full details (excluding password) if valid, null otherwise.
     */
    public UserModel loginUser(UserModel userModel) {
        if (isConnectionError || dbConn == null) {
            System.err.println("LoginService: Cannot perform login due to DB connection issue.");
            return null; // Cannot proceed without DB connection
        }
        if (userModel == null || userModel.getUser_name() == null || userModel.getPassword() == null) {
             System.err.println("LoginService: Invalid input userModel.");
             return null;
        }

        // Query uses JOIN to get role_type directly for clarity
        String query = "SELECT u.user_id, u.first_name, u.last_name, u.user_name, u.dob, u.gender, " +
                       "u.email, u.contact, u.password, r.role_type " +
                       "FROM user u JOIN role r ON u.role_id = r.role_id " + // Assuming role table with role_id, role_type
                       "WHERE u.user_name = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, userModel.getUser_name());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                // User found, now validate password
                return validatePassword(result, userModel.getPassword());
            } else {
                // User not found
                return null;
            }
        } catch (SQLException e) {
            System.err.println("LoginService: Error during login query for user " + userModel.getUser_name() + ": " + e.getMessage());
            e.printStackTrace();
            return null; // Indicates a database error occurred
        }
    }

    /**
     * Validates the provided plain password against the stored encrypted password.
     *
     * @param result       ResultSet containing the user's data from the database (including encrypted password).
     * @param plainPassword The plain text password entered by the user.
     * @return UserModel populated with details if password matches, null otherwise.
     * @throws SQLException If there's an error reading from the ResultSet.
     */
    private UserModel validatePassword(ResultSet result, String plainPassword) throws SQLException {
        String dbUserName = result.getString("user_name");
        String dbEncryptedPassword = result.getString("password");

        // *** Use PasswordUtil.decrypt (Preferred Method) ***
        // Assumes PasswordUtil.decrypt takes encrypted pass and a key/salt (like username)
        String decryptDbPassword = PasswordUtil.decryptPassword(dbEncryptedPassword, dbUserName); // Adjust key as needed

        if (decryptDbPassword != null && decryptDbPassword.equals(plainPassword)) {
            // Passwords match! Populate the user model.
            UserModel validatedUser = new UserModel();
            validatedUser.setUser_id(result.getInt("user_id")); // Include user ID
            validatedUser.setFirst_name(result.getString("first_name"));
            validatedUser.setLast_name(result.getString("last_name"));
            validatedUser.setUser_name(dbUserName);
            // Handle potential null date from DB gracefully
            java.sql.Date dobSql = result.getDate("dob");
            if (dobSql != null) {
                validatedUser.setDob(dobSql.toLocalDate());
            }
            validatedUser.setGender(result.getString("gender"));
            validatedUser.setEmail(result.getString("email"));
            validatedUser.setContact(result.getString("contact"));
            // DO NOT set the password (plain or encrypted) back into the model returned to controller
            // Set the role based on the joined role_type
            validatedUser.setRole(new RoleModel(result.getString("role_type"))); // Assumes RoleModel constructor takes role_type string

            return validatedUser;
        } else {
            // Password mismatch or decryption failed
            return null;
        }
    }

    /**
     * Closes the database connection. Should be called when the application shuts down
     * or the service instance is no longer needed (e.g., in Servlet's destroy method).
     */
    public void close() {
        try {
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
                System.out.println("LoginService: Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("LoginService: Error closing database connection: " + e.getMessage());
        }
    }
}