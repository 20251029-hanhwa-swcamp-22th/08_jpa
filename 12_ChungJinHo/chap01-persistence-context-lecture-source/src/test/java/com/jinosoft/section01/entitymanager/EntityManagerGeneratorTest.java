package com.jinosoft.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerGeneratorTest {

  @Test
  @DisplayName("엔티티 매니저 팩토리 생성 확인")
  void testGenerateEntityManagerFactory() {
    // given

    // when
    EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

    // then
    assertNotNull(factory);
  }

  @Test
  @DisplayName("엔티티 매니저 팩토리 싱글톤 인스턴스 확인")
  void testIsEntityManagerFactorySingletonInstance(){
    // given

    // when
    EntityManagerFactory factory1 = EntityManagerFactoryGenerator.getInstance();
    EntityManagerFactory factory2 = EntityManagerFactoryGenerator.getInstance();

    // then
    // 서로 같은 객체를 참조하는지 확인
    assertEquals(factory1, factory2);
    assertEquals(factory1.hashCode(), factory2.hashCode());
  }

  @Test
  @DisplayName("엔티티 매니저 생성 확인")
  void testGenerateEntityManager() {
    // given

    // when
    EntityManager entityManager
        = EntityManagerGenerator.getInstance();

    // then
    assertNotNull(entityManager);

    // EntityMAnager는 JVM 외부에 메모리가 할당되기 때문에
    // 메모리 반환을 해야함
    entityManager.close();
  }

  @Test
  @DisplayName("엔티티 매니저 스코프 확인")
  void testEntityManagerLifeCycle() {
    // given

    // when
    EntityManager entityManager1
        = EntityManagerGenerator.getInstance();
    EntityManager entityManager2
        = EntityManagerGenerator.getInstance();

    // then
    // 서로 다른 객체를 참조하는지 확인
    assertNotEquals(entityManager1, entityManager2);
    assertNotEquals(entityManager1.hashCode(), entityManager2.hashCode());

    entityManager1.close();
    entityManager2.close();
  }

}