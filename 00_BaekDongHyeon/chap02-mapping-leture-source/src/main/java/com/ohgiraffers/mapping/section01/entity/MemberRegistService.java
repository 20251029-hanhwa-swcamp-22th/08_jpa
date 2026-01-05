package com.ohgiraffers.mapping.section01.entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service // Bean 등록 + 비즈니스 로직 처리 역할
public class MemberRegistService {

    private MemberRepository memberRepository;

    // 생성자 (매개변수 Member Repository는 등록된 Bean이 의존성 주입됨)
    public MemberRegistService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional // 선언적 트랜잭션 (오류없으면 영속, 오류나면 롤백or커밋)
    public void registMember(MemberRegistDTO newMember) {
        Member member = new Member(
                newMember.getMemberId(),
                newMember.getMemberPwd(),
                newMember.getMemberName(),
                newMember.getPhone(),
                newMember.getAddress(),
                newMember.getEnrollDate(),
                newMember.getMemberRole(),
                newMember.getStatus()
        );

        memberRepository.save(member);
    }
    @Transactional(
            isolation = Isolation.DEFAULT
    )

    public String registMemberAndFindName(MemberRegistDTO newMember) {
        registMember(newMember);
        return memberRepository.findNameById(newMember.getMemberId());
    }
}