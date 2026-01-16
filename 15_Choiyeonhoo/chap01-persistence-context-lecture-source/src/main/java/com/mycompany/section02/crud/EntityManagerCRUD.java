package com.mycompany.section02.crud;

import com.mycompany.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/* EntityManager를 이용하여 JPA CRUD 실행 */
public class EntityManagerCRUD {

  private EntityManager entityManager;

  /* 1. 특정 메뉴 코드로 메뉴를 조회하기 */
  public Menu findMenuByMenuCode(int menuCode){
    entityManager = EntityManagerGenerator.getInstance();
    
    // Menu.class와 매핑된 tbl_menu 테이블에서
    // menu_Code가 menuCode와 일치하는 행을 찾는
    // SQL을 만들어 DB에 Query를 보낸 후,
    // 조회 한 결과를 Menu.class로 만든 객체에 담아서 반환
    return entityManager.find(Menu.class, menuCode);
  }

  /* 2. 새로운 메뉴를 저장하는 기능 */
  public Long saveAndReturnAllCount(Menu newMenu){

    // 새로운 EntityManager Instance 생성 (각 작업마다 독립적인 Persistence context)
    entityManager = EntityManagerGenerator.getInstance();

    // Transaction을 얻어와서 시작
    // - JPA의 쓰기 작업(INSERT, UPDATE, DELETE) 시 필수로 작성
    EntityTransaction entityTransaction = entityManager.getTransaction();

    entityTransaction.begin();

    // 새 메뉴 데이터(newMenu)를 persistence context에 저장
    // = newMenu 객체를 "영속 상태"로 만듦
    // 이 시점에는 아직 1차 캐시 상태 (아직 DB 반영 X)
    entityManager.persist(newMenu);

    // 실제로 newMenu의 내용이 DB에 반영
    // -> newMenu의 @Id 필드 값이랑 일치하는 PK가 없다면 INSERT
    //    있으면 Update
    entityTransaction.commit();

    return getCount(entityManager);
  }

  public Long getCount(EntityManager entityManager){
    return entityManager.createQuery("select count(*) from Section02Menu",Long.class).getSingleResult();
  }

  /* 3. 메뉴 이름 수정하는 기능 */
  public Menu modifyMenuName(int menuCode, String menuName) {
    entityManager = EntityManagerGenerator.getInstance();

    // tbl_menu 테이블에서 menu_code = menuCode인 행을 찾아서 반환
    // (중요) 조회된 Menu Entity 객체는 Persistence context의 1차 캐싱 됨
    //      == 영속 상태
    Menu foundMenu = entityManager.find(Menu.class, menuCode);

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();

    // 1차 캐싱된 foundMenu의 값을 수정
    // -> commit, flush 수행 시, 해당 내용이 DB에 반영됨
    foundMenu.setMenuName(menuName);

    transaction.commit();

    return foundMenu;
  }

  /* 4. 특정 메뉴 코드로 메뉴 삭제하는 기능 */
  public Long removeAndReturnAllCount(int menuCode) {

    entityManager = EntityManagerGenerator.getInstance();
    Menu foundMenu = entityManager.find(Menu.class, menuCode);

    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();

    entityManager.remove(foundMenu);

    transaction.commit();

    return getCount(entityManager);
  }
}
