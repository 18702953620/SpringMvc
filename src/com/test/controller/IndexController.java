package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController extends BaseController {

	@RequestMapping("/index")
	public String hello() {
		return "index";
	}

	@RequestMapping("")
	public String index() {
		return "login";
	}

}
