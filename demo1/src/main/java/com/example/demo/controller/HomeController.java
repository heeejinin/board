package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	@GetMapping("/") //기본주소(/) 요청이 오면 메서드가 호출이 됨 //주소가 없음 대신 home.html을 넘김
	public String home() {
		return "home"; // => templates 폴더의 index.html을 찾아감 (스프링의 뷰 리졸브라는 녀석이 해줌)
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	

}
