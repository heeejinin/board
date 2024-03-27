package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import com.example.demo.DTO.CommentDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity extends DateEntity{ //날짜 상속 받음
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private String commentWriter;

	@Column
	private String commentContents;
	
	// board와 comment의 관계 => comment는 board의 자식임: 보드를 참조하는 관계
	// Board : Comment = 1 : N (보드 하나에 댓글 여러개가 될 수 있다)
	//댓글을 기준으로 ManytoOne
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private BoardEntity boardEntity;

	public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setCommentWriter(commentDTO.getCommentWriter());
		commentEntity.setCommentContents(commentDTO.getCommentContents());
		commentEntity.setBoardEntity(boardEntity); //게시글 번호로 조회한 부모 엔티티 값을 넣어줘야 함 => JPA 사용할 때, 참조관계를 맺었을 시의 문법임
		return commentEntity;
	}
	
}
