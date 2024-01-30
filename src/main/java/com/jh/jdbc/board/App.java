package com.jh.jdbc.board;

import com.jh.jdbc.board.container.Container;
import com.jh.jdbc.board.controller.ArticleController;
import com.jh.jdbc.board.controller.MemberController;
import com.jh.jdbc.board.dto.Article;
import com.jh.jdbc.board.util.MysqlUtil;
import com.jh.jdbc.board.util.SecSql;

import java.util.*;

public class App {


  List<Article> articles;

  public App() {

    articles = new ArrayList<>();
  }

  private static boolean isDevMode() {
    return true;
  }

  public void run() {

    System.out.println("=== JDBC게시판 시작 ===");

    Scanner in = new Scanner(System.in);

    try {

      while (true) {
        System.out.printf("명령) ");
        String cmd = in.nextLine();
        Rq rq = new Rq(cmd);

        //DB 세팅
        MysqlUtil.setDBInfo("localhost", "jheverywhere", "jh960525", "text_board");
        MysqlUtil.setDevMode(isDevMode());

        //명령 로직 실행
        action(rq);
      }
    } finally {
      in.close();
    }
  }


  private void action(Rq rq) {
    MemberController memberController = Container.memberController;
    ArticleController articleController = Container.articleController;

    if (rq.getUrlPath().equals("/usr/article/write")) {
      articleController.write();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      articleController.list();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      articleController.detail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      articleController.modify(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      articleController.delete(rq);
    } else if (rq.getUrlPath().equals("/usr/member/join")) {
      memberController.join();
    } else if (rq.getUrlPath().equals("/usr/member/login")) {
      memberController.login();
    } else if (rq.getUrlPath().equals("/usr/member/logout")) {
      memberController.logout();
    } else if(rq.getUrlPath().equals("/usr/member/whoami")){
      memberController.whoami();
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("== 프로그램을 종료합니다 ==");
      System.exit(0);
    } else {
      System.out.println("명령어를 잘못 입력하셨습니다.");
    }
  }
}