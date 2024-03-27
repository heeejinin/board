package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class DateEntity { //나중에 회원가입은 언제 했는지 등 시간 정보가 필요할 때 상속받아서 쓰면 됨
	@CreationTimestamp
	@Column(updatable = false) //옵션: 업데이트 시 관여X
	private LocalDateTime writeTime;
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updateTime;
	
}
