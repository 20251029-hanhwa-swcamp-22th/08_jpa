package org.ho.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class EntityManagerFactoryGenerator {
  // argument로 넣는 persistenceUnitName은 xml의 persistence-unit name과 똑같이 적어줘야한다. 식별해야 됨.
  private static EntityManagerFactory factory
      = Persistence.createEntityManagerFactory("jpatest");

  // 생성자를 private으로 작성
  // -> 외부에서 해당 객체를 만들 수 없게 함
  private EntityManagerFactoryGenerator() {}

  // 만들어 놓은 factory 객체 하나만 얻어갈 수 있게 함
  // == SingleTon 패턴
  public static EntityManagerFactory getInstance(){
    return factory;
  }
}
