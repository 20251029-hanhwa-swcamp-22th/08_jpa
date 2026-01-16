package com.my.jpql.section07.subquery;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity(name="Section07Category")
@Table(name="tbl_category")
public class Category {

  @Id
  private int categoryCode;
  private String categoryName;
  private Integer refCategoryCode;


  /* 역방향 참조에 대한 출력 코드는 삭제해야 한다. */
/*  @Override
  public String toString() {
    return "Category{" +
        "categoryCode=" + categoryCode +
        ", categoryName='" + categoryName + '\'' +
        ", refCategoryCode=" + refCategoryCode +
        '}';
  }*/
}