package myspring.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface SampleService {
	List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception;

	int insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;

	Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception;

	int updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;

	int deleteGbBoard(Map<String, Object> map) throws Exception;

	int deleteBoard(Map<String, Object> map) throws Exception;

	List<Map<String, Object>> selectBoardListPagingAjax(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selectBoardListPagingEgov(Map<String, Object> map) throws Exception;

}

