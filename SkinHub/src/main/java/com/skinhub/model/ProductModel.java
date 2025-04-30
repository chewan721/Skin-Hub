package com.skinhub.model;

import java.time.LocalDate;

public class ProductModel {
	private int product_id;
	private String product_name;
	private double price;
	private int weight; // weight is measured in ml.
	private LocalDate manufacture_date;
	private LocalDate expiry_date;
	private String ingredients;
	private BrandModel brand;
	private CategoryModel category;
	
	public ProductModel() {
		
	}

	public ProductModel(int product_id, String product_name, double price, int weight, LocalDate manufacture_date,
			LocalDate expiry_date, String ingredients, BrandModel brand, CategoryModel category) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.price = price;
		this.weight = weight;
		this.manufacture_date = manufacture_date;
		this.expiry_date = expiry_date;
		this.ingredients = ingredients;
		this.brand = brand;
		this.category = category;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public LocalDate getManufacture_date() {
		return manufacture_date;
	}

	public void setManufacture_date(LocalDate manufacture_date) {
		this.manufacture_date = manufacture_date;
	}

	public LocalDate getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(LocalDate expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public BrandModel getBrand() {
		return brand;
	}

	public void setBrand(BrandModel brand) {
		this.brand = brand;
	}

	public CategoryModel getCategory() {
		return category;
	}

	public void setCategory(CategoryModel category) {
		this.category = category;
	}



	
}
