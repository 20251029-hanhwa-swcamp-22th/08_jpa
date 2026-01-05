package com.jinosoft.jpql.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleJPQLRepository {

  @PersistenceContext
  private EntityManager manager;
  
	public String selectSingleMenuByTypedQuery() {
	  String jpql 
	    = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
	  TypedQuery<String> query = manager.createQuery(jpql, String.class);
	  String resultMenuName = query.getSingleResult();
	
	  return resultMenuName;
	}

	public Object selectSingleMenuByQuery() {
		String jpql
				= "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
		Query query = manager.createQuery(jpql);  //결과 값의 타입을 명시하지 않음
		Object resultMenuName = query.getSingleResult();//결과 값은 Object로 반환

		return resultMenuName;
	}

	public Menu selectSingleRowByTypedQuery() {
		String jpql
				= "SELECT m FROM Section01Menu m WHERE m.menuCode = 8";
		//반환 타입을 row와 매핑할 엔티티 타입으로 설정
		TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);
		Menu resultMenu = query.getSingleResult();

		return resultMenu;
	}

	public List<Menu> selectMultipleRowByTypedQuery() {
		String jpql = "SELECT m FROM Section01Menu m";
		TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);
		//반환 타입을 row와 매핑할 엔티티 타입으로 설정
		List<Menu> resultMenuList = query.getResultList();

		return resultMenuList;
	}

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

	public List<Menu> selectUsingIn() {
		String jpql
				= "SELECT m FROM Section01Menu m WHERE m.categoryCode IN (11, 12)";
		List<Menu> resultMenuList
				= manager.createQuery(jpql, Menu.class).getResultList();

		return resultMenuList;
	}

	public List<Menu> selectUsingLike() {
		String jpql
				= "SELECT m FROM Section01Menu m WHERE m.menuName LIKE '%마%'";
		List<Menu> resultMenuList
				= manager.createQuery(jpql, Menu.class).getResultList();

		return resultMenuList;
	}
}