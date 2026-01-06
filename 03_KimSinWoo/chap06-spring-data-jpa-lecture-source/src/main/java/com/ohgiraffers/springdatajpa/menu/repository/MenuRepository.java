package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Integer> {

    /* Spring Data JPA 쿼리 메서드 */
    // 전달 받은 가격을 초과하는 메뉴 목록 조회 + 정렬
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

    // 전달 받은 가격 이상인 메뉴 목록 조회 + 정렬
    List<Menu> findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(Integer menuPrice);

}