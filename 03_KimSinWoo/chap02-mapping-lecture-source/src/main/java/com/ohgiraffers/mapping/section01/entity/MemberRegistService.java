package com.ohgiraffers.mapping.section01.entity;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberRegistService {

    private MemberRepository memberRepository;

    // Argument MemberRepository는 등록된 Bean이 의존성 주입이 된다
    public MemberRegistService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional // 선언적 트랜잭션 (예외 발생시 rollback, 아니면 commit)
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
            isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String registMemberAndFindName(MemberRegistDTO newMember) {
        registMember(newMember);
        return memberRepository.findNameById(newMember.getMemberId());
    }
}