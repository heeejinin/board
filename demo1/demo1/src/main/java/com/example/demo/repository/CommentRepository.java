package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

}
