package org.ho.association.section02.onetomany;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OneToManyService {

  private OneToManyRepository oneToManyRepository;

  public OneToManyService(OneToManyRepository oneToManyRepository) {
    this.oneToManyRepository = oneToManyRepository;
  }

  @Transactional
  public Category findCategory(int categoryCode) {
    Category category = oneToManyRepository.find(categoryCode);
    System.out.println("[category printed ] " + category);  // 이 시점에 menu를 LAZY 로딩을 시작함
    // 왜냐하면 category를 출력하려면 menu를 가져와야 되기 때문이다. Category.toString을 조회해봐라.

    return category;
  }

  @Transactional
  public void registMenu(CategoryDTO categoryInfo) {
    // Category DTO -> Category Entity
    Category category = new Category(
        categoryInfo.getCategoryCode(),
        categoryInfo.getCategoryName(),
        categoryInfo.getRefCategoryCode(),
        null
    );
    // Menu DTO -> Menu Entity
    List<MenuDTO> ml = categoryInfo.getMenuList();
    List<Menu> menuList = new ArrayList<>();
    for (MenuDTO menu : ml) {
      Menu nm = new Menu(
          menu.getMenuCode(),
          menu.getMenuName(),
          menu.getMenuPrice(),
          menu.getCategoryCode(),
          menu.getOrderableStatus());
      menuList.add(nm);
    }
    category.setMenuList(menuList);

    oneToManyRepository.regist(category);
  }

  // N+1 문제 확인용
  @Transactional
  public void checkNPlusOne(){
    List<Category> categories = oneToManyRepository.findAll();

    for(Category category : categories){
      System.out.println("카테고리명 : "+category.getCategoryName());

      // LAZY 로딩일 경우 해당 코드가 수행될 때
      // tbl_menu 테이블을 조회하는 SELECT 수행된다
      System.out.println(category.getCategoryCode()+"번 카테고리의 메뉴 개수 : "+ category.getMenuList().size());
    }
  }
}