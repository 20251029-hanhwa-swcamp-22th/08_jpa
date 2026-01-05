package com.jinosoft.springdatajpa.menu.repository;

import com.jinosoft.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);
}