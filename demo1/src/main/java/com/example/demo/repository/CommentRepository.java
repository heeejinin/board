package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BoardEntity;
import com.example.demo.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
	// select * from comment where board_id=? order by id desc;
	// 1. List 타입으로 정의 (findAllByBoardEntityOrderByIdDesc 는 메소드 이름임.. 존나 긴..)
	List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

}