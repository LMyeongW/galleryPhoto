package com.photo.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.photo.demo.vo.MemberVO;

public class GalleryPageInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO membervo = (MemberVO)session.getAttribute("member");
		
		if(membervo == null || membervo.getGalleryId() == null) {
			response.sendRedirect("/account/login"); 
			return false;
		} else {
			return true;
		}
	}
	
}
