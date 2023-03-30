package com.photo.demo.gallery.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.photo.demo.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{
	
	@Autowired 
	private SqlSession sql;
	
	private static String namespace="account";
	
	//회원가입
	@Override
	public void joinPOST(MemberVO membervo) {
		sql.insert(namespace+".join", membervo);
	}
	
	//아이디중복체크
	@Override
	public int idCheck(String galleryId) {
		return sql.selectOne(namespace+".idCheck", galleryId);
	}
	
	//로그인
	@Override
	public MemberVO loginPOST(MemberVO membervo) {
		return sql.selectOne(namespace+".login", membervo);
	}
}
