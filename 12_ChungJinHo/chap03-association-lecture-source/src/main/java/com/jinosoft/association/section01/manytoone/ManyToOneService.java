package com.jinosoft.association.section01.manytoone;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyToOneService {

  private ManyToOneRepository manyToOneRepository;

  public ManyToOneService(ManyToOneRepository manyToOneRepository) {
    this.manyToOneRepository = manyToOneRepository;
  }

  public Menu findMenu(int menuCode) {
    return manyToOneRepository.find(menuCode);
  }

  public String findCategoryNameByJpql(int menuCode) {
    return manyToOneRepository.findCategoryName(menuCode);
  }

  @Transactional
  public void registMenu(MenuDTO menuInfo) {
    com.jinosoft.association.section01.manytoone.Menu menu = new Menu(
        menuInfo.getMenuCode(),
        menuInfo.getMenuName(),
        menuInfo.getMenuPrice(),
        menuInfo.getOrderableStatus(),
        new Category(
            menuInfo.getCategory().getCategoryCode(),
            menuInfo.getCategory().getCategoryName(),
            menuInfo.getCategory().getRefCategoryCode()));

    manyToOneRepository.regist(menu);
  }
}