package com.ecommerce.request;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.Category;
import com.ecommerce.model.Size;

public class CreateProductRequest {
	
	private String imageUrl;
	private String brand;
	private String title;
	private String color;
	private int discountedPrice;
	private int price;
	private int discountedPercent;
	private Set<Size> size = new HashSet<>();
	private int quantity;
	
	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;
	private String description;
	
	
	public CreateProductRequest() {
		super();
	}
	
	public CreateProductRequest(String imageUrl, String brand, String title, String color, int discountedPrice,
			int price, int discountedPercent, Set<Size> size, int quantity, String topLevelCategory,
			String secondLevelCategory, String thirdLevelCategory, String description) {
		super();
		this.imageUrl = imageUrl;
		this.brand = brand;
		this.title = title;
		this.color = color;
		this.discountedPrice = discountedPrice;
		this.price = price;
		this.discountedPercent = discountedPercent;
		this.size = size;
		this.quantity = quantity;
		this.topLevelCategory = topLevelCategory;
		this.secondLevelCategory = secondLevelCategory;
		this.thirdLevelCategory = thirdLevelCategory;
		this.description = description;
	}





	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public int getDiscountedPercent() {
		return discountedPercent;
	}
	public void setDiscountedPercent(int discountedPercent) {
		this.discountedPercent = discountedPercent;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Set<Size> getSize() {
		return size;
	}
	public void setSize(Set<Size> size) {
		this.size = size;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTopLevelCategory() {
		return topLevelCategory;
	}
	public void setTopLevelCategory(String topLevelCategory) {
		this.topLevelCategory = topLevelCategory;
	}
	public String getSecondLevelCategory() {
		return secondLevelCategory;
	}
	public void setSecondLevelCategory(String secondLevelCategory) {
		this.secondLevelCategory = secondLevelCategory;
	}
	public String getThirdLevelCategory() {
		return thirdLevelCategory;
	}
	public void setThirdLevelCategory(String thirdLevelCategory) {
		this.thirdLevelCategory = thirdLevelCategory;
	}
	
	

}
