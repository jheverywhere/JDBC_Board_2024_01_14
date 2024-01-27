package com.jh.jdbc.board.container;

import com.jh.jdbc.board.controller.ArticleController;
import com.jh.jdbc.board.controller.MemberController;
import com.jh.jdbc.board.repository.ArticleRepository;
import com.jh.jdbc.board.repository.MemberRepository;
import com.jh.jdbc.board.service.ArticleService;
import com.jh.jdbc.board.service.MemberService;

import java.util.Scanner;

public class Container {
  public static Scanner in;
  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ArticleService articleService;

  public static MemberController memberController;
  public static ArticleController articleController;

  static{
    in = new Scanner(System.in);

    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();
    articleService = new ArticleService();
    memberService = new MemberService();
    memberController = new MemberController();
    articleController = new ArticleController();

  }


}
