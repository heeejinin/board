package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.MemberDTO;
import com.example.demo.entity.MemberEntity;
import com.example.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	public void join(MemberDTO memberDTO) {
		// 1. DTO -> Entity 변환
		// 2. Repository의 save 메서드를 호출(조건: entity 객체를 넘겨줘야 함)
		MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
		memberRepository.save(memberEntity); //repository.save는 jpa에서 제공해주는 메서드임
	}

	public MemberDTO login(MemberDTO memberDTO) {
		// 1. 회원이 입력한 이메일로 DB에서 조회를 함
		// 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
		Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
		if(byMemberEmail.isPresent()) {
			// 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있음)
			MemberEntity memberEntity = byMemberEmail.get();
			if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
				// 비밀번호 일치
				// entity -> dto 변환 후 리턴
				MemberDTO loginResult = MemberDTO.toMemberDTO(memberEntity);
				return loginResult;
			} else {
				// 비밀번호가 불일치(로그인 실패)
				return null;
			}
		} else {
			// 조회 결과가 없다(해당 이메일을 가진 회원이 없음)
			return null;
		}
	}

	public List<MemberDTO> findAll() {
		List<MemberEntity> memberEntityList = memberRepository.findAll();
		List<MemberDTO> memberDTOList = new ArrayList<>();
		//List에 담긴 값들이 여러 개니까 하나씩 담아줘야 하기에 for문 사용
		for(MemberEntity memberEntity: memberEntityList) { //foreach문
		memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
		}
		return memberDTOList;
	}

}
