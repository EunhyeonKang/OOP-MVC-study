package service;

import dto.MemberDto;

public interface MemberService {
	public boolean getId(String id);
	public boolean addMember(MemberDto dto);	// 회원가입하기
	public MemberDto login(String id, String pwd); // 로그인하기 
}
