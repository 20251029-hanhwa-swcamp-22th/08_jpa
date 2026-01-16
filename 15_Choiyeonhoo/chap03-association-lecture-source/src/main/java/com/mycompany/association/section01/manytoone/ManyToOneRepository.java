package com.mycompany.association.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Menu find(int menuCode) {
        return entityManager.find(Menu.class, menuCode);
    }
    
    public String findCategoryName(int menuCode) {
        String jpql = "SELECT c.categoryName" +
                    " FROM menu_and_category m" +
                    " JOIN m.category c" +   // ON 절은 이미 메뉴에 @JoinColumn(name = "categoryCode") 적혀있어서 생략
                    " WHERE m.menuCode = :menuCode"; // :menuCode 는 #{menuCode} 와 같은 모양

        return entityManager.createQuery(jpql, String.class)
                    .setParameter("menuCode", menuCode)
                    .getSingleResult(); // 실행 후 컬럼 1개 결과 반환 받기
    }

    public void regist(Menu menu) {
        entityManager.persist(menu);
    }
}