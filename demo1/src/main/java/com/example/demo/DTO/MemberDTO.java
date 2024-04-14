package com.example.demo.DTO;

import java.time.LocalDateTime;

import com.example.demo.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor //기본 생성자를 자동으로 만들어줌
@AllArgsConstructor //필드를 모두 다 매개변수로 하는 생성자를 만들어줌
@ToString //DTO객체가 가지고 있는 어떤 필드 값을 출력할 때 사용 // ToString 메서드를 자동으로 만들어줌
public class MemberDTO { //DTO클래스는 member에 필요한 내용들을 필드로 정의
	private Long id;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
//	private String memberAddress;
//	private String memberPhone;
//	private String memberGender;
//	private LocalDateTime joinDate;


	public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId(memberEntity.getId());
		memberDTO.setMemberEmail(memberEntity.getMemberEmail());
		memberDTO.setMemberPassword(memberEntity.getMemberPassword());
		memberDTO.setMemberName(memberEntity.getMemberName());
		return memberDTO;
	}
	

}
