package com.example.demo.entity;

import com.example.demo.DTO.MemberDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity //Entity 클래스가 일종의 테이블 역할을 하는 것임 // 스프링 데이터 jpa라는 것은 DB의 테이블을 일종의 자바 객체처럼 활용할 수 있도록 해주는 것이 특징임
@Getter
@Setter
@Table(name = "member") //DB에 Entity 내용을 기반으로 테이블을 생성해주는 어노테이션
public class MemberEntity {
	@Id // pk를 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment를 지정해주는 부분(오라클의 경우 시퀀스 역할이라고 보면 됨)
	private Long id;
	
	@Column(unique = true) // unique 제약조건을 추가
	private String memberEmail;
	
	@Column
	private String memberPassword;
	
	@Column
	private String memberName;
	
	
	//Entity를 객체로 만들어서 호출하는 메서드가 아니라 class메서드(static메서드)로 정의를 하는 것임
	public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setId(memberDTO.getId());
		memberEntity.setMemberEmail(memberDTO.getMemberEmail());
		memberEntity.setMemberPassword(memberDTO.getMemberPassword());
		memberEntity.setMemberName(memberDTO.getMemberName());
		return memberEntity;
	}
}
