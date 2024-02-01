package com.jh.jdbc.board.controller;

import com.jh.jdbc.board.Rq;
import com.jh.jdbc.board.container.Container;
import com.jh.jdbc.board.dto.Article;
import com.jh.jdbc.board.service.ArticleService;

import java.util.List;

public class ArticleController {

  private ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
  }

  public void write() {

    if(Container.session.isLogined() == false){
      System.out.println("로그인 후에 이용해주세요.");
      return;
    }
    int memberId = Container.session.loginedMemberId;

    System.out.println("== 게시물 작성 ==");
    System.out.printf("제목 : ");
    String title = Container.in.nextLine();

    System.out.printf("내용 : ");
    String body = Container.in.nextLine();


    int id = articleService.write(memberId,title, body);


    System.out.printf("%d번 게시물이 작성되었습니다.\n", id);

  }

  public void list(Rq rq) {
    int page = rq.getIntParam("page",1);
    String searchKeyword = rq.getParam("searchKeyword","");

    System.out.println("== 게시물 리스트 ==");
    // 한 페이지에 보여줄 게시물 개수
    int pageItemCount = 10;




    List<Article> articles = articleService.getForPrintArticles(page,pageItemCount,searchKeyword);


    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("번호 / 작성날짜 / 제목 / 작성 / 조회수 ");
    for (Article article : articles) {
      System.out.printf("%d / %s / %s / %s / %d\n",article.getId(),article.getRegDate(),article.getTitle(),article.getExtra__writeName(),article.getHit());
    }
  }

  public void detail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    articleService.increaseHit(id);
    Article article = articleService.getForPrintArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }



    System.out.println("==== 게시물 상세보기 ====");
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성날짜 : %s\n", article.getRegDate());
    System.out.printf("수정날짜 : %s\n", article.getUpdateDate());
    System.out.printf("작성자 : %s\n", article.getExtra__writeName());
    System.out.printf("조회수 : %s\n", article.getHit());
    System.out.printf("제목 : %s\n", article.getTitle());
    System.out.printf("내용 : %s\n", article.getBody());

  }

  public void modify(Rq rq) {
    if(Container.session.isLogined() == false){
      System.out.println("로그인 후에 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.getForPrintArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != Container.session.loginedMemberId){
      System.out.println("수정 권한이 없습니다.");
      return;
    }


    System.out.printf("새 제목 : ");
    String title = Container.in.nextLine();
    System.out.printf("새 내용 : ");
    String body = Container.in.nextLine();


    articleService.update(id, title, body);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

  }

  public void delete(Rq rq) {

    if(Container.session.isLogined() == false){
      System.out.println("로그인 후에 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }


    Article article = articleService.getForPrintArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != Container.session.loginedMemberId){
      System.out.println("삭제 권한이 없습니다.");
      return;
    }



    articleService.delete(id);


    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

  }
}
