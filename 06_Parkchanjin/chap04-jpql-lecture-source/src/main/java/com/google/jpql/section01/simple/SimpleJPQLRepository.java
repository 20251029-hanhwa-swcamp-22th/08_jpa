package com.google.jpql.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/* JPQL
*  - 엔티티 중심으로 개발 가능한 객체 지향 쿼리
*  - 특정 DBMS에 의존하지 않음(SQL 실행 시 각 DBMS에 맞는 SQL 형태로 변경)
* */

@Repository
public class SimpleJPQLRepository {

  @PersistenceContext
  private EntityManager manager;

  // TypedQuery를 이용한 단일 컬럼 조회
	public String selectSingleMenuByTypedQuery() {
	  String jpql 
	    = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
                          /*중요!! 테이블 명이 아닌 Entity Name!!*/


    // menuname이 varchar 타입이라 제네릭에 string씀
	  TypedQuery<String> query = manager.createQuery(jpql, String.class);

	  String resultMenuName
        = query.getSingleResult(); // 결과 1행 얻어오기
        // 만약 없거나 1행 초과 시 예외 발생 -> 1행인걸 확실히 알고 있을 때 사용하는게 좋다!
	
	  return resultMenuName;
	}


  // Query를 이용한 단일 컬럼 조회
  public Object selectSingleMenuByQuery() {
    String jpql
        = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";

    Query query = manager.createQuery(jpql);  //결과 값의 타입을 명시하지 않음
    Object resultMenuName = query.getSingleResult();//결과 값은 Object로 반환

    return resultMenuName;
  }


  // TypeQuery를 이용한 단일 행 조회
  public Menu selectSingleRowByTypedQuery() {
    String jpql
        = "SELECT m FROM Section01Menu m WHERE m.menuCode = 8";

    //반환 타입을 row와 매핑할 엔티티 타입으로 설정
    TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);
    Menu resultMenu = query.getSingleResult();

    return resultMenu;
  }


  // TypedQuery 를 이용한 다중행 조회
  public List<Menu> selectMultipleRowByTypedQuery() {
    String jpql = "SELECT m FROM Section01Menu m";
    TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);

    //반환 타입을 row와 매핑할 엔티티 타입으로 설정 ->(한 행만 생각하고 저장타입 생각하기)
    List<Menu> resultMenuList = query.getResultList();

    return resultMenuList;
  }


  // Query를 이용한 다중행 조회
  public List<Menu> selectMultipleRowByQuery() {
    String jpql = "SELECT m FROM Section01Menu m";

    Query query = manager.createQuery(jpql);

    List<Menu> resultMenuList = query.getResultList();

    return resultMenuList;
  }


  public List<Integer> selectUsingDistinct() {
    String jpql = "SELECT DISTINCT m.categoryCode FROM Section01Menu m";
    TypedQuery<Integer> query = manager.createQuery(jpql, Integer.class);
    List<Integer> resultCategoryList = query.getResultList();

    return resultCategoryList;
  }


  // Query를 이용한 in 연산자
  public List<Menu> selectUsingIn() {
    String jpql
        = "SELECT m FROM Section01Menu m WHERE m.categoryCode IN (11, 12)";
    List<Menu> resultMenuList
        = manager.createQuery(jpql, Menu.class).getResultList();

    return resultMenuList;
  }


  // Query를 이용한 Like 연산자
  public List<Menu> selectUsingLike() {
    String jpql
        = "SELECT m FROM Section01Menu m WHERE m.menuName LIKE '%마%'";
    List<Menu> resultMenuList
        = manager.createQuery(jpql, Menu.class).getResultList();

    return resultMenuList;
  }
}