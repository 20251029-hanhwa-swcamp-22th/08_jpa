package com.beyond.nativequery.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NativeQueryRepository {

  @PersistenceContext
  private EntityManager manager;

  /* Native Query 수행 결과를 엔티티 클래스에 매핑 시키기 위해서는
  *   도든 컬럼이 처리되어야만 한다.
  *   == 엔티티 클래스의 한 필드라도 값을 매핑 시키지 않으면 오류 발생
  * */

  public Menu nativeQueryByResultType(int menuCode) {
	  String query 
		  = "SELECT" +
		    " menu_code, menu_name, menu_price, category_code, orderable_status" +
	      " FROM tbl_menu" +
	      " WHERE menu_code = ?";

    // 조회 결과를 Menu 엔티티에 매핑
	  Query nativeQuery
		  = manager.createNativeQuery(query, Menu.class)
						  .setParameter(1, menuCode);

    // 쿼리 수행 결과가 Object이기 때문에 Menu로 다운 캐스팅
	  return (Menu) nativeQuery.getSingleResult();

    // 생성된 SQL을 확인해보면
    // - 엔티티명 X, 테이블명 O
    // - 별칭 X
	}

  public List<Object[]> nativeQueryByNoResultType() {

    /* 컬럼을 2개만 조회하기 때문에 Menu 엔티티 클래스에 매핑 불가
     *  -> Object[]로 값 저장
     *  */
    String query = "SELECT menu_name, menu_price FROM tbl_menu";

    return manager.createNativeQuery(query).getResultList();
  }

  public List<Object[]> nativeQueryByAutoMapping() {
    String query
        = "SELECT a.category_code, a.category_name, a.ref_category_code," +
        " COALESCE(v.menu_count, 0) menu_count" +
        " FROM tbl_category a" +
        " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
        " FROM tbl_menu b" +
        " GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
        " ORDER BY 1";

    Query nativeQuery
        = manager.createNativeQuery(query, "categoryCountAutoMapping");

    // List<Object[]> 중 Object[] 형태
    // 0번 인덱스 : Category Entity
    // 1번 인덱스 : menu_code 컬럼 값

    return nativeQuery.getResultList();
  }

  public List<Object[]> nativeQueryByManualMapping() {
    String query
        = "SELECT a.category_code, a.category_name, a.ref_category_code," +
        " COALESCE(v.menu_count, 0) menu_count" +
        " FROM tbl_category a" +
        " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
        " FROM tbl_menu b" +
        " GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
        " ORDER BY 1";

    Query nativeQuery
        = manager.createNativeQuery(query, "categoryCountManualMapping");
    return nativeQuery.getResultList();
  }


}