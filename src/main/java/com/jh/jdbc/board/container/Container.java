package com.jh.jdbc.board.container;

import com.jh.jdbc.board.controller.ArticleController;
import com.jh.jdbc.board.controller.MemberController;

import java.util.Scanner;

public class Container {
  public static Scanner in;
  public static MemberController memberController;
  public static ArticleController articleController;

  static{
    in = new Scanner(System.in);
    memberController = new MemberController();
    articleController = new ArticleController();
  }


}
