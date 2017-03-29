package myspring.app.service;

import java.util.List;
import java.util.Map;

import myspring.app.vo.Board1TransferVO;
import myspring.app.vo.Board1VO;

public interface Board1Service {
	List<Board1VO> selectListBoard1() throws Exception;
	Board1VO selectOneBoard1(Integer idx) throws Exception;
	
	Map<String, Object> mapProcedureListBoard1(Map map) throws Exception;
	Board1TransferVO procedureListBoard1(Board1TransferVO container) throws Exception;
	Board1TransferVO anonymousBlockListBoard1(Board1TransferVO container) throws Exception;
}
