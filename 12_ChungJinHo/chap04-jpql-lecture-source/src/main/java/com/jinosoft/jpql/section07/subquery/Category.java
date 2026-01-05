package com.jinosoft.jpql.section07.subquery;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="Section07Category")
@Table(name="tbl_category")
public class Category {

	@Id
	private int categoryCode;
	private String categoryName;
	private Integer refCategoryCode;
	
	public Category() {}
	
	public Category(
		int categoryCode, String categoryName, Integer refCategoryCode
	) {
		super();
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.refCategoryCode = refCategoryCode;
	}
	
	public int getCategoryCode() {
		return categoryCode;
	}
	
	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public Integer getRefCategoryCode() {
		return refCategoryCode;
	}
	
	public void setRefCategoryCode(Integer refCategoryCode) {
		this.refCategoryCode = refCategoryCode;
	}
	
	@Override
	public String toString() {
		return "Category [categoryCode=" + categoryCode + ", categoryName=" 
				+ categoryName + ", refCategoryCode=" + refCategoryCode + "]";
	}
}