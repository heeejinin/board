package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.BoardEntity;
import com.example.demo.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	//글 작성 DB에 데이터 보냄 //서비스부터는 Entity를 사용(엔터티가 DB 테이블 역할 & 보안 이유로 구분지어 사용)
	public void write(BoardDTO boardDTO) { 
		//DTO에 담아서 이 값을 엔터티에 담아서 저장하는 기능을 엔터티에 만들어줌
		BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
		boardRepository.save(boardEntity); // write => insert into board (title, writer, content) values(?,?,?) 레포지토리에서 실행됨 
	}
	
	//이 함수는 Entity로 넘어온 객체를 DTO객체로 옮겨 담아서 다시 컨트롤러로 리턴해줘야 함
	public List<BoardDTO> findAll(){
		List<BoardEntity> boardEntityList = boardRepository.findAll(); //레포지토리에서 무언가를 가져올 땐 보통 Entity로 가져옴 
		
		//boardEntityList에 담긴 데이터를 boardDTOList로 옮겨 담아야 함
		//엔터티 객체를 DTO 객체로 옮겨 담는 작업은 BoardDTO에 가서 할 것임
		//조회된 게시글을 담을 리스트 객체 boardDTOList를 선언
		List<BoardDTO> boardDTOList = new ArrayList<>();
		//엔터티에 있는 값들을 꺼내서 DTOlist에 하나씩 담아줌
		for (BoardEntity boardEntity : boardEntityList) {
			boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
		}
		return boardDTOList;
	}
	
	@Transactional //JPA에서 제공하는 메서드가 아닌 별도로 추가한 메서드를 사용하는 경우에는 @Transactional 사용해야 함
	public void updateHits(Long id) {
		boardRepository.updateHits(id);
		
	}

	public BoardDTO findById(Long id) {
		Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
		//Optional은 값의 존재 여부를 나타내는 컨테이너 객체 
		//만약 해당 ID에 해당하는 게시글이 없으면 빈 Optional 객체가 반환
		if(optionalBoardEntity.isPresent()) { //optionalBoardEntity 옵셔널 객체가 있으면 boardDTO로 반환하고, 
			BoardEntity boardEntity = optionalBoardEntity.get(); //optionalBoardEntity 객체에서 실제 BoardEntity 객체를 가져옵니다. 이 작업은 get() 메서드를 사용하여 수행
			BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity); //가져온 BoardEntity 객체를 BoardDTO로 변환
			return boardDTO;
		} else {
			return null; //없으면 null 리턴
		}
	}
	
	//수정
	public BoardDTO update(BoardDTO boardDTO) {
		//레퍼지토리에 따로 update 메서드가 존재하지 않음 // save로 값을 넣어주는데, 업뎃인지 작성인지 우째 아냐? ID값이 있냐 없냐로 구분함
		BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
		boardRepository.save(boardEntity); 
		return findById(boardDTO.getId()); //위에 있는 메서드 findById를 쓴 것
	}

	public void delete(Long id) {
		boardRepository.deleteById(id);
	}
	
	
	
	

}
