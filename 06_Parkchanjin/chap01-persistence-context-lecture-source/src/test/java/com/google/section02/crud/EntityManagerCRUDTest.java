package com.google.section02.crud;

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

  private EntityManagerCRUD entityManagerCRUD;

  @BeforeEach // 각 테스트가 시작하기전에 EntityManagerCRUD 객체를 새로 만들어라
  void setUp() {
    this.entityManagerCRUD = new EntityManagerCRUD();
  }

  @DisplayName("메뉴 코드로 메뉴 조회(SELECT)")
  @ParameterizedTest
  @CsvSource({"1,1", "2,2", "3,3"})
  void findMenuByMenuCode(int menuCode, int expected){

    // when
    Menu foundMenu = entityManagerCRUD.findMenuByMenuCode(menuCode); // menuCode와 일치하는 걸 DB에서 찾겠다

    // then
    assertEquals(expected, foundMenu.getMenuCode()); // 실제로 DB에서 조회한 menuCode와 같은지 확인해보겠다
    System.out.println(foundMenu);

  /* 확인해야되는 내용
  1. 실제 SQL 쿼리 생성
  - JPA가 자동으로 생성한 SELECT 쿼리 확인
  - 콘솔에서 SELECT * FROM tbl_menu WHERE menu_code = ? 형태의 쿼리 출력
  *

  2. 엔티티-테이블 매핑
  - @Entity, @Table, @Column 어노테이션이 올바르게 동작하는지
  - 필드명과 컬럼명이 정확히 매핑되어 출력되 필드에 값이 다 담겨 있는지 확인
  * */
  }

  private static Stream<Arguments> newMenu() {
    return Stream.of(Arguments.of("아귀찜", 40000, 4, "Y"));
  }

  @DisplayName("새로운 메뉴 추가")
  @ParameterizedTest
  @MethodSource("newMenu")
  void testRegist(String menuName, int menuPrice,
                  int categoryCode, String orderableStatus){

    // when
    Menu menu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);

    Long count = entityManagerCRUD.saveAndReturnAllCount(menu);


    // then
    System.out.println("count = " + count);
    assertEquals(28 + 1, count);

  }




  @DisplayName("메뉴 이름 수정 테스트")
  @ParameterizedTest
  @CsvSource("1, 변경 된 이름")
  void testModifyMenuName(int menuCode, String menuName) {
    // when
    Menu modifyMenu = entityManagerCRUD.modifyMenuName(menuCode, menuName);
    // then
    assertEquals(menuName, modifyMenu.getMenuName());
  }




  @DisplayName("메뉴 코드로 메뉴 삭제 테스트")
  @ParameterizedTest
  @ValueSource(ints = {105}) // 100이라는 정수 값을 테스트 메서드의 매개변수로 전달
  void testRemoveMenu(int menuCode) {
    //when
    Long count = entityManagerCRUD.removeAndReturnAllCount(menuCode);
    // then
    assertEquals(29 - 1, count);
  }
}