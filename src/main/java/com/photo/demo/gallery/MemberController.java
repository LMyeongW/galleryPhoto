package com.photo.demo.gallery;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.photo.demo.gallery.service.MemberService;
import com.photo.demo.vo.MemberVO;

@Controller
@RequestMapping("/account")
public class MemberController {
	
	@Autowired
	private MemberService memberservice;
	
    @Autowired
    private BCryptPasswordEncoder pwEncoder;
    
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//회원가입 페이지
	@RequestMapping(value ="/join", method = RequestMethod.GET)
	public ModelAndView joinGET() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/account/join");
		
		return mav;
	}
	
	//회원가입 실행
	@RequestMapping(value ="join.do", method = RequestMethod.POST)
	public ModelAndView joinPOST(@ModelAttribute("membervo")MemberVO membervo) {
		ModelAndView mav = new ModelAndView();
		
		String rawPw = "";
		String encodePw = "";
		
		rawPw = membervo.getGalleryPw();
		encodePw = pwEncoder.encode(rawPw);
		membervo.setGalleryPw(encodePw);
		System.out.println(membervo);
		
		memberservice.joinPOST(membervo);
		
		mav.setViewName("jsonView");
		
		return mav;
	}
	
	//아이디중복체크
	@RequestMapping(value="/galleryId", method = RequestMethod.GET)
	@ResponseBody
	public String employeeIdCk(String galleryId)throws Exception{
		
		int result = memberservice.idCheck(galleryId);
		
		System.out.println(result);
		
		if(result != 0) {
			return "fail"; //중복o
		} else {
			return "success"; //중복x
		}
		
		
	}	
	
	//로그인 페이지
	@RequestMapping(value ="/login", method = RequestMethod.GET)
	public ModelAndView loginGET() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/account/login");
		
		return mav;
	}
	
	@RequestMapping(value ="/login.do", method = RequestMethod.POST)
	public String loginPOST(HttpServletRequest request, MemberVO membervo, RedirectAttributes rttr) {
		
		System.out.println("전달된 데이터 : " + membervo);
		
		HttpSession session = request.getSession();
		String rawPw = "";
		String encodePw = "";
		
		MemberVO memvo = memberservice.loginPOST(membervo);
		
		System.out.println("담긴 데이터  : " + memvo);
		

		if(memvo != null) {
			
			rawPw = membervo.getGalleryPw();       
			encodePw = memvo.getGalleryPw(); 
			
			if(true == pwEncoder.matches(rawPw, encodePw)) {
				
				memvo.setGalleryPw("");
				session.setAttribute("member", memvo);
				
				return "redirect:/";
			} else {
				rttr.addFlashAttribute("msg", "아이디나 비밀번호를 확인해주세요.");
				return "redirect:/account/login";
			}
			
		}else {
			
			rttr.addFlashAttribute("msg", "존재하지 않는 아이디입니다.");
			rttr.addFlashAttribute("result", 0);
			return "redirect:/account/login";
		}
		

	}
	
    /* 로그아웃 */
    @RequestMapping(value="/logout.do", method=RequestMethod.GET)
    public String logoutMainGET(HttpServletRequest request) throws Exception{
        
        logger.info("logoutMainGET메서드 진입");
        
        HttpSession session = request.getSession();
        
        session.invalidate();
        
        return "redirect:/account/login";        
        
    }
}
