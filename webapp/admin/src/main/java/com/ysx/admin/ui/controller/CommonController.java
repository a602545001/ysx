package com.ysx.admin.ui.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ysx.admin.ui.People;
import com.ysx.admin.ui.PeopleRepository;
import com.ysx.admin.ui.RedisRepository;
import com.ysx.admin.ui.User;
//import com.ysx.base.model.repository.BlogRepository;
//import com.ysx.base.model.entity.Blog;

@Controller
public class CommonController {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private PeopleRepository peopleRepository;
	
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public ModelAndView rotate(){
		Blog bog= blogRepository.findById(1);
		Map d =new HashMap();
        // 保存字符串
		return new ModelAndView("common/edit","blog",bog);
	}
	
	@ResponseBody
	@RequestMapping(value = "/date",method = RequestMethod.GET)
	public String test(){
		Blog bog= blogRepository.findById(1);
        // 保存字符串
		return bog.getContent();
	}
	
	@ResponseBody
	@RequestMapping(value = "/addpeople/{name}")
	public String add(@PathVariable String name){
		People people = new People();
		people.setName(name);
		peopleRepository.save(people);
        // 保存字符串
		return "1";
	}

}

