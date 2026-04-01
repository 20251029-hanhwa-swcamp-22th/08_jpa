package com.google.mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
// LIKE 클래스에 직렬화가 있을 수 있으니 안전장치로 직렬화를 상속받음
public class LikeCompositeKey implements Serializable { // Serializable 직렬화

    @Column(name = "liked_member_no")
    private int likedMemberNo;

    @Column(name = "liked_book_no")
    private int likedBookNo;

    protected LikeCompositeKey() {}

    public LikeCompositeKey(int likedMemberNo, int likedBookNo) {
        this.likedMemberNo = likedMemberNo;
        this.likedBookNo = likedBookNo;
    }

    @Override
    public String toString() {
        return "LikeCompositeKey{" +
                "likedMemberNo=" + likedMemberNo +
                ", likedBookNo=" + likedBookNo +
                '}';
    }
}