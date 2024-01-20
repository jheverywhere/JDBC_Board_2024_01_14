package com.jh.jdbc.board;

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
      if(cmd.equals("/usr/article/write")){
        System.out.printf("제목 : ");
        String title = in.nextLine();
        System.out.printf("내용 : ");
        String body = in.nextLine();

        int id = ++articleLastId;
        articles.add(new Article(id,title,body));

        System.out.println("입력된 게시물 : " + articles.get(id-1));
        System.out.printf("%d번 게시물이 등록되었습니다.\n",id);
      }else if(cmd.equals("exit")){
        System.out.println("프로그램을 종료합니다.");
        break;
      }else if(cmd.equals("/usr/article/list")){

        if(articles.isEmpty()){
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }
        System.out.println("===게시물 리스트===");
        System.out.println("번호 // 제목 // 내용");

        /* 오름차순 정렬
        for(Article article : articles){
          System.out.printf("%d // %s // %s \n",article.id,article.title,article.body);
        }
        */

        //내림차순 정렬
        for (int i = articles.size()-1; i >= 0; i--) {
          Article article = articles.get(i);
          System.out.printf("%d // %s // %s \n",article.id , article.title,article.body);
        }


      }
      else{
        System.out.println("명령어를 잘못 입력하셨습니다. 다시 입력해주세요.");
      }

    }
  }
}
