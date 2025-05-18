package com.skinhub.model;

public class BrandModel {

	private int brand_id;
	private String brand_name;

	public BrandModel() {

	}

	/**
	 * @param brand_id
	 * @param brand_name
	 */

	public BrandModel(int brand_id, String brand_name) {
		super();
		this.brand_id = brand_id;
		this.brand_name = brand_name;
	}
	/**
	 * @return the brand_id
	 */
	public int getBrand_id() {
		return brand_id;
	}
	/**
	 * @param brand_id the brand_id to set
	 */
	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}
	/**
	 * @return the brand_name
	 */
	public String getBrand_name() {
		return brand_name;
	}
	/**
	 * @param brand_name the brand_name to set
	 */
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}


}
