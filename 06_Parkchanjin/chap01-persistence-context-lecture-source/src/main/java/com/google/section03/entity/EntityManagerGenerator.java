package com.google.section03.entity;

import com.google.section01.entitymanager.EntityManagerFactoryGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerGenerator {

  private static EntityManagerFactory factory
      = Persistence.createEntityManagerFactory("google");

  public static EntityManager getInstance() {

    // EntityManager 객체를 생성하여 반환
    // -> 요청마다 새로운 EntityManager 객체가 생성됨
    return factory.createEntityManager();
  }
}
