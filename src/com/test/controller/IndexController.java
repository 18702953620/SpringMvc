package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.mapper.UserDao;

@Controller
@RequestMapping("")
public class IndexController extends BaseController {

	@Autowired
	UserDao userdao;



	@RequestMapping("/index")
	public String hello() {
		return "index";
	}

	@RequestMapping("")
	public String index() {
		return "login";
	}

}
