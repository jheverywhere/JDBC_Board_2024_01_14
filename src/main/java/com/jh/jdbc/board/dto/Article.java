package com.jh.jdbc.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {
 private int id;
 private String regDate;
 private String updateDate;
 private String title;
 private String body;



 public Article(Map<String, Object> selectRow) {
  this.id = (int) selectRow.get("id");
  this.regDate = (String) selectRow.get("regDate");
  this.updateDate = (String) selectRow.get("updateDate");
  this.title = (String) selectRow.get("title");
  this.body = (String) selectRow.get("body");

 }

 @Override
 public String toString() {
  return "Article{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", body='" + body + '\'' +
      '}';
 }
}
