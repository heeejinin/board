package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
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
	        BoardEntity boardEntity = optionalBoardEntity.get(); //.get()메서드로 Optional 객체에서 실제 BoardEntity 객체를 가져옴
	      //DTO로 받아온 것을 Entity로 변환하는 작업 필요
	        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity); //boardEntity는 게시글 id 때문에 필요
	        return commentRepository.save(commentEntity).getId();
	    } else {
	        return null;
	    }
	}

	public List<CommentDTO> findAll(Long boardId) {
		//게시글 기준으로 목록 전체를 가져오고 거기서 id 기준으로 내림차순적으로 최근 작성한 댓글이 먼저 보이게끔 하려고 함
		// select * from comment where board_id=? order by id desc;
		BoardEntity boardEntity = boardRepository.findById(boardId).get();
		List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
		/* EntityList -> DTOList */
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
		return commentDTOList;
	}
	
	
}