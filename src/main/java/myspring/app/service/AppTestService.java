package myspring.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import myspring.sample.vo.Board2VO;

public interface AppTestService {

	List<Map> selectListBoard1(String queryId) throws Exception;
	List<Map> selectListBoard1(String queryId, Map map) throws Exception;

	Board2VO selectOneBoard2(String queryId) throws Exception;
	Board2VO selectOneBoard2(String queryId, Map map) throws Exception;

	List<Map> selectListBoard2(String queryId) throws Exception;
	List<Map> selectListBoard2(String queryId, Map map) throws Exception;
	
	List<Map> openBoardList(String queryId) throws Exception;
	List<Map> openBoardList(String queryId, Map map) throws Exception;
	
}
