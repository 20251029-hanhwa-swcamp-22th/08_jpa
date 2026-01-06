package com.ohgiraffers.section02.crud;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityTransaction;
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

    @BeforeEach
    void setUp() {
        this.entityManagerCRUD = new EntityManagerCRUD();
    }

    @DisplayName("메뉴 코드로 메뉴 조회(SELECT)")
    @ParameterizedTest
    @CsvSource({"1,1","2,2","3,3"})
    void findMenuByMenuCode(int menuCode, int expected) {

        // when
        Menu foundMenu = entityManagerCRUD.findMenuByMenuCode(menuCode);

        // then
        assertEquals(expected, foundMenu.getMenuCode());
        System.out.println(foundMenu);
    }

    private static Stream<Arguments> newMenu() {
        return Stream.of(Arguments.of("아귀찜", 40000, 4, "Y"));
    }

    @DisplayName("새로운 메뉴 추가")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        // when
        Menu menu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);
        Long count = entityManagerCRUD.saveAndReturnAllCount(menu);

        // then
        System.out.println("count = " + count);
        assertEquals(25+1, count);
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
        assertEquals(25, count);
    }


}