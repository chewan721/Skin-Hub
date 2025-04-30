package com.skinhub.model;

public class RoleModel {
	private int role_id;
	private String role_type;
	
	public RoleModel() {
		
	}

	/**
	 * @param role_id
	 * @param role_type
	 */
	public RoleModel(int role_id, String role_type) {
		super();
		this.role_id = role_id;
		this.role_type = role_type;
	}

	public RoleModel(String role_type) {
		this.role_type=role_type;
	}
	/**
	 * @return the role_id
	 */
	public int getRole_id() {
		return role_id;
	}

	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	/**
	 * @return the role_type
	 */
	public String getRole_type() {
		return role_type;
	}

	/**
	 * @param role_type the role_type to set
	 */
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	
	
}
