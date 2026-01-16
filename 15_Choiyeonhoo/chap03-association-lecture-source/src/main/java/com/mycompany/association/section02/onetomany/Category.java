package com.mycompany.association.section02.onetomany;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "category_and_menu")
@Table(name = "tbl_category")
public class Category {

    @Id
    private int categoryCode;

    private String categoryName;

    private Integer refCategoryCode;

    /* @OneToMany
    * - 일대다(1:N) 관계를 매핑할 때 사용
    * - Category Entity 하나(1)와 여러 Menu Entity(N)를 가질수 있는 관계
    *
    * [주의점]
    * - 일대다 단방향 매핑은
    *   현재 Entity(Category)가 아니라, 반대편 Entity(Menu)에 외래키(FK)가 있기 때문에
    *   연관 관계 처리를 위해서 UPDATE SQL이 추가적으로 실행될 수 있다.
    *   -> 현재 Entity의 PK와 반대편 Entity의 FK를 직접 등록 해줘도
    *      불필요한 UPDATE SQL이 시행된다.
    * - "N+1 문제"가 발생할 수 있다.
    *
    * - fetch = FetchType.LAZY (기본값)
    *   - Category Entity만 먼저 조회 후, Menu Entity가 필요한 시점에 따로 조회
    *   -> Select가 2회 수행
    *
    * - fetch = FetchType.EAGER
    *  - Category Entity 조회 시, Menu Entity를 Join하여 같이 조회
    *  -> Select가 1회 수행
    * */


    @JoinColumn(name="categoryCode")
    // OneToMany 의 fetch 기본값은 LAZY, ManyToOne과 반대
    @OneToMany(cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Menu> menuList;

    protected Category() {}

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