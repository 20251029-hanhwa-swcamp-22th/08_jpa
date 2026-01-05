package com.wjdqudwls.springdatajpa.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

  @RequestMapping({"/", "/main"})
  public String mainPage() {

    return "main/main";
  }

  /**
   * queryMethod 페이지 조회
   * - 반환형이 void인 경우
   *   요청 주소 "menu/queryMethod" 로 forward
   */
  @GetMapping("/querymethod")
  public void queryMethodPage(){}
}