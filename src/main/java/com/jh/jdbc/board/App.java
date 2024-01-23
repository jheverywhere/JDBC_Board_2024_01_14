package com.jh.jdbc.board;

import com.jh.jdbc.board.util.MysqlUtil;
import com.jh.jdbc.board.util.SecSql;

import java.sql.*;
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
        doAction(in, rq);
      }
    } finally {
      in.close();
    }
  }


  private void doAction(Scanner in, Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");
      System.out.printf("제목 : ");
      String title = in.nextLine();

      System.out.printf("내용 : ");
      String body = in.nextLine();


      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);

      int id = MysqlUtil.insert(sql);


      System.out.printf("%d번 게시물이 작성되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      System.out.println("== 게시물 리스트 ==");


      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC;");

      List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

      for (Map<String, Object> articleMap : articleListMap) {
        articles.add(new Article(articleMap));
      }

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      System.out.println("번호 / 제목");
      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      Map<String,Object> articleMap = MysqlUtil.selectRow(sql);

      if(articleMap.isEmpty()){
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
        return;
      }

      Article article = new Article(articleMap);

      System.out.println("==== 게시물 상세보기 ====");
      System.out.printf("번호 : %d\n",article.id);
      System.out.printf("작성날짜 : %s\n",article.regDate);
      System.out.printf("수정날짜 : %s\n",article.updateDate);
      System.out.printf("제목 : %s\n",article.title);
      System.out.printf("내용 : %s\n",article.body);

    }else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }
      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS CNT");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articlesCount = MysqlUtil.selectRowIntValue(sql);

      if(articlesCount == 0){
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
        return;
      }

      System.out.printf("새 제목 : ");
      String title = in.nextLine();
      System.out.printf("새 내용 : ");
      String body = in.nextLine();

      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);
      sql.append("WHERE id = ?", id);

      MysqlUtil.update(sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    }else if (rq.getUrlPath().equals("/usr/article/delete")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }
      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS CNT");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articlesCount = MysqlUtil.selectRowIntValue(sql);

      if(articlesCount == 0){
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
        return;
      }

      sql = new SecSql();
      sql.append("DELETE FROM article");
      sql.append("WHERE id = ?",id);


      MysqlUtil.delete(sql);

      System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
    }else if (rq.getUrlPath().equals("/usr/member/join")) {
      String loginId;
      String loginPw;
      String loginPwConfirm;
      String name;

      System.out.println("== 회원 가입 ==");
      while(true){
        System.out.printf("로그인 아이디 : ");
        loginId = in.nextLine().trim();

        if(loginId.length() == 0){
          System.out.println("로그인 아이디를 입력해주세요.");
          continue;
        }
        break;
      }


      while(true){
        System.out.printf("로그인 비밀번호 : ");
        loginPw = in.nextLine().trim();

        if(loginPw.length() == 0){
          System.out.println("로그인 비밀번호를 입력해주세요.");
          continue;
        }
        boolean loginPwConfirmIsSame = true;

        while(true){
          System.out.printf("로그인 비밀번호 확인 : ");
          loginPwConfirm = in.nextLine().trim();

          if(loginPw.length() == 0){
            System.out.println("비밀번호 확인을 입력해주세요.");
            continue;
          }
          if(!loginPw.equals(loginPwConfirm)){
            System.out.println("로그인 비밀번호가 일치하지 않습니다.");
            System.out.println("확인 후 다시 입력해주세요.");

            loginPwConfirmIsSame = false;
            break;
          }
          break;
        }
        //로그인 비번과 비번확인이 일치한다면 제대로 입력된 것을 간주한다.
        if(loginPwConfirmIsSame){
          break;
        }
      }
      //이름 입력
      while(true){
        System.out.printf("이름 : ");
        name = in.nextLine().trim();

        if(name.length() == 0){
          System.out.println("이름을 입력해주세요.");
          continue;
        }
        break;
      }

      SecSql sql = new SecSql();
      sql.append("INSERT INTO `member`");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", loginId = ?", loginId);
      sql.append(", loginPw = ?", loginPw);
      sql.append(", `name` = ?", name);

      MysqlUtil.insert(sql);

      System.out.printf("\"%s\"님 환영합니다!\n",name);
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("== 프로그램을 종료합니다 ==");
      System.exit(0);
    } else {
      System.out.println("명령어를 잘못 입력하셨습니다.");
    }
  }
}