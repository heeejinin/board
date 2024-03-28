package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment/")
public class CommentController {
	private final CommentService commentService;
	
	//댓글 작성 내용 받아오는 부분
	@PostMapping("/write") //ajax 요청이라 @ResponseBody가 필요함 근데 서버로 전송된 데이터는 컨트롤러에서 @ModelAttribute 어노테이션을 통해 CommentDTO 객체로 매핑함
	public ResponseEntity wrtie(@RequestBody CommentDTO commentDTO) { //ResponseEntity는 HTTP 응답을 나타내는 클래스이며, 클라이언트에게 응답을 보내기 위해 사용
		System.out.println("commentDTO = " + commentDTO); //입력 받아온 댓글 내용 출력
		
		//댓글 작성 성공 후 save만 하면 안됨 => 댓글 작성하면 기존 댓글에 새로 작성한 댓글이 추가된 목록을 다시 화면에 띄워야 함 => 새로운 댓글만 추가하면 되는거 아니냐?라고 보이지만
		//결국 다시 전체 댓글을 가져와서 그걸 화면에서 반복문 형태로 보여주는게 다루기가 쉬움
		Long saveResult = commentService.write(commentDTO); //서비스에서 id로 리턴할 거라서 Long
		if(saveResult != null) {
			//작성 성공하면 댓글 목록을 가져와서 리턴
			//댓글목록: 해당 게시글의 댓글 전체(해당 게시글 아이디를 기준으로 그 아이디에 해당하는 댓글 전체를 가져오는 것) // 댓글 목록을 가져올 땐 게시글 번호가 기준이 됨
			List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
			return new ResponseEntity<>(commentDTOList, HttpStatus.OK); // 
		} else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

}