package com.mycompany.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryGenerator {

  // persistence.xml 파일의 <persistence-unit name="jpatest"> 이름과 같게 함
  private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpatest");

  // 생성자를 private로 생성
  // -> 외부에서 해당 객체를 만들 수 없게 함.
  private EntityManagerFactoryGenerator() {
  }

  // 만들어 놓은 factory 객체 하나만 얻어갈 수 있게 함
  // singleton pattern 구현
  public static EntityManagerFactory getInstance() {
    return factory;
  }
}
