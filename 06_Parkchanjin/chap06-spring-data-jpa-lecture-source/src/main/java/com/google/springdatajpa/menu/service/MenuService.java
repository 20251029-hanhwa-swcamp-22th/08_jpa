package com.google.springdatajpa.menu.service;

import com.google.springdatajpa.menu.dto.CategoryDTO;
import com.google.springdatajpa.menu.dto.MenuDTO;
import com.google.springdatajpa.menu.entity.Category;
import com.google.springdatajpa.menu.entity.Menu;
import com.google.springdatajpa.menu.repository.CategoryRepository;
import com.google.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    // 생성자 방식의 의존성 주입
		public MenuService(MenuRepository menuRepository,
                       ModelMapper modelMapper,
                       CategoryRepository categoryRepository) {
		this.menuRepository = menuRepository;
    this.modelMapper = modelMapper;
    this.categoryRepository = categoryRepository;
	}


  /* menuCode가 일치하는 메뉴를 DB에서 조회 후 반환
  *  @Param menuCode
  *  @return 조회된 MenuDTO,
  *  @throws IllegalArgumentException 조회 결과가 없으면 예외 발생
  * */
  public MenuDTO findMenuByCode(int menuCode) {

    Menu menu = menuRepository.findById(menuCode)
        .orElseThrow(IllegalArgumentException::new);

    /* Menu Entity -> Menu DTO로 변환 (ModelMapper이용) */
    return modelMapper.map(menu, MenuDTO.class);
  }

  /* 2. 전체 메뉴 조회 서비스 */
  public List<MenuDTO> findMenuList() {

    List<Menu> menuList
        = menuRepository.findAll(Sort.by("menuCode").descending()); // 메뉴코드 기준으로 내림차순 정렬

    // entity -> dto 변환
    return menuList.stream() // map은 기존스트림을 이용해서 새로운 스트림을 만들 떄 사용
        .map(menu -> modelMapper.map(menu, MenuDTO.class))
        .toList();

  }



  /* 3. 전체 메뉴 조회 서비스 + 페이징 */
  public Page<MenuDTO> findMenuList(Pageable pageable) {

    // Pageable은 Spring data에서 제공하는 페이징 처리 클래스
    // - pageNumber : 0 == 1 페이지
    // - pageSize : 한 페이지에 보여질 데이터의 개수
    // - sort : 정렬 방식
    pageable = PageRequest.of(
        pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
        pageable.getPageSize(),
        Sort.by("menuCode").descending()
    );
    Page<Menu> menuList
        = menuRepository.findAll(pageable); // 메뉴코드 기준으로 내림차순 정렬

    // entity -> dto 변환
    return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));

  }

  /* 4. 메뉴 가격 초과 조회*/
  public List<MenuDTO> findByMenuPrice(Integer menuPrice){
//    List<Menu> menuList
//        = menuRepository.findByMenuPriceGreaterThan(
//        menuPrice, Sort.by("menuPrice").descending());

    List<Menu> menuList
        = menuRepository.findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(menuPrice);
    // entity -> dto로 변환하여 반환
    return menuList.stream()
        .map(menu -> modelMapper.map(menu, MenuDTO.class))
        .toList();

  }

  /* 5. JPQL 또는 Native Query 를 이용한 카테고리 목록 조회 */
  public List<CategoryDTO> findAllCategory() {
    List<Category> categoryList
        = categoryRepository.findAllCategory();

    return  categoryList.stream()
        .map(category -> modelMapper.map(category, CategoryDTO.class))
        .toList();
  }


  /* 6. Menu 추가(INSERT (DML)) */
  @Transactional
  public void registMenu(MenuDTO menuDTO){
    // dto -> entity 변환 후 DB 저장
    // ( 내부적으로 Menu 엔티티를 엔티티 매니저가 persist() )
    menuRepository.save(modelMapper.map(menuDTO, Menu.class));
  }


  @Transactional
  public void modifyMenu(MenuDTO modifyMenu) {
    Menu foundMenu
        = menuRepository.findById(modifyMenu.getMenuCode())
        .orElseThrow(IllegalArgumentException::new);

    /* setter 사용 (지양)
     * 이름 수정 메서드를 정의하여 사용 */
    foundMenu.modifyMenuName(modifyMenu.getMenuName());

  }

  @Transactional
  public void deleteMenu(int menuCode) {
    menuRepository.deleteById(menuCode);
  }
}