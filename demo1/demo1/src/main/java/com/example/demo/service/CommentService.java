package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.entity.BoardEntity;
import com.example.demo.entity.CommentEntity;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentService {
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;

	//댓글 작성 기능
	public Long write(CommentDTO commentDTO) {
		// 부모엔티티(BoardEntity) 조회
		Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
		if (optionalBoardEntity.isPresent()) {
	        BoardEntity boardEntity = optionalBoardEntity.get();
	      //DTO로 받아온 것을 Entity로 변환하는 작업 필요
	        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
	        return commentRepository.save(commentEntity).getId();
	    } else {
	        return null;
	    }
	}
	
}
