package com.google.jpql.section02.parameter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // DB 연결용 Bean 등록
public class ParameterBindingRepository {

  @PersistenceContext // 영속화
  private EntityManager manager; // 매니저 생성

  /* 이름 기준 파라미터 바인딩 */
	public List<Menu> selectMenuByBindingName(String menuName) {
	  String jpql 
	    = "SELECT m FROM Section02Menu m WHERE m.menuName = :menuName";
	  List<Menu> resultMenuList = manager.createQuery(jpql, Menu.class) // menu.class 있으면 타입쿼리라 생각해두댐
	                                      .setParameter("menuName", menuName)
	                                      .getResultList();
	
	  return resultMenuList;
	}


  /* 위치 기준 파라미터 바인딩
   * 직관적이기도 하고 유지보수 성이 좋은 이름 기준을 많이 사용함!*/
  public List<Menu> selectMenuByBindingPosition(String menuName) {
    String jpql = "SELECT m FROM Section02Menu m WHERE m.menuName = ?1";
    List<Menu> resultMenuList = manager.createQuery(jpql, Menu.class)
        .setParameter(1, menuName)
        .getResultList();

    return resultMenuList;
  }
}