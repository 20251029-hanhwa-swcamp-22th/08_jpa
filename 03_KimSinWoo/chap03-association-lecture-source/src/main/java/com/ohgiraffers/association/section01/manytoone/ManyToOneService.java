package com.ohgiraffers.association.section01.manytoone;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyToOneService {

  private ManyToOneRepository manyToOneRepository;

  public ManyToOneService(ManyToOneRepository manyToOneRepository) {
    this.manyToOneRepository = manyToOneRepository;
  }
  @Transactional // FetchType.LAZY 시 필수
  public Menu findMenu(int menuCode) {
    Menu menu = manyToOneRepository.find(menuCode);

    System.out.println(menu.getCategory());

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