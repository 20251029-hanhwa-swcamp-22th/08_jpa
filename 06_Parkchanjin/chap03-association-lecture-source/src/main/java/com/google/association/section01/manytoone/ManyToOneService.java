package com.google.association.section01.manytoone;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyToOneService {

  private ManyToOneRepository manyToOneRepository;

  public ManyToOneService(ManyToOneRepository manyToOneRepository) {
    this.manyToOneRepository = manyToOneRepository;
  }

  @Transactional // 패치타입 LAZY 사용하려면 필수임
  public Menu findMenu(int menuCode) {
    Menu menu = manyToOneRepository.find(menuCode);

    // LAZY 테스트할떄 주석 해제하면서 확인
    System.out.println(menu.getCategory()); // -> Category 조회

    return menu;
  }

  public String findCategoryNameByJpql(int menuCode) {
    return manyToOneRepository.findCategoryName(menuCode);
  }

  @Transactional
  public void registMenu(MenuDTO menuInfo) {
    Menu menu = new Menu(
        menuInfo.getMenuCode(),
        menuInfo.getMenuName(),
        menuInfo.getMenuPrice(),
        menuInfo.getOrderableStatus(),
        new Category(
            menuInfo.getCategory().getCategoryCode(),
            menuInfo.getCategory().getCategoryName(),
            menuInfo.getCategory().getRefCategoryCode()
        )
        );

    manyToOneRepository.regist(menu);
  }
}