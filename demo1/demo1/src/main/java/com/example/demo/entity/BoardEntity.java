package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.DTO.BoardDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//DB의 테이블 역할을 하는 클래스 (제이쿼리 사용해야 함)
@Entity
@Getter
@Setter
@Table(name = "board")
public class BoardEntity extends DateEntity{ //날짜entity를 상속 받음 (날짜를 갖게 됨)
	@Id //pk컬럼 지정, 필수
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 20, nullable = false) //옵션: 크기 20, not null
	private String writer;
	
	@Column //기본 옵션 시 => 크기255, null 가능
	private String title;
	
	@Column 
	private String password;
	
	@Column(length = 500)
	private String contents;
	
	@Column
	private int hits;
	
	//Board와 Comment와의 관계 //부모가 삭제되면 자식도 같이 삭제된다
	@OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CommentEntity> commentEntityList = new ArrayList<>();

	// 글 작성 시//DTO에 담긴 값들을 Entity로 옮겨 담는 작업
	public static BoardEntity toSaveEntity(BoardDTO boardDTO) { //id가 없는 이유는 스프링데이터 jpa가 이게 수정이 아니라 데이터 입력이라는 것을 알 수 있도록 
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setWriter(boardDTO.getWriter());
		boardEntity.setTitle(boardDTO.getTitle());
		boardEntity.setContents(boardDTO.getContents());
		boardEntity.setPassword(boardDTO.getPassword());
		boardEntity.setHits(0);
		return boardEntity;
	}
	
	// 글 수정 시//DTO에 담긴 값들을 Entity로 옮겨 담는 작업
	public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {// id값이 있으면 그 id로 찾아가서 데이터를 입력하기 때문에 수정이 됨
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setId(boardDTO.getId());
		boardEntity.setWriter(boardDTO.getWriter());
		boardEntity.setTitle(boardDTO.getTitle());
		boardEntity.setContents(boardDTO.getContents());
		boardEntity.setPassword(boardDTO.getPassword());
		boardEntity.setHits(boardDTO.getHits()); //조회수도 DB에서 가져온 값으로 
		return boardEntity;
	}

}
