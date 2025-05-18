
package com.skinhub.util;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.Part;

/**
 * Utility class for handling image file uploads.
 * <p>
 * This class provides methods for extracting the file name from a {@link Part}
 * object and uploading the image file to a specified directory on the server.
 * </p>
 */
public class ImageUtil {

    // Method to extract image file name from the Part header
    public String getImageNameFromPart(Part part) {
        String contentDisposition = part.getHeader("content-disposition");

        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                // Extract file name and remove surrounding quotes
                return cd.substring(cd.indexOf('=') + 2, cd.length() - 1);
            }
        }
        return null;
    }

    // Method to get the full image save path
    public String getSavePath(String rootPath, String saveFolder) {
        // Append folder structure: /resources/images/[folder]/
        return rootPath + "resources" + File.separator + "images" + File.separator + saveFolder + File.separator;
    }

    // Method to upload the image to the server
    public boolean uploadImage(Part part, String rootPath, String saveFolder) {
        String savePath = getSavePath(rootPath, saveFolder);
        File fileSaveDir = new File(savePath);

        // Create directory if it doesn't exist
        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdirs()) {
                System.out.println("Failed to create directory: " + savePath);
                return false;
            }
        }

        try {
            String imageName = getImageNameFromPart(part);
            if (imageName == null || imageName.isEmpty()) {
                System.out.println("Image name is null or empty.");
                return false;
            }

            String filePath = savePath + File.separator + imageName;

            // Debug info
            System.out.println("Saving image to: " + filePath);

            // Write file to the server
            part.write(filePath);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
