package com.skinhub.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

public class ValidationUtil {

    // Validate if a field is null or empty
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Validate if a string starts with a letter and is composed of letters and numbers
    // Adjust regex if you allow underscores or other characters in username
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    // Validate gender (Adjust if you allow 'Other')
    public static boolean isValidGender(String value) {
        // Make case-insensitive and allow "Other"
        return value != null && (value.equalsIgnoreCase("Male") || value.equalsIgnoreCase("Female") || value.equalsIgnoreCase("Other"));
    }

    // Validate if a string is a valid email address
    public static boolean isValidEmail(String email) {
        // Standard regex for basic email format validation
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    // Validate phone number (Example: 10 digits, starting with 98/97 for Nepal)
    // Modify this regex according to your target audience's phone number format
    public static boolean isValidPhoneNumber(String number) {
        if (number == null) {
			return false;
		}
        // Allows optional +country code, spaces, hyphens, but requires a certain length range (e.g., 10-15 digits)
        // This is a more flexible example, adjust as needed:
        String cleanedNumber = number.replaceAll("[\\s\\-\\+\\(\\)]", ""); // Remove common symbols
        // return cleanedNumber.matches("^\\d{10,15}$"); // Just check length
         return cleanedNumber.matches("^(98|97)\\d{8}$"); // Specific Nepal format
    }

    // Validate if password meets strength requirements (reuse from PasswordUtil is fine too)
    public static boolean isValidPassword(String password) {
        if (password == null) {
			return false;
		}
        // Uses the same strong pattern: Min 8 chars, 1 upper, 1 lower, 1 digit, 1 special char
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        // Added ! to the special chars allowed - sync with PasswordUtil if necessary
        return password.matches(pattern);
    }

    // Validate if password and retype password match
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        // Handles null check implicitly via equals
        return password != null && password.equals(retypePassword);
    }

 // 8. Validate if a Part's file extension matches with image extensions (jpg, jpeg, png, gif)
    public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null) return false;
        String fileName = imagePart.getSubmittedFileName();
        if (fileName == null) return false;
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return ext.matches("jpg|jpeg|png|gif");
    }

   
    
    // Validate if the date of birth indicates an age of at least 16 years
    public static boolean isAgeAtLeast16(LocalDate dob) {
        if (dob == null) {
            return false; // Cannot calculate age if dob is null
        }
        LocalDate today = LocalDate.now();
        // Period.between calculates based on whole years completed
        return Period.between(dob, today).getYears() >= 16;
    }

    // Add any other validation methods needed for SkinHub
}