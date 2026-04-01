package com.google.mapping.section01.entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service // Bean 등록 + 비즈니스 로직 처리 역할
public class MemberRegistService {

    private MemberRepository memberRepository;

    // 생성자(매개변수 MemberRepository는 등록된 Bean이 의존성 주입 해줌)
    public MemberRegistService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional // 선언적 트랜잭션(예외 발생 시 rollback, 정상수행 시 commint)
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
      isolation = Isolation.DEFAULT, // 해당 메서드의 격리수준을 기본으로 하겠다!
      propagation = Propagation.REQUIRED, // 트랜잭션이 없으면 만들고 있으면 합류해라
      rollbackFor = Exception.class
  )
  public String registMemberAndFindName(MemberRegistDTO newMember) {
    registMember(newMember);
    return memberRepository.findNameById(newMember.getMemberId());
  }
}