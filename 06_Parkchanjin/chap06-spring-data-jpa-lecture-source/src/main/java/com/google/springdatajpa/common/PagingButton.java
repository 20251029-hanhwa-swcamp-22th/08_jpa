package com.google.springdatajpa.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PagingButton {
  private int currentPage; // 현재 페이지
  private int startPage; // 시작번호
  private int endPage; // 끝번호
}