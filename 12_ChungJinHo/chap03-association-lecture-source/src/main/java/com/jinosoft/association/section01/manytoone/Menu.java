package com.jinosoft.association.section01.manytoone;

import jakarta.persistence.*;

@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class Menu {

  @Id
  private int menuCode;

  private String menuName;

  private int menuPrice;

  private String orderableStatus;


  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "categoryCode")
  private Category category;


  protected Menu() {
  }

  public Menu(
      int menuCode, String menuName, int menuPrice,
      String orderableStatus, Category category
  ) {
    this.menuCode = menuCode;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.orderableStatus = orderableStatus;
    this.category = category;
  }

  public int getMenuCode() {
    return menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public int getMenuPrice() {
    return menuPrice;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public Category getCategory() {
    return category;
  }


  @Override
  public String toString() {
    return "Menu{" +
        "menuCode=" + menuCode +
        ", menuName='" + menuName + '\'' +
        ", menuPrice=" + menuPrice +
        ", orderableStatus='" + orderableStatus + '\'' +
        ", category=" + category +
        '}';
  }
}