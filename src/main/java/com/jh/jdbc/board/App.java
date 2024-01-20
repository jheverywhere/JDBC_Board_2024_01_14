package com.jh.jdbc.board;

import java.sql.*;
import java.util.*;

public class App {
  int articleLastId;
  List<Article> articles;

  public App() {

    articleLastId = 0;
    articles = new ArrayList<>();
  }

  public void run(){

    System.out.println("=== JDBC게시판 시작 ===");

    Scanner in = new Scanner(System.in);


    while(true){
      System.out.printf("명령) ");
      String cmd = in.nextLine();
      Rq rq = new Rq(cmd);
      if(rq.getUrlPath().equals("/usr/article/write")){
        System.out.println("===게시물 등록===");
        System.out.printf("제목 : ");
        String title = in.nextLine();
        System.out.printf("내용 : ");
        String body = in.nextLine();

        int id = ++articleLastId;
        // JDBC 드라이버 클래스 이름
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";

        // 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String username = "jheverywhere";
        String password = "jh960525";

        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          // JDBC 드라이버 로드
          Class.forName(jdbcDriver);

          // 데이터베이스에 연결
          conn = DriverManager.getConnection(url, username, password);

          String sql = "insert into article";
          sql+= " SET regDate = NOW()";
          sql+= ", updateDate = now()";
          sql+= ", title = \""+title+"\"";
          sql+= ",`body` = \""+body+"\"";

          pstat = conn.prepareStatement(sql);
          int affectedRows = pstat.executeUpdate();

          System.out.println("affectedRows: " + affectedRows);

        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
        } finally {
          try {
            if (conn != null && !conn.isClosed()) {
              // 연결 닫기
              conn.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }

        articles.add(new Article(id,title,body));

        System.out.printf("%d번 게시물이 등록되었습니다.\n",id);
      }else if(rq.getUrlPath().equals("/usr/article/modify")) {
        int id = rq.getIntParam("id", 0);

        System.out.println("== 게시물 등록 ==");

        System.out.printf("새 제목 : ");
        String title = in.nextLine();
        System.out.printf("새 내용 : ");
        String body = in.nextLine();

        // JDBC 드라이버 클래스 이름
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";

        // 데이터베이스 연결 정보
        String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String username = "jheverywhere";
        String password = "jh960525";

        Connection conn = null;
        PreparedStatement pstat = null;

        String sql = "UPDATE article";
        sql += " SET updateDate = NOW()";
        sql += ", title = \"" + title + "\"";
        sql += ", `body` = \"" + body + "\"";
        sql += " WHERE id = " + id;

        System.out.println("sql : "+sql);

        try {
          // JDBC 드라이버 로드
          Class.forName(jdbcDriver);

          // 데이터베이스에 연결
          conn = DriverManager.getConnection(url, username, password);

          pstat = conn.prepareStatement(sql);

          pstat.executeUpdate();
        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
        } finally {
          try {
            if (conn != null && !conn.isClosed()) {
              // 연결 닫기
              conn.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if(pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }

        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
      }
      else if(rq.getUrlPath().equals("/usr/article/list")){


// JDBC 드라이버 클래스 이름
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";

        // 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String username = "jheverywhere";
        String password = "jh960525";

        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;


        try {
          // JDBC 드라이버 로드
          Class.forName(jdbcDriver);

          // 데이터베이스에 연결
          conn = DriverManager.getConnection(url, username, password);

          String sql = "SELECT *";
          sql += " FROM article";
          sql += " ORDER BY id DESC;";

          pstat = conn.prepareStatement(sql);
          rs = pstat.executeQuery(sql);

          while(rs.next()){
            int id = rs.getInt("id");
            String regDate = rs.getString("regDate");
            String updateDate = rs.getString("updateDate");
            String title = rs.getString("title");
            String body = rs.getString("body");

            Article article = new Article(id,regDate,updateDate,title,body);
            articles.add(article);
          }

          System.out.println("결과 : "+ articles);

        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
        } finally {
          try {
            if (rs != null && !rs.isClosed()) {
              rs.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (conn != null && !conn.isClosed()) {
              // 연결 닫기
              conn.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }

        }

        if(articles.isEmpty()){
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("===게시물 리스트===");
        System.out.println("번호 / 제목");


        for(Article article : articles){
          System.out.printf("%d // %s // %s \n",article.id,article.title,article.body);
        }


        //내림차순 정렬
//        for (int i = articles.size()-1; i >= 0; i--) {
//          Article article = articles.get(i);
//          System.out.printf("%d / %s  \n",article.id , article.title);
//        }


      }else if(rq.getUrlPath().equals("exit")){
        System.out.println("프로그램을 종료합니다.");
        break;
      }
      else{
        System.out.println("명령어를 잘못 입력하셨습니다. 다시 입력해주세요.");
      }

    }
  }
}
