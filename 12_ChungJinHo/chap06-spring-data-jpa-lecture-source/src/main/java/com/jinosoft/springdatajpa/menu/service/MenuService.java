package com.jinosoft.springdatajpa.menu.service;

import com.jinosoft.springdatajpa.menu.dto.MenuDTO;
import com.jinosoft.springdatajpa.menu.entity.Menu;
import com.jinosoft.springdatajpa.menu.repository.MenuRepository;

import com.jinosoft.springdatajpa.menu.dto.CategoryDTO;
import com.jinosoft.springdatajpa.menu.entity.Category;
import com.jinosoft.springdatajpa.menu.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

	private final MenuRepository menuRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;

	public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.menuRepository = menuRepository;
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	/**
	 * menuCode가 일치하는 메뉴를 DB에서 조회 후 반환
	 * 
	 * @param menuCode
	 * @return 조회된 MenuDTO
	 * @throws IllegalArgumentException 조회 결과 없으면 예외 발생
	 */
	public MenuDTO findMenuByCode(int menuCode) {
		Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

		return modelMapper.map(menu, MenuDTO.class);
	}

	public List<MenuDTO> findMenuList() {

		List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

		return menuList.stream()
				.map(menu -> modelMapper.map(menu, MenuDTO.class))
				.collect(Collectors.toList());
	}

	public Page<MenuDTO> findMenuList(Pageable pageable) {

		/*
		 * page 파라미터가 Pageable의 number 값으로 넘어오는데
		 * 해당 값이 조회시에는 인덱스 기준이 되어야 해서 -1 처리가 필요하다.
		 */
		pageable = PageRequest.of(
				pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize(),
				Sort.by("menuCode").descending());

		Page<Menu> menuList = menuRepository.findAll(pageable);

		return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
	}

	public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

		/*
		 * List<Menu> menuList
		 * = menuRepository.findByMenuPriceGreaterThan(menuPrice);
		 */
		/*
		 * List<Menu> menuList
		 * = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
		 */
		List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(
				menuPrice,
				Sort.by("menuPrice").descending());

		return menuList.stream()
				.map(menu -> modelMapper.map(menu, MenuDTO.class))
				.collect(Collectors.toList());
	}

	public List<CategoryDTO> findAllCategory() {

		List<Category> categoryList = categoryRepository.findAllCategory();

		return categoryList.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public void registNewMenu(MenuDTO newMenu) {

		menuRepository.save(modelMapper.map(newMenu, Menu.class));
	}

	@Transactional
	public void modifyMenu(MenuDTO modifyMenu) {
		Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode())
				.orElseThrow(IllegalArgumentException::new);

		/*
		 * setter 사용 (지양)
		 * 이름 수정 메서드를 정의하여 사용
		 */
		foundMenu.modifyMenuName(modifyMenu.getMenuName());
	}

	@Transactional
	public void deleteMenu(Integer menuCode) {

		menuRepository.deleteById(menuCode);

	}

}