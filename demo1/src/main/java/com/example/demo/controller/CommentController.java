package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.CommentDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment/")
public class CommentController {
	
	//댓글 작성 내용 받아오는 부분
	@PostMapping("/save") //ajax 요청이라 @ResponseBody가 필요함
	public @ResponseBody String save(@ModelAttribute CommentDTO commentDTO) {
		System.out.println("commentDTO = " + commentDTO);
		return "요청 성공";
	}
}
