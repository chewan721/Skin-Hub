package com.skinhub.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;

    // Generate random bytes for IV and salt
    private static byte[] getRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    // Derive secret key from password and salt
    private static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     * Encrypts password for storage
     * @param username Used as part of encryption key (should be unique per user)
     * @param password The password to encrypt
     * @return Base64 encoded encrypted string (contains IV + salt + ciphertext)
     */
    public static String encryptPassword(String username, String password) {
        try {
            byte[] salt = getRandomBytes(SALT_LENGTH_BYTE);
            byte[] iv = getRandomBytes(IV_LENGTH_BYTE);
            
            SecretKey key = getAESKeyFromPassword(username.toCharArray(), salt);
            
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            
            byte[] cipherText = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            
            // Combine IV + salt + ciphertext
            byte[] encryptedData = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();
            
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Password encryption failed", e);
        }
    }

    /**
     * Decrypts password from storage
     * @param encryptedPassword Base64 encoded encrypted string
     * @param username Same username used during encryption
     * @return Decrypted password
     */
    public static String decryptPassword(String encryptedPassword, String username) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedPassword);
            ByteBuffer bb = ByteBuffer.wrap(decoded);
            
            // Extract IV, salt, and ciphertext
            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);
            
            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);
            
            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);
            
            SecretKey key = getAESKeyFromPassword(username.toCharArray(), salt);
            
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            
            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Password decryption failed", e);
        }
    }
    
 // Add to PasswordUtil class
    public static boolean isPasswordStrong(String password) {
        // Minimum 8 chars, at least 1 uppercase, 1 lowercase, 1 number, 1 special char
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }

    public static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&+=";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}