package com.jh.jdbc.board.session;

import com.jh.jdbc.board.dto.Member;

public class Session {
  public int loginedMemberId;
  public Member loginedMember;

  public Session(){
    loginedMemberId=-1;
  }
}
