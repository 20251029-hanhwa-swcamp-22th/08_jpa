package com.work.section01.entityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerGenerator {

  public static EntityManager getInstance(){

    // EntityManagerFactoryGenerator 에서 팩토리 객체 얻어오기
    EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

    // EntityManager 객체를 생성해서 반환
    // 요청마다 새로운 EntityManager 객체가 생성
    return factory.createEntityManager();


  }
}
