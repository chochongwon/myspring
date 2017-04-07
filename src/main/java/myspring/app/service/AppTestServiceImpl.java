package myspring.app.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import myspring.common.dao.SqlSessionDAO;
import myspring.sample.vo.Board2VO;


@Service("appTestService")
public class AppTestServiceImpl implements AppTestService {

	@Resource(name="sqlSessionDAO")
	private SqlSessionDAO sqlSessionDAO;

	@Override
	@SuppressWarnings("unchecked")
	public List<Map> selectListBoard1(String queryId) throws Exception {
		return openBoardList(queryId, null);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Map> selectListBoard1(String queryId, Map map) throws Exception {
		return sqlSessionDAO.select(queryId, map);
	}	

	@Override
	@SuppressWarnings("unchecked")
	public Board2VO selectOneBoard2(String queryId) throws Exception {
		return selectOneBoard2(queryId, null);
	}	
	@Override
	@SuppressWarnings("unchecked")
	public Board2VO selectOneBoard2(String queryId, Map map) throws Exception {
		return (Board2VO) sqlSessionDAO.selectOne(queryId, map);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Map> selectListBoard2(String queryId) throws Exception {
		return  selectListBoard2(queryId, null);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Map> selectListBoard2(String queryId, Map map) throws Exception {
        return sqlSessionDAO.queryForList(queryId, map);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Map> openBoardList(String queryId) throws Exception {
		return openBoardList(queryId, null);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Map> openBoardList(String queryId, Map map) throws Exception {
		return sqlSessionDAO.select(queryId, map);
	}	

}
