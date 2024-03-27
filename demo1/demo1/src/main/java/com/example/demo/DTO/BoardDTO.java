package com.example.demo.DTO;

import java.time.LocalDateTime;

import com.example.demo.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private long id;
	private String writer;
	private String title;
	private String password;
	private String contents;
	private int hits;
	private LocalDateTime writeTime;
	private LocalDateTime updateTime;
	
	//엔티티 객체를 DTO 객체로 옮겨 담음
	public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setId(boardEntity.getId());
		boardDTO.setWriter(boardEntity.getWriter());
		boardDTO.setTitle(boardEntity.getTitle());
		boardDTO.setPassword(boardEntity.getPassword());
		boardDTO.setContents(boardEntity.getContents());
		boardDTO.setHits(boardEntity.getHits());
		boardDTO.setWriteTime(boardEntity.getWriteTime());
		boardDTO.setUpdateTime(boardEntity.getUpdateTime());
		return boardDTO;
	}
	

}
