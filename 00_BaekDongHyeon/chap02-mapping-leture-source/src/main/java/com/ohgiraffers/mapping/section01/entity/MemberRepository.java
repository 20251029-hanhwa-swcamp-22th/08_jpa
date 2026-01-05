package com.ohgiraffers.mapping.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


/*
* @Repository : Bean 등록 + DB 연결과 관련된 역할 명시
*
* @PersistenceContxet
*   - Spring Boot가 실행될 때
*     application.yml 파일에 작성된
 *    spring.dataSource , spring.jpa 속성 값을 읽어
 *    EntityManager를 생성 후 Bean으로 등록함
 *
 *  - @PersistenceContext 어노테이션이 작성된 필드에
 *      EmtityManager Bean을 얻어와 의존성 주입
 * 엔티티 매니저는 요청당 한개씩 작성됨
 *
 */

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Member member) {
        entityManager.persist(member);
    }
    public String findNameById(String memberId) {
        String jpql
                = "SELECT m.memberName FROM entityMember m "
                + "WHERE m.memberId = '" + memberId + "'";
        return entityManager.createQuery(jpql, String.class).getSingleResult();
    }
}