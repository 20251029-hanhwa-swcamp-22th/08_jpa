package com.jinosoft.association.section02.onetomany;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "category_and_menu")
@Table(name = "tbl_category")
public class Category {

  @Id
  private int categoryCode;

  private String categoryName;

  private Integer refCategoryCode;

  @JoinColumn(name = "categoryCode")
  @OneToMany(cascade = CascadeType.PERSIST)
  private List<Menu> menuList;

  protected Category() {
  }

  public Category(
      int categoryCode, String categoryName,
      Integer refCategoryCode, List<Menu> menuList
  ) {
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.refCategoryCode = refCategoryCode;
    this.menuList = menuList;
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

  public List<Menu> getMenuList() {
    return menuList;
  }

  public void setMenuList(List<Menu> menuList) {
    this.menuList = menuList;
  }

  @Override
  public String toString() {
    return "CategoryAndMenu [categoryCode=" + categoryCode +
        ", categoryName=" + categoryName +
        ", refCategoryCode=" + refCategoryCode +
        ", menuList=" + menuList + "]";
  }
}