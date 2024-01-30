package com.jh.jdbc.board.controller;

import com.jh.jdbc.board.container.Container;
import com.jh.jdbc.board.dto.Member;
import com.jh.jdbc.board.service.MemberService;
import com.jh.jdbc.board.util.MysqlUtil;
import com.jh.jdbc.board.util.SecSql;

public class MemberController {
  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
  }

  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("== 회원 가입 ==");
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = Container.in.nextLine().trim();

      if (loginId.length() == 0) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }


      boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

      if (isLoginIdDup) {
        System.out.printf("\"%s\"(은)는 이미 사용중인 아이디 입니다.\n", loginId);
        continue;
      }

      break;
    }


    while (true) {
      System.out.printf("로그인 비밀번호 : ");
      loginPw = Container.in.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }
      boolean loginPwConfirmIsSame = true;

      while (true) {
        System.out.printf("로그인 비밀번호 확인 : ");
        loginPwConfirm = Container.in.nextLine().trim();

        if (loginPw.length() == 0) {
          System.out.println("비밀번호 확인을 입력해주세요.");
          continue;
        }
        if (!loginPw.equals(loginPwConfirm)) {
          System.out.println("로그인 비밀번호가 일치하지 않습니다.");
          System.out.println("확인 후 다시 입력해주세요.");

          loginPwConfirmIsSame = false;
          break;
        }
        break;
      }
      //로그인 비번과 비번확인이 일치한다면 제대로 입력된 것을 간주한다.
      if (loginPwConfirmIsSame) {
        break;
      }
    }
    //이름 입력
    while (true) {
      System.out.printf("이름 : ");
      name = Container.in.nextLine().trim();

      if (name.length() == 0) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }
      break;
    }


    memberService.join(loginId, loginPw, name);

    System.out.printf("\"%s\"님 환영합니다!\n", name);

  }


  public void login() {
    String loginId;
    String loginPw;

    System.out.println("== 로그인 ==");
    System.out.printf("로그인 아이디 : ");
    loginId = Container.in.nextLine().trim();

    if (loginId.length() == 0) {
      System.out.println("아이디를 입력해주세요");
      return;
    }

    Member member = memberService.getMemberByLoginId(loginId);

    if (member == null) {
      System.out.println("존재하지 않은 아이디입니다.");
      return;
    }

    int tryMaxCount = 3;
    int tryCount = 0;
    while (true) {
      if (tryCount == tryMaxCount) {
        System.out.println("비밀번호 확인 후 다음에 다시 입력해주새요.");
        break;
      }
      System.out.printf("로그인 비밀번호 : ");
      loginPw = Container.in.nextLine().trim();
      if (loginPw.length() == 0 || member.getLoginPw().equals(loginPw) == false) {
        System.out.println("비밀번호가 일치하지 않습니다.");
        tryCount++;
        continue;
      }
      System.out.printf("\"%s\"님 환영합니다.\n", member.getName());
      break;
    }
  }
}

