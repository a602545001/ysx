package com.ysx.admin.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public ModelAndView rotate(){
		return new ModelAndView("common/edit");
	}
}

