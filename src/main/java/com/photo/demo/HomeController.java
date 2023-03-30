package com.photo.demo;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController {
	
	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value ="/interceptorTest")
	public ModelAndView interceptorTest()throws Exception {
		ModelAndView mav = new ModelAndView();
		
		log.debug("인터셉터 테스트");

		return mav;
	}
}
