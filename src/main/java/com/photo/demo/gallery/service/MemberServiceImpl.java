package com.photo.demo.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photo.demo.gallery.dao.MemberDAO;
import com.photo.demo.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired 
	private MemberDAO memberDao;
	
	//회원가입
	@Override
	public void joinPOST(MemberVO membervo) {
		memberDao.joinPOST(membervo);
	}
	
	//아이디중복체크
	@Override
	public int idCheck(String galleryId) {
		return memberDao.idCheck(galleryId);
	}
	
	//로그인
	@Override
	public MemberVO loginPOST(MemberVO membervo) {
		return memberDao.loginPOST(membervo);
	}
}
