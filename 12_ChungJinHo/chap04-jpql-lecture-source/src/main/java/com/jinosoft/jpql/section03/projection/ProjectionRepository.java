package com.jinosoft.jpql.section03.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectionRepository {

  @PersistenceContext
  private EntityManager manager;
  
	public List<Menu> singleEntityProjection() {
	  String jpql = "SELECT m FROM Section03Menu m";
	  List<Menu> menuList
	    = manager.createQuery(jpql, Menu.class).getResultList();
	
	  return menuList;
	}

	public List<MenuInfo> embeddedTypeProjection() {
		String jpql = "SELECT m.menuInfo FROM EmbeddedMenu m";
		List<MenuInfo> resultMenuInfo
				= manager.createQuery(jpql, MenuInfo.class).getResultList();

		return resultMenuInfo;
	}

	public List<String> scalarTypeProjectionByTypedQuery() {
		String jpql = "SELECT c.categoryName FROM Section03Category c";
		List<String> resultList
				= manager.createQuery(jpql, String.class).getResultList();

		return resultList;
	}

	public List<Object[]> scalarTypeProjectionByQuery() {
		String jpql
				= "SELECT c.categoryCode, c.categoryName FROM Section03Category c";
		List<Object[]> resultList = manager.createQuery(jpql).getResultList();

		return resultList;
	}

	public List<CategoryInfo> newCommandProjection() {
		String jpql
				= "SELECT new com.jinosoft.jpql.section03.projection.CategoryInfo"
				+ "(c.categoryCode, c.categoryName) FROM Section03Category c";
		List<CategoryInfo> resultList
				= manager.createQuery(jpql, CategoryInfo.class).getResultList();

		return resultList;
	}


}