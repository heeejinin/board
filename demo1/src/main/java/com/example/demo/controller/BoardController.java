package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.DTO.CommentDTO;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board/")
public class BoardController {
	// 생성자 주입 방식으로 service의 의존성을 주입 받음
	private final BoardService boardService; 
	private final CommentService commentService;
	
	//작성 폼 가져오기 (href는 get 속성)
	@GetMapping("write") 
	public String writeform() { 
		return "write"; //write.html로 감 
	}
	
	//글 작성하고 홈으로 돌아가기
	@PostMapping("write")
	public String write(@ModelAttribute BoardDTO boardDTO) {//폼에 있는 데이터를 write 매개변수(BoardDTO)에 담음
//		System.out.println("boardDTO = " + boardDTO.getContents()+", "+boardDTO.getTitle()+","+boardDTO.getId());
		boardService.write(boardDTO); 
		return "home"; //home.html로 감
	}
	
	//글 목록
	// DB에서 전체 게시글 데이터를 가져옴 => 서비스에서 리턴 받은걸 모델에 담음 => list.html에 보냄
	@GetMapping("list")
	public String findAll(Model model) {//DB로부터 전체 리스트 불러오기 그럴때 model 객체를 사용함
		//여러 개를 담겠다(List<>사용) 
		List<BoardDTO> boardDTOList = boardService.findAll();
		
		//boardDTOList를 "boardList"라는 이름으로 모델에 추가 => 이렇게 함으로써 뷰에서 해당 이름을 사용하여 데이터에 접근
		model.addAttribute("boardList", boardDTOList); //("attributeName", AttributeValue)
		return "list"; //list.html로 감
	}
	
	//게시판 상세보기
	//아이디를 넘겨받음
	//경로 상의 값을 가져올 때는 @PathVariable을 사용 & 데이터를 담아가야 하니까 model 객체를 사용해줌 
	@GetMapping("{id}")
	public String findbyId(@PathVariable Long id, Model model) {
//		게시판 상세보기는 두가지 고려를 해야함
//		1. 해당 게시글 조회수를 하나 올리고
//		2. 게시글 데이터를 가져와서 detail.html에 출력
//		두번의 호출이 발생한다
		
		boardService.updateHits(id); //호출1: 조회수 처리
		BoardDTO boardDTO = boardService.findById(id); //호출2: 해당 게시글 가져와서 DTO(boardDTO) 객체로 가져옴 => model에 담음 => detail.html로 넘어감
		// 댓글 목록 가져오기
		List<CommentDTO> commentDTOList = commentService.findAll(id);
		model.addAttribute("commentList",commentDTOList);
		
		//board 라는 매개변수(파라미터)에 담아서 detail.html로 넘어간다
		model.addAttribute("board", boardDTO);
		return "detail";
	}
	
	
	// 게시글 수정 폼
	@GetMapping("update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {//model 객체는 컨트롤러에서 뷰로 데이터를 전달하는 데 사용됨
		//updateForm: 해당 게시글의 정보를 가져와서 update.html에 그 정보를 보여주기 위한 메서드
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("boardUpdate",boardDTO);
		return "update";
	}
	// 게시글 수정
	@PostMapping("update")
	public String update(@ModelAttribute BoardDTO boardDTO, Model model) { //수정이 끝나고 반영된 객체를 가져와서 detail.html로 보내줌
		BoardDTO board = boardService.update(boardDTO);
		model.addAttribute("board", board);
//		return "detail";
		return "redirect:/board/" + boardDTO.getId(); //리다이렉트 방식도 있지만 수정이 조회수에 영향을 미치게 됨
	}
	
	//게시글 삭제
	@GetMapping("delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.delete(id);
		return "redirect:/board/list";
	}
	
 
}







