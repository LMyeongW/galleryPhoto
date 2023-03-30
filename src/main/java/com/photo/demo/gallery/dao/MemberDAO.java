package com.photo.demo.gallery.dao;

import com.photo.demo.vo.MemberVO;

public interface MemberDAO {
	
	//회원가입
	void joinPOST(MemberVO membervo);
	
	//아이디중복체크
	int idCheck(String galleryId);
	
	//로그인
	MemberVO loginPOST(MemberVO membervo);

}
