package com.jh.jdbc.board;


import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int id = 0;


    System.out.println("=== JDBC게시판 시작 ===");
    while(true){
      System.out.printf("명령) ");
      String cmd = in.nextLine();
      if(cmd.equals("/usr/article/write")){
        System.out.printf("제목 : ");
        String title = in.nextLine();
        System.out.printf("내용 : ");
        String body = in.nextLine();
       id++;
        System.out.printf("%d번 게시물이 등록되었습니다.\n",id);
      }else if(cmd.equals("exit")){
        System.out.println("프로그램을 종료합니다.");
        break;
      }

    }





  }
}