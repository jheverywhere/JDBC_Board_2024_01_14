package com.jh.jdbc.board.service;

import com.jh.jdbc.board.repository.ArticleRepository;

import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleRepository articleRepository;
  public ArticleService(){
    articleRepository = new ArticleRepository();
  }
  public int write(String title, String body) {
    return articleRepository.wrtie(title,body);
  }
  public List<Map<String, Object>> getArticlesMap(){
    return articleRepository.getArticlesMap();
  }

  public Map<String, Object> getArticleMap(int id) {
    return articleRepository.getArticleMap(id);
  }

  public int getArticleCount(int id) {
    return articleRepository.getArticleCount(id);
  }

  public void update(int id, String title, String body) {
    articleRepository.update(id,title,body);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }
}
