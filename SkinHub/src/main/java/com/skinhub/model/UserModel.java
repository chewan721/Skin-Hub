package com.skinhub.model;

import java.time.LocalDate;


public class UserModel {
	private int user_id;
	private String first_name;
	private String last_name;
	private String user_name;
	private String email;
	private String contact;
	private String gender;
	private LocalDate dob;
	private String password;
	
	private RoleModel role;
	
	public UserModel() {
		
	}
	
	// this constructor is used in login process.
	public UserModel(String user_name, String password) {
		this.user_name = user_name;
		this.password = password;
	}

	// this constructor is used in admin dashboard to view user list.
	
	public UserModel(int user_id, String first_name, String last_name, String user_name, String email, String contact,
			String gender, LocalDate dob) {
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.user_name = user_name;
		this.email = email;
		this.contact = contact;
		this.gender = gender;
		this.dob = dob;
	}

	public UserModel(int user_id, String first_name, String last_name, String user_name, String email, String contact,
			String gender, LocalDate dob, String password, RoleModel role) {
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.user_name = user_name;
		this.email = email;
		this.contact = contact;
		this.gender = gender;
		this.dob = dob;
		this.password = password;
		this.role = role;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}
	
	
}
