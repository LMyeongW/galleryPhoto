package com.photo.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
	    System.out.println("LoginInterceptor preHandle 작동");
	        
	    HttpSession session = request.getSession();
	        
	    session.invalidate();
	 
		
		return true;
	}
	
}
