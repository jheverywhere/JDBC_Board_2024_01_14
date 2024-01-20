package com.jh.jdbc.test;


import com.jh.jdbc.board.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {
    // JDBC 드라이버 클래스 이름
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";

    // 데이터베이스 연결 정보
    String url = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String username = "jheverywhere";
    String password = "jh960525";

    Connection conn = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;

    List<Article>articles = new ArrayList<>();

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
  }
}
