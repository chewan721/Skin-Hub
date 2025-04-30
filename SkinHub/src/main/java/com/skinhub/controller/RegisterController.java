package com.skinhub.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.skinhub.model.UserModel;
import com.skinhub.model.RoleModel; // Needed if setting role here (though service handles it)
import com.skinhub.service.RegisterService;
import com.skinhub.util.PasswordUtil; // Controller needs this now
import com.skinhub.util.ValidationUtil; // Controller needs this now

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RegisterController handles customer registration requests (SkinHub)
 * following the "College" system approach (Controller validation & encryption).
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
// Remove @MultipartConfig unless handling file uploads
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Service is still needed, but its role changes slightly
    private final RegisterService registerService = new RegisterService();
    // No ImageUtil needed here unless adding uploads

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // --- 1. Validate Form using ValidationUtil (Controller responsibility in this style) ---
            String validationMessage = validateRegistrationForm(req);
            if (validationMessage != null) {
                handleError(req, resp, validationMessage); // Forward back with error
                return;
            }

            // --- 2. Extract and Prepare UserModel (Controller responsibility) ---
            // Includes PASSWORD ENCRYPTION here
            UserModel userModel = extractUserModelAndEncryptPassword(req);

            // --- 3. Call Service (Service just handles DB logic now) ---
            // The service will receive the model with the *already encrypted* password
            Boolean isAdded = registerService.registerUser(userModel); // Service method

            // --- 4. Handle Service Result ---
            if (isAdded == null) {
                 // This condition might indicate a DB connection issue in the Service constructor
                handleError(req, resp, "Registration service unavailable. Please try again later.");
            } else if (isAdded) {
                // SUCCESS: Use FORWARD like the college example (though REDIRECT is generally better)
                handleSuccess(req, resp, "Your account is successfully created! Please log in.", "/WEB-INF/pages/login.jsp");
            } else {
                // Service returned false (e.g., duplicate found *by service's direct check*)
                // Try to get a specific error if the service provides one
                handleError(req, resp, registerService.getLastError() != null ? registerService.getLastError() : "Registration failed. Username or email might be taken.");
            }
        } catch (DateTimeParseException e) {
             handleError(req, resp, "Invalid Date of Birth format. Please use YYYY-MM-DD.");
        } catch (RuntimeException e) {
             // Catch specific RuntimeExceptions like from PasswordUtil if needed
             handleError(req, resp, "An internal error occurred during processing.");
             System.err.println("Registration Error: " + e.getMessage());
             e.printStackTrace(); // Log unexpected runtime errors
        } catch (Exception e) {
            handleError(req, resp, "An unexpected error occurred. Please try again later!");
            System.err.println("Unexpected Registration Error: " + e.getMessage());
            e.printStackTrace(); // Log unexpected checked exceptions
        }
    }

    /**
     * Performs validation using ValidationUtil.
     * (Mirrors the College controller's validation approach)
     */
    private String validateRegistrationForm(HttpServletRequest req) {
        // Retrieve parameters using consistent names
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String username = req.getParameter("user_name"); // Use userName
        String dobStr = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact"); // Use contact
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("retypePassword"); // Use retypePassword

        // Check for null or empty fields first
        if (ValidationUtil.isNullOrEmpty(firstName)) return "First name is required.";
        if (ValidationUtil.isNullOrEmpty(lastName)) return "Last name is required.";
        if (ValidationUtil.isNullOrEmpty(username)) return "Username is required.";
        if (ValidationUtil.isNullOrEmpty(dobStr)) return "Date of birth is required.";
        if (ValidationUtil.isNullOrEmpty(gender)) return "Gender is required.";
        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required.";
        if (ValidationUtil.isNullOrEmpty(contact)) return "Contact number is required.";
        if (ValidationUtil.isNullOrEmpty(password)) return "Password is required.";
        if (ValidationUtil.isNullOrEmpty(retypePassword)) return "Please retype the password.";

        // Convert date of birth for age check
        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (DateTimeParseException e) {
            return "Invalid date format. Please use YYYY-MM-DD.";
        }

        // Validate field formats and rules using ValidationUtil
        if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
            return "Username must start with a letter and contain only letters and numbers.";
        if (!ValidationUtil.isValidGender(gender)) // Assumes Male/Female/Other allowed
            return "Please select a valid gender.";
        if (!ValidationUtil.isValidEmail(email))
            return "Invalid email format.";
        if (!ValidationUtil.isValidPhoneNumber(contact)) // Use the rule from ValidationUtil
            return "Invalid phone number format."; // Make error msg match rule
        if (!ValidationUtil.isValidPassword(password)) // Check strength
            return "Password must be at least 8 characters, with 1 uppercase, 1 lowercase, 1 number, and 1 special character (@#$%^&+=!).";
        if (!ValidationUtil.doPasswordsMatch(password, retypePassword))
            return "Passwords do not match.";
        if (!ValidationUtil.isAgeAtLeast16(dob)) // Check age
            return "You must be at least 16 years old to register.";

        // No file validation here unless added back

        return null; // All validations passed
    }

    /**
     * Extracts data, ENCRYPTS password, and creates UserModel.
     * (Mirrors the College controller's extraction approach)
     */
    private UserModel extractUserModelAndEncryptPassword(HttpServletRequest req) {
        // Retrieve parameters again (could optimize by passing validated values, but mirroring College style)
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String username = req.getParameter("user_name");
        LocalDate dob = LocalDate.parse(req.getParameter("dob")); // Assumes already validated
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");
        String plainPassword = req.getParameter("password"); // Get plain password

        // --- Encrypt Password HERE in the Controller ---
        String encryptedPassword;
        try {
             // Use the correct PasswordUtil method name
            encryptedPassword = PasswordUtil.encryptPassword(username, plainPassword);
            if (encryptedPassword == null) {
                 // Handle case where encryption itself fails in PasswordUtil
                 throw new RuntimeException("Password encryption returned null.");
            }
        } catch (Exception e) {
             // Wrap encryption specific exceptions if PasswordUtil throws checked ones
             // Or handle RuntimeException thrown by PasswordUtil's catch block
             throw new RuntimeException("Failed to secure password.", e);
        }


        UserModel userModel = new UserModel();
        userModel.setFirst_name(firstName.trim());
        userModel.setLast_name(lastName.trim());
        userModel.setUser_name(username.trim());
        userModel.setDob(dob);
        userModel.setGender(gender);
        userModel.setEmail(email.trim().toLowerCase());
        userModel.setContact(contact.trim());
        userModel.setPassword(encryptedPassword); // Set the ENCRYPTED password

        // Role might be set here or inferred/set in the Service depending on exact College style interpretation
        // Setting it here makes the Service even simpler (just DB interaction)
        // RoleModel roleModel = new RoleModel();
        // roleModel.setRole_id(2); // Explicitly set CUSTOMER Role ID
        // userModel.setRole(roleModel); // Attach role if needed by service/DAO signature

        return userModel;
    }

    // Handle Success (Forwarding like College example)
    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String forwardPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        // Forwarding to login page - consider redirect instead for PRG pattern
        req.getRequestDispatcher(forwardPage).forward(req, resp);
    }

    // Handle Error (Forwarding back to register page)
    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        // Re-populate form fields to retain user input
        req.setAttribute("first_name", req.getParameter("first_name"));
        req.setAttribute("last_name", req.getParameter("last_name"));
        req.setAttribute("user_name", req.getParameter("user_name"));
        req.setAttribute("dob", req.getParameter("dob"));
        req.setAttribute("gender", req.getParameter("gender"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("contact", req.getParameter("contact"));
        // Do NOT re-populate password fields

        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

     @Override
    public void destroy() {
        if (registerService != null) {
            registerService.close(); // Ensure service resources are cleaned up
        }
        super.destroy();
    }
}