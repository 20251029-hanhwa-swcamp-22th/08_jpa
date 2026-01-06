package org.ho.springdatajpa.menu.service;

import org.ho.springdatajpa.menu.dto.CategoryDTO;
import org.ho.springdatajpa.menu.dto.MenuDTO;
import org.ho.springdatajpa.menu.entity.Category;
import org.ho.springdatajpa.menu.entity.Menu;
import org.ho.springdatajpa.menu.repository.CategoryRepository;
import org.ho.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.modelmapper.Converters.Collection.map;

@Service
public class MenuService {

  private final MenuRepository menuRepository;
  private final ModelMapper modelMapper;
  private final CategoryRepository categoryRepository;

  // 생성자 방식의 DI
  // MenuRepository는 JpaRepository를 extends하기 때문에 어노테이션 없어도 자동으로 빈등록 되어있음
  public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
    this.menuRepository = menuRepository;
    this.modelMapper = modelMapper;
    this.categoryRepository = categoryRepository;
  }

  /**
   * menuCode가 일치하는 메뉴를 DB에서 조회 후 반환
   *
   * @param menuCode
   * @return 조회된 MenuDTO,
   * @throws IllegalArgumentException 조회 결과 없으면 예외 발생
   */
  public MenuDTO findMenuByCode(int menuCode) {

    Menu menu = menuRepository.findById(menuCode)
        .orElseThrow(IllegalArgumentException::new);

    /* Menu Entity -> Menu DTO로 변환 (ModelMapper 이용) */
    return modelMapper.map(menu, MenuDTO.class);
  }

  /* 2. 전체 메뉴 조회 서비스 */
  public List<MenuDTO> findMenuList() {
    List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

    // entity -> DTO
    return menuList.stream()
        .map(menu -> modelMapper.map(menu, MenuDTO.class))
        .toList();
  }

  /* 3. 전체 메뉴 조회 서비스 + 페이징*/
  public Page<MenuDTO> findMenuList(Pageable pageable) {

    // Pageable은 Spring data에서 제공하는 페이징 처리 클래스
    // - pageNumber : 0 == 1페이지
    // - pageSize : 한 페이지에 보여질 데이터의 개수
    // - Sort : 정렬 방식
    pageable = PageRequest.of(
        pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
        pageable.getPageSize(),
        Sort.by("menuCode").descending()
    );

    Page<Menu> menuList = menuRepository.findAll(pageable);

    // entity -> DTO
    // Page interface의 map메서드는 자동으로 stream화 시키는 것 같다.
    return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
  }

  /* 4. 메뉴 가격 초과 조회 */
  public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
    // List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(
    //     menuPrice,
    //     Sort.by("menuPrice").descending());
    List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqualOrderByMenuPriceAsc(menuPrice);

    // entity -> dto로 변환하여 반환
    return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
  }

  /* 5. JPQL 또는 Native Query를 이용한 카테고리 목록 조회 */
  public List<CategoryDTO> findAllCategory() {
    List<Category> categoryList
        = categoryRepository.findAllCategory();
    return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
  }

  /* 6. Menu 추가(INSERT(DML)) */
  @Transactional
  public void registMenu(MenuDTO menuDTO) {
    // dto -> entity 변환 후 save(db에 저장)
    // (내부적으로 Menu entity를 엔티티 매니저가 persist())
    menuRepository.save(modelMapper.map(menuDTO, Menu.class));  // persist로 바꿈
  }

  /* 7. 메뉴 수정(엔티티 필드 값 수정)
   * 1) 영속 상태 엔티티 준비 == menuCode가 일치하는 엔티티 먼저 조회
   * 2) 영속 상태 엔티티의 필드를 수정 후 commit -> DB에 수정된 내용이 반영
   * */
  @Transactional
  public void modifyMenu(MenuDTO menuDTO) {

    // 1) menuCode가 일치하는 메뉴 엔티티 조회
    //    조회 결과가 null이면 예외 던짐
    Menu foundMenu = menuRepository.findById(menuDTO.getMenuCode())
        .orElseThrow(IllegalArgumentException::new);

    // 2) 영속상태 엔티티의 필드 수정
    /* setter 사용 (지양)
     * 이름 수정 메서드를 정의하여 사용 */
    foundMenu.modifyMenuName(menuDTO.getMenuName());
  }

  /* 8. 메뉴 삭제 */
  @Transactional
  public void deleteMenu(int menuCode){
    menuRepository.deleteById(menuCode);
  }
}