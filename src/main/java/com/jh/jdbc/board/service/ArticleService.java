package com.jh.jdbc.board.service;

import com.jh.jdbc.board.dto.Article;
import com.jh.jdbc.board.repository.ArticleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleRepository articleRepository;
  public ArticleService(){
    articleRepository = new ArticleRepository();
  }
  public int write(int memberId, String title, String body) {
    return articleRepository.wrtie(memberId,title,body);
  }
  public List<Article> getForPrintArticles(int page, int pageItemCount, String searchKeyword){
   int limitFrom = (page - 1) * pageItemCount;
   int limitTake = pageItemCount;
    Map<String,Object> args = new HashMap<>();
    args.put("searchKeyword", searchKeyword);
    args.put("limitFrom", limitFrom);
    args.put("limitTake", limitTake);
    return articleRepository.getForPrintArticles(args);
  }

  public Article getForPrintArticleById(int id) {
    return articleRepository.getForPrintArticleById(id);
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


  public void increaseHit(int id) {
    articleRepository.increaseHit(id);
  }
}
