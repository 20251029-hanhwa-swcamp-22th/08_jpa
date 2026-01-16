package com.my.springdatajpa.menu.service;

import com.my.springdatajpa.menu.dto.MenuDTO;
import com.my.springdatajpa.menu.entity.Menu;
import com.my.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

  private final MenuRepository menuRepository;
  private final ModelMapper modelMapper;
  
  // 생성자 방식의 의존성 주입
  // MenuRepository가 JpaRepository를 상속받았기 때문에
  // EntityManager등을 생성해서 자동으로 의존성 주입이 된다
  public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
    this.menuRepository = menuRepository;
    this.modelMapper = modelMapper;
  }

  /**
   * menuCode가 일치하는 메뉴를 DB에서 조회 후 반환
   * @param menuCode
   * @return 조회된 MenuDTO
   * @throws IllegalArgumentException 조회결과 없으면 예외 발생
   */
  public MenuDTO findMenuByCode(int menuCode) {

    Menu menu = menuRepository.findById(menuCode) //optional null처리 객체
        .orElseThrow(IllegalArgumentException::new);
        // null이면 예외를 던져라

    /* Menu Entity -> MenuDTO로 변환(ModelMapper이용) */
    return modelMapper.map(menu, MenuDTO.class);
  }
}