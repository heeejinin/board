package com.example.demo.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
	private Long id; //댓글 아이디 값
	private String commentWriter;
	private String commentContents;
	private Long boardId; //게시글 번호 (중요함)
	private LocalDateTime commentSavedTime;
}
