package com.mycompany.association.section02.onetomany;

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
//        System.out.println("[category printed ] " + category); // category.toString에 menu 불러오는 구문이 있어 이떄 LAZY 실행

        return category;
    }

    @Transactional
    public void registMenu(CategoryDTO categoryInfo) {
      
        // CategoryDTO -> Category Entity로 변환
        Category category = new Category(
                categoryInfo.getCategoryCode(),
                categoryInfo.getCategoryName(),
                categoryInfo.getRefCategoryCode(),
                null
        );
        
        // MenuDTO -> Menu Entity로 변환
        Menu menu = new Menu(
                categoryInfo.getMenuList().get(0).getMenuCode(),
                categoryInfo.getMenuList().get(0).getMenuName(),
                categoryInfo.getMenuList().get(0).getMenuPrice(),
                categoryInfo.getMenuList().get(0).getCategoryCode(),
                categoryInfo.getMenuList().get(0).getOrderableStatus()
        );
        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        category.setMenuList(menuList);

        oneToManyRepository.regist(category);
    }

  // N+1문제 확인용
  @Transactional
  public void checkNPlusOne(){
    List<Category> categories = oneToManyRepository.findAll();

    for(Category category : categories){
      System.out.println("카테고리명 : " + category.getCategoryName());

      // LAZY 로딩일 경우 해당 코드가 수행 될 때, tbl_menu 테이블을 조회하는 Select가 수행된다.
      System.out.println("해당 카테고리 메뉴 개수 : " + category.getMenuList().size());
    }

  }


}