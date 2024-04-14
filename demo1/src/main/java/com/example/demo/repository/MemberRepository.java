package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MemberEntity;

//인터페이스에서는 추상 메서드가 정의됨
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{ //extends: 상속 <상속받을 Entity, 해당 Entity의 pk 타입>
	//이메일로 회원 정보 조회 ( select * from member where member_email=?)
	//Optional은 일종의 null 방지 (java.util에서 제공하는 기능)
	Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
