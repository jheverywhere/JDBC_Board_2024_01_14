package com.jh.jdbc.board.repository;

import com.jh.jdbc.board.dto.Article;
import com.jh.jdbc.board.util.MysqlUtil;
import com.jh.jdbc.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int wrtie(int memberId, String title, String body) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);

    int id = MysqlUtil.insert(sql);

    return id;
  }

  public  List<Article> getForPrintArticles() {
    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writeName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("on A.memberId = M.id");
    sql.append("ORDER BY id DESC;");

    List<Map<String,Object>> selectRows = MysqlUtil.selectRows(sql);
    List<Article> articles = new ArrayList<>();

    for (Map<String, Object> selectRow : selectRows) {
      articles.add(new Article(selectRow));
    }




    return articles;
  }

  public Article getForPrintArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> selectRow = MysqlUtil.selectRow(sql);
    if (selectRow.isEmpty()) {
      return null;
    }

    return new Article(selectRow);
  }

  public int getArticleCount(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS CNT");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return MysqlUtil.selectRowIntValue(sql);
  }

  public void update(int id, String title, String body) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?",id);

    MysqlUtil.delete(sql);
  }


}
