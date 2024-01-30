package com.jh.jdbc.board.session;

import com.jh.jdbc.board.dto.Member;

public class Session {
  public int loginedMemberId;
  public Member loginedMember;

  public Session(){
    loginedMemberId=-1;
  }

  public boolean isLogined() {
    return loginedMemberId != -1;
  }

  public void login(Member member) {
    loginedMemberId= member.getId();
    loginedMember = null;
  }

  public boolean isLogout(){
    return loginedMemberId == -1;
  }
  public void logout() {
    loginedMemberId=-1;
    loginedMember = null;
  }
}
