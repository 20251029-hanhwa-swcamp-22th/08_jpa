package com.my.section03.entity;

import com.my.section01.entitymanager.EntityManagerFactoryGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerGenerator {

  private static EntityManagerFactory factory
    = Persistence.createEntityManagerFactory("jpatest");

  public static EntityManager getInstance(){

    // EntityManager 객체를 생성하여 반환
    // -> 요청마다 새로운 EntityManager객체가 생성됨
    return factory.createEntityManager();
  }
}
