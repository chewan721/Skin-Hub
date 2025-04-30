<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - SkinHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/portfolio.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
    <%@ include file="header.jsp" %>  

    <section class="profile-container">
        <div class="container">
            <h2 class="section-title">My Profile</h2>
            
            <div class="profile-content">
                <!-- Profile Main Content -->
                <div class="profile-main">
                    <!-- Personal Information Section -->
                    <div id="personal-info" class="profile-section active">
                        <div class="section-header">
                            <h3>Personal Information</h3>
                            <button id="edit-profile-btn" class="btn"><i class="fas fa-edit"></i> Edit Profile</button>
                        </div>
                        
                        <!-- View Mode -->
                        <div id="profile-view-mode" class="profile-details">
                            <div class="profile-info">
                                <div class="info-group">
                                    <label>Username:</label>
                                    <p id="username-display">${user.user_name}</p>
                                </div>
                                <div class="info-group">
                                    <label>Full Name:</label>
                                    <p id="fullname-display">${user.first_name} ${user.last_name}</p>
                                </div>
                                <div class="info-group">
                                    <label>Email:</label>
                                    <p id="email-display">${user.email}</p>
                                </div>
                                <div class="info-group">
                                    <label>Phone:</label>
                                    <p id="phone-display">${user.contact}</p>
                                </div>
                                <div class="info-group">
                                    <label>Date of Birth:</label>
                                    <p id="dob-display">
                                        <c:if test="${not empty user.dob}">
                                            ${user.dob.toString()}
                                        </c:if>
                                    </p>
                                </div>
                                <div class="info-group">
                                    <label>Account Type:</label>
                                    <p id="role-display">${user.role.role_type}</p>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Edit Mode -->
                        <div id="profile-edit-mode" class="profile-edit-form" style="display: none;">
                            <form id="profile-form" action="${pageContext.request.contextPath}/update-profile" method="post">
                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="first_name">First Name:</label>
                                        <input type="text" id="first_name" name="first_name" value="${user.first_name}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="last_name">Last Name:</label>
                                        <input type="text" id="last_name" name="last_name" value="${user.last_name}" required>
                                    </div>
                                </div>
                                
                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="email">Email:</label>
                                        <input type="email" id="email" name="email" value="${user.email}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="contact">Phone:</label>
                                        <input type="tel" id="contact" name="contact" value="${user.contact}">
                                    </div>
                                </div>
                                
                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="dob">Date of Birth:</label>
                                        <input type="date" id="dob" name="dob" 
                                            value="<c:if test='${not empty user.dob}'>${user.dob}</c:if>">
                                    </div>
                                    <div class="form-group">
                                        <label for="gender">Gender:</label>
                                        <select id="gender" name="gender">
                                            <option value="Male" ${user.gender eq 'Male' ? 'selected' : ''}>Male</option>
                                            <option value="Female" ${user.gender eq 'Female' ? 'selected' : ''}>Female</option>
                                            <option value="Other" ${user.gender eq 'Other' ? 'selected' : ''}>Other</option>
                                        </select>
                                    </div>
                                </div>
                                
                                <div class="form-actions">
                                    <button type="button" id="cancel-edit" class="btn btn-secondary">Cancel</button>
                                    <button type="submit" id="save-profile" class="btn btn-primary">Save Changes</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    
                    <!-- Account Settings Section -->
                    <div id="settings" class="profile-section">
                        <div class="section-header">
                            <h3>Account Settings</h3>
                        </div>
                        <div class="settings-options">
                            <div class="settings-group">
                                <h4>Password</h4>
                                <button id="change-password-btn" class="btn btn-secondary">Change Password</button>
                                <!-- Password Change Form (Hidden Initially) -->
                                <div id="password-change-form" style="display: none; margin-top: 15px;">
                                    <form action="${pageContext.request.contextPath}/change-password" method="post">
                                        <div class="form-group">
                                            <label for="current-password">Current Password:</label>
                                            <input type="password" id="current-password" name="current_password" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="new-password">New Password:</label>
                                            <input type="password" id="new-password" name="new_password" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="confirm-password">Confirm Password:</label>
                                            <input type="password" id="confirm-password" name="confirm_password" required>
                                        </div>
                                        <div class="form-actions">
                                            <button type="button" id="cancel-password-change" class="btn btn-secondary">Cancel</button>
                                            <button type="submit" class="btn btn-primary">Update Password</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            
                            -
                            
                            <div class="settings-group">
                                <h4>Delete Account</h4>
                                <p class="warning-text">This action cannot be undone. All your data will be permanently deleted.</p>
                                <button id="delete-account-btn" class="btn btn-danger">Delete Account</button>
                                
                                <!-- Delete Confirmation Modal (Hidden Initially) -->
                                <div id="delete-confirmation" style="display: none; margin-top: 15px;">
                                    <p>Are you sure you want to delete your account? This action is irreversible.</p>
                                    <form action="${pageContext.request.contextPath}/delete-account" method="post">
                                        <div class="form-group">
                                            <label for="confirm-password-delete">Enter your password to confirm:</label>
                                            <input type="password" id="confirm-password-delete" name="password" required>
                                        </div>
                                        <div class="form-actions">
                                            <button type="button" id="cancel-delete" class="btn btn-secondary">Cancel</button>
                                            <button type="submit" class="btn btn-danger">Permanently Delete Account</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <%@ include file="footer.jsp" %>  

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Navigation between profile sections
            const navLinks = document.querySelectorAll('.profile-nav ul li a');
            const profileSections = document.querySelectorAll('.profile-section');
            
            if (navLinks.length > 0) {
                navLinks.forEach(link => {
                    link.addEventListener('click', function(e) {
                        e.preventDefault();
                        
                        // Update active navigation
                        document.querySelector('.profile-nav ul li.active').classList.remove('active');
                        this.parentElement.classList.add('active');
                        
                        // Show corresponding section
                        const targetId = this.getAttribute('href');
                        profileSections.forEach(section => {
                            section.classList.remove('active');
                        });
                        document.querySelector(targetId).classList.add('active');
                    });
                });
            }
            
            // Edit Profile Functionality
            const editProfileBtn = document.getElementById('edit-profile-btn');
            const cancelEditBtn = document.getElementById('cancel-edit');
            const viewMode = document.getElementById('profile-view-mode');
            const editMode = document.getElementById('profile-edit-mode');
            
            if (editProfileBtn) {
                editProfileBtn.addEventListener('click', function() {
                    viewMode.style.display = 'none';
                    editMode.style.display = 'block';
                });
            }
            
            if (cancelEditBtn) {
                cancelEditBtn.addEventListener('click', function() {
                    viewMode.style.display = 'flex';
                    editMode.style.display = 'none';
                });
            }
            
            // Password Change Functionality
            const changePasswordBtn = document.getElementById('change-password-btn');
            const cancelPasswordChange = document.getElementById('cancel-password-change');
            const passwordChangeForm = document.getElementById('password-change-form');
            
            if (changePasswordBtn) {
                changePasswordBtn.addEventListener('click', function() {
                    passwordChangeForm.style.display = passwordChangeForm.style.display === 'none' ? 'block' : 'none';
                });
            }
            
            if (cancelPasswordChange) {
                cancelPasswordChange.addEventListener('click', function() {
                    passwordChangeForm.style.display = 'none';
                });
            }
            
            // Account Deletion Functionality
            const deleteAccountBtn = document.getElementById('delete-account-btn');
            const cancelDelete = document.getElementById('cancel-delete');
            const deleteConfirmation = document.getElementById('delete-confirmation');
            
            if (deleteAccountBtn) {
                deleteAccountBtn.addEventListener('click', function() {
                    deleteConfirmation.style.display = deleteConfirmation.style.display === 'none' ? 'block' : 'none';
                });
            }
            
            if (cancelDelete) {
                cancelDelete.addEventListener('click', function() {
                    deleteConfirmation.style.display = 'none';
                });
            }
            
            // Form Validation
            const forms = document.querySelectorAll('form');
            forms.forEach(form => {
                form.addEventListener('submit', function(e) {
                    // Password confirmation check
                    if (this.id === 'password-change-form') {
                        const newPassword = document.getElementById('new-password').value;
                        const confirmPassword = document.getElementById('confirm-password').value;
                        
                        if (newPassword !== confirmPassword) {
                            e.preventDefault();
                            alert('New password and confirmation do not match!');
                            return;
                        }
                        
                        if (newPassword.length < 8) {
                            e.preventDefault();
                            alert('Password must be at least 8 characters long!');
                            return;
                        }
                    }
                    
                    // Delete account confirmation
                    if (this.action.includes('delete-account')) {
                        if (!confirm('Are you absolutely sure you want to delete your account? This cannot be undone.')) {
                            e.preventDefault();
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>