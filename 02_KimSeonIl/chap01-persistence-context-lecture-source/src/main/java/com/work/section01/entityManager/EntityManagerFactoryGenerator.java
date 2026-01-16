package com.work.section01.entityManager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryGenerator {

  private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpatest");
  //생성자를 private으로 작성
  // -> 외부에서 해당 객체를 만들수 없게 함
  private EntityManagerFactoryGenerator(){}

  //만들어 놓은 factory 객체만 하나만 얻어갈수 있게 한다 == 싱글톤 패턴
  public static EntityManagerFactory getInstance(){
    return factory;
  }


}
