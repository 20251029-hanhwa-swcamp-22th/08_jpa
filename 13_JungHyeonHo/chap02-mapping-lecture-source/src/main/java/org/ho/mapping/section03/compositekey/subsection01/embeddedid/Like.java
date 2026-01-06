package org.ho.mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "tbl_like")
public class Like implements Serializable {

    @EmbeddedId // 임베드 시킨 LikeCompositeKey의 필드를 PK(복합키)로 사용
    private LikeCompositeKey likeInfo;

    protected Like() {}

    public Like(LikeCompositeKey likeInfo) {
        this.likeInfo = likeInfo;
    }

    @Override
    public String toString() {
        return "Like{" +
                "likeInfo=" + likeInfo +
                '}';
    }
}