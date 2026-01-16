package com.work.section02.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerCRUDTest {

  private EntityManagerCRUD entityMangerCRUD;

  @BeforeEach
  void setUp() {
    this.entityMangerCRUD = new EntityManagerCRUD();
  }

  @DisplayName("메뉴 코드로 메뉴 조회(SELECT)")
  @ParameterizedTest
  @CsvSource({"1,1", "2,2", "3,3"})
  void findMenuByMenuCode(int menuCode, int expected) {
    //when
    Menu foundMenu = entityMangerCRUD.findMenuByMenuCode(menuCode);
    //then
    assertEquals(expected, foundMenu.getMenuCode());
    System.out.println("foundMenu = " + foundMenu);
  }


  private static Stream<Arguments> newMenu() {
    return Stream.of(Arguments.of("아귀찜", 40000,4, "Y"));
  }

  @DisplayName("새로운 메뉴 추가")
  @ParameterizedTest
  @MethodSource("newMenu")
  void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus) {

    //when
    Menu menu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);
    Long count = entityMangerCRUD.saveAndReturnAllCount(menu);//  추가하고 전체 몇개인가
//then
    System.out.println("count = " + count);
    assertEquals(21+1, count);

  }

  @DisplayName("메뉴 이름 수정 테스트")
  @ParameterizedTest
  @CsvSource("1, 변경 된 이름")
  void testModifyMenuName(int menuCode, String menuName) {
    // when
    Menu modifiedMenu = entityMangerCRUD.modifyMenuName(menuCode, menuName);

    // then
    assertEquals(menuName, modifiedMenu.getMenuName());
  }

  @DisplayName("메뉴 삭제 테스트")
  @ParameterizedTest
  @ValueSource(ints = {22})
  void testRemoveMenu(int menuCode) {
    //when
    Long count = entityMangerCRUD.removeAndReturnAllCount(menuCode);

    //then
    assertEquals(22-1, count);
  }


}

/* 확인해야되는 내용
          1. 실제 SQL 쿼리 생성
          - JPA가 자동으로 생성한 SELECT 쿼리 확인
          - 콘솔에서 SELECT * FROM tbl_menu WHERE menu_code = ? 형태의 쿼리 출력
          *

          2. 엔티티-테이블 매핑
          - @Entity, @Table, @Column 어노테이션이 올바르게 동작하는지
          - 필드명과 컬럼명이 정확히 매핑되어 출력되 필드에 값이 다 담겨 있는지 확인
        * */