package myspring.sample.dao.mapper2;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import myspring.sample.vo.Board1TransferVO;
import myspring.sample.vo.Board1VO;

public interface Board1DAO {    
	List<Board1VO> selectListBoard1() throws Exception;
	Board1VO selectOneBoard1(@Param(value = "idx") Integer idx) throws Exception;
	
	// HashMap을 이용한 예제    
	void mapProcedureListBoard1(Map map) throws Exception;	
	// VO를 사용한 프로시저 호출 예제
	void procedureListBoard1(Board1TransferVO container) throws Exception;
	// VO를 사용한 익명 블럭 호출 예제
	void anonymousBlockListBoard1(Board1TransferVO container) throws Exception;
}
