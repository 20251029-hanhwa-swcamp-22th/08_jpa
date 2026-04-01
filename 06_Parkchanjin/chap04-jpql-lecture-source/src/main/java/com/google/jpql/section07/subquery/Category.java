package com.google.jpql.section07.subquery;

import com.google.jpql.section06.join.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity(name="Section07Category")
@Table(name="tbl_category")
public class Category {

  @Id
  private int categoryCode;
  private String categoryName;
  private Integer refCategoryCode;



  public Category() {}

  public Category(
    int categoryCode, String categoryName, 
    Integer refCategoryCode, List<com.google.jpql.section06.join.Menu> menuList
  ) {
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



  /* 역방향 참조에 대한 출력 코드는 삭제해야 한다. */
  @Override
  public String toString() {
      return "Category{" +
              "categoryCode=" + categoryCode +
              ", categoryName='" + categoryName + '\'' +
              ", refCategoryCode=" + refCategoryCode +
              '}';
  }
}