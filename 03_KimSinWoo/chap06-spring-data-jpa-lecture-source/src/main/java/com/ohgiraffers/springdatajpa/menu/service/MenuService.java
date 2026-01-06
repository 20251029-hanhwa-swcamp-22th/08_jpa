package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final ModelMapper modelMapper;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public MenuDTO findMenuByCode(int menuCode) {

        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(menu, MenuDTO.class);
    }

    public List<MenuDTO> findMenuList() {

        List<Menu> menuList
                = menuRepository.findAll(Sort.by("menuCode").descending());

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .toList();
    }

    public Page<MenuDTO> findMenuList(Pageable pageable) {
        // Pageable은 Spring data에서 제공하는 페이징 처리 클래스
        // pageNumber: 0 == 1 페이지
        // pageSize: 한 페이지에 보여질 데이터의 개수
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending()
        );

        Page<Menu> menuList
                = menuRepository.findAll(pageable);

        // Entity -> DTO 변환
        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
        // List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());

        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(menuPrice);

        // Entity -> DTO로 변환하여 반환
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }

    /* JPQL 또는 Native Query를 이용한 카테고리 목록 조회 */
    public List<CategoryDTO> findAllCategory() {
        List<Category> categoryList = this.categoryRepository.findAllCategory();

        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    }

    /* 메뉴 추가 */
    @Transactional
    public void registMenu(MenuDTO menuDTO) {
        // dto -> entity 변환 후 DB 저장
        // 내부적으로 Menu entity를 entity 매니저가 persist()
        menuRepository.save(modelMapper.map(menuDTO, Menu.class));
    }

    /* 메뉴 수정(엔티티 필드 값 수정)
     * 1) 영속 상태 엔티티 준비 == menuCode가 일치하는 엔티티 먼저 조회
     * 2) 영속 상태 엔티티의 필드를 수정 후 commit -> DB에 수정된 내용이 반영
     * */
    @Transactional
    public void modifyMenu(MenuDTO menuDTO) {
        // 1) menuCode가 일치하는 메뉴 조회
        // 조회 결과가 null이면 예외 던짐
        Menu foundMenu = menuRepository.findById(menuDTO.getMenuCode()).orElseThrow(IllegalArgumentException::new);

        // 2) 영속상태 엔티티의 필드 수정
        // setter 사용 (지양) -> 이름 수정 메서드를 따로 만들어 사용
        foundMenu.modifyMenuName(menuDTO.getMenuName());

    }

    @Transactional
    public void deleteMenu(Integer menuCode) {

        menuRepository.deleteById(menuCode);

    }


}