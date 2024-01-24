package com.jh.jdbc.board.controller;

import com.jh.jdbc.board.Rq;
import com.jh.jdbc.board.container.Container;
import com.jh.jdbc.board.dto.Article;
import com.jh.jdbc.board.util.MysqlUtil;
import com.jh.jdbc.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleController {
private List<Article>articles ;


public ArticleController(){
  articles = new ArrayList<>();
}
  public void write() {
    System.out.println("== 게시물 작성 ==");
    System.out.printf("제목 : ");
    String title = Container.in.nextLine();

    System.out.printf("내용 : ");
    String body = Container.in.nextLine();


    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);

    int id = MysqlUtil.insert(sql);


    System.out.printf("%d번 게시물이 작성되었습니다.\n", id);

  }

  public void list() {
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
  }

  public void detail(Rq rq) {
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


  }

  public void modify(Rq rq) {
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
    String title = Container.in.nextLine();
    System.out.printf("새 내용 : ");
    String body = Container.in.nextLine();

    sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

  }

  public void delete(Rq rq) {
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

  }
}
