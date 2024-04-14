package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.MemberDTO;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor //생성자 주입을 위해 필요한 어노테이션 //memberService 이 필드를 매개변수로 하는 컨트롤러 생성자를 만들어줌 그러면 MemberController 이 클래스에 대한 객체를 등록할 때 자동적으로 서비스 클래스에 대한 객체를 주입 받음
@RequestMapping("/member/*")
public class MemberController {
	//생성자 주입  => 주입을 받는다는 것은 컨트롤러 클래스가 서비스 클래스의 자원인 메서드나 필드를 사용할 수 있는 권한이 생기는 것임
	private final MemberService memberService; // 객체를 주입 받음 
	
	//회원가입 페이지 출력 요청
	@GetMapping("join")
	public String joinForm() {
		return "member/join"; 
	}
	
	// form으로 입력 받은 회원가입 값을 DB에 입력
	@PostMapping("join")
	public String join(@ModelAttribute MemberDTO memberDTO) { //@ModelAttribute는 생략이 가능함
		memberService.join(memberDTO);
		return "member/login";
	}

	//@RequestParam(태그 속 name 값) String memberEmail => @RequestParam은 html name값을 오른쪽 변수에 옮겨 닮는다는 뜻
//	public String join(@RequestParam("memberEmail") String memberEmail,
//						@RequestParam("memberPassword") String memberPassword,
//						@RequestParam("memberName") String memberName) { 
//		return "member/join";
//	}
	
	@GetMapping("login")
	public String loginForm() {
		return "member/login";
	}
	
	//로그인
	@PostMapping("login")
	public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
		MemberDTO loginResult = memberService.login(memberDTO);
		if(loginResult != null) {
			// 로그인 성공
			session.setAttribute("loginEmail", loginResult.getMemberEmail());
			return "member/main";			
		} else {
			//로그인 실패
			return "member/login";
		}
	}
	
	
	//회원목록
	@GetMapping("list")
	public String findAll(Model model) {
		// member는 한 개가 아니므로 List사용
		List<MemberDTO> memberDTOList = memberService.findAll();
		// html로 가져갈 데이터가 있다면 model 사용 => 데이터를 담아서 view로 가져가는 역할
		model.addAttribute("memberList", memberDTOList);
		return "member/list";
	}
	
}
