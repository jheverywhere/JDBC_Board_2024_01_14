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
 private int memberId;
 private String title;
 private String body;
 private String extra__writeName;


 public Article(Map<String, Object> selectRow) {
  this.id = (int) selectRow.get("id");
  this.regDate = (String) selectRow.get("regDate");
  this.updateDate = (String) selectRow.get("updateDate");
  this.memberId = (int) selectRow.get("memberId");
  this.title = (String) selectRow.get("title");
  this.body = (String) selectRow.get("body");

  if(selectRow.get("extra__writeName") != null){
   this.extra__writeName = (String)selectRow.get("extra__writeName");
  }

 }


}
