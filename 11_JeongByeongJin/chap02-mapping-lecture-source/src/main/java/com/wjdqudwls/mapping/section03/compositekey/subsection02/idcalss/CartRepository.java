package com.wjdqudwls.mapping.section03.compositekey.subsection02.idcalss;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Cart cart) {
        entityManager.persist(cart);
    }
}