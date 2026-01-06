package org.ho.section03.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ho.section01.entitymanager.EntityManagerFactoryGenerator;

public class EntityManagerGenerator {
  private static EntityManagerFactory factory
      = Persistence.createEntityManagerFactory("jpatest");

  public static EntityManager getInstance(){
    return factory.createEntityManager();
  }
}
