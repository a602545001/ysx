package com.ysx.fish.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ysx.fish.ui.common.User;

@Controller
public class CommonController {

	@RequestMapping(value = "/plane",method = RequestMethod.GET)
	public ModelAndView plane(){
		return new ModelAndView("common/plane");
	}
	
	@RequestMapping(value = "/fish",method = RequestMethod.GET)
	public ModelAndView fish(){
		return new ModelAndView("common/fish");
	}
	
	@RequestMapping(value = "/tree",method = RequestMethod.GET)
	public ModelAndView tree(){
//		User user=new User();
//		return new ModelAndView("common/tree","user",user);
		return new ModelAndView("common/tree");
	}
	
	@RequestMapping(value = "/rotate",method = RequestMethod.GET)
	public ModelAndView rotate(){
		return new ModelAndView("common/rotate");
	}
	
	@RequestMapping(value = "/book",method = RequestMethod.GET)
	public ModelAndView book(){
		return new ModelAndView("common/book");
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView login(){
		return new ModelAndView("common/login");
	}
}

