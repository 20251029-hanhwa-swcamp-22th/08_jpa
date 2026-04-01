package com.google.mapping.section01.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "entityMember") // db테이블과 매핑하는 객체로 만듬
@Table(name = "tbl_member") // tbl 멤버와 매핑할거다!
@TableGenerator( // 테이블을 이용해서 기본키(PK) 생성
    name = "member_seq_tbl_generator",
    table = "tbl_my_sequences",
    pkColumnValue = "my_seq_member_no"
)
@Access(AccessType.FIELD) // JPA Entity의 모든 필드에 접근하는 방식을 FIELD로 지정해서 직접 접근한다. -> 기본 값
public class Member {

    @Id
    @Column(name = "member_no")
    // @GeneratedValue(strategy = GenerationType.IDENTITY) -> Auto Increment 전략에서
    @GeneratedValue( // 테이블 전략으로 바꾸겠당
        strategy = GenerationType.TABLE,
        generator = "member_seq_tbl_generator"
    )
    private int memberNo;

    @Column(
	    name = "member_id",
      unique = true,
	    nullable = false,
      columnDefinition = "varchar(10)"
    )
    private String memberId;

    @Column(name = "member_pwd", nullable = false)
    private String memberPwd;

    @Column(name = "member_name")
    private String memberName;

		@Transient // 테이블에 매핑되지 않음!! , 비영속
    @Column(name = "phone")
    private String phone;

    @Column(name = "address", length = 900)
    private String address;

    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(name = "status", columnDefinition = "char(1) default 'Y'")
    private String status;

  // Entity 클래스는 기본생성자 필수이다!
    protected Member() {}

    public Member(
	    String memberId, String memberPwd, String memberName, 
	    String phone, String address, LocalDateTime enrollDate, 
	    MemberRole memberRole, String status
	  ) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.phone = phone;
        this.address = address;
        this.enrollDate = enrollDate;
        this.memberRole = memberRole;
        this.status = status;
    }

  // 해당 값(memberName)의 접근 방식만 getter로 변경
  @Access(AccessType.PROPERTY) // 값에 접근해서 부가적인 기능을 추가하고 싶을 때 사용
  public String getMemberName() {
    System.out.println("getMemberName()을 이용한 Access 확인");

    return memberName + "님";
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

}