package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	
	//조회수 증가 라는건 DB를 기준으로네이티브 쿼리를 고민한다면 
	//update board set hits=hits+1 where id=? (DB기준 네이티브 쿼리)
	//별도의 쿼리가 필요한 상황 
	@Modifying //업데이트나 삭제 기능의 쿼리를 실행해야 할 때 필수로 사용해야 하는 어노테이션
    @Query(value = "update BoardEntity b set b.hits=b.hits+1 where b.id=:id") //엔티티 기준 쿼리 작성 //:id는 쿼리 매개변수로 사용
    void updateHits(@Param("id") Long id);
	//이 메서드는 조회수를 증가시키는 업데이트 쿼리를 실행하는데 사용
	//@Param 어노테이션을 사용하여 id 매개변수를 지정하고, 이를 JPQL 쿼리에서 사용
	//메서드의 반환 타입은 void => 조회수를 증가시키는 작업이 완료된 후 아무 값도 반환하지 않음
}