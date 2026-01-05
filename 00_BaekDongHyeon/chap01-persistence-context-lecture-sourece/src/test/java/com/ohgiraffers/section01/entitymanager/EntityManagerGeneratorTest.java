package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerGeneratorTest {

    @Test
    @DisplayName("엔티티 매니저 팩토리 생성 확인")
    void testGenerateEntityManagerFactory(){
        EntityManagerFactory factory=EntityManagerFactoryGenerator.getInstance();
        assertNotNull(factory); // 엔티티 맨니저 팩토리 생성화인
    }

    @Test
    @DisplayName("엔티티 매니저 팩토리 싱글톤 햎더확인")
    void testEntityManagerFactorySingleton(){
        // when
        EntityManagerFactory factory1
                = EntityManagerFactoryGenerator.getInstance();
        EntityManagerFactory factory2
                = EntityManagerFactoryGenerator.getInstance();
        // then
assertSame(factory1, factory2); // 두 객체의 참조 주소가 같은지 확인 -> factory1,2 주소가같음
assertEquals(factory1.hashCode(), factory2.hashCode()); // 두 객체의 참조 주소가 같은지 확인
    }
    @Test
    @DisplayName("엔티티 매닞저 생성 확인")
    void testGenerateEntityManager(){
        // when
        // 팩토리 객체를 통해서 새로운 EntityManager 객체를 생성
        EntityManager entityManager=EntityManagerGenerator.getInstance();
        // then
        assertNotNull(entityManager);
        // EntityManager는 JVM 외부에 메모리가 할당되기 때문에
        //사용후 반드시 close() (메모리반환실행)
        entityManager.close();
    }
    @Test
    @DisplayName("엔티티 매니저 스코프 확인")
    void testEntityManagerScope(){
        // when
        EntityManager manager1 = EntityManagerGenerator.getInstance();
        EntityManager manager2 = EntityManagerGenerator.getInstance();
        // then
        // 두객체가 같지않음을 확인 (서로 다른 객체)
        //같지만 독립된
        assertNotEquals(manager1.hashCode(), manager2.hashCode());
        assertNotSame(manager1, manager2);
        manager1.close();
        manager2.close();

    }
}