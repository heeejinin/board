package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	@GetMapping("/") //주소가 없음 대신 home.html을 넘김
	public String home() {
		return "home";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	

}
