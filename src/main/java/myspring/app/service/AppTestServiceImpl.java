package myspring.app.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import myspring.common.dao.SqlSessionDAO;


@Service("appTestService")
public class AppTestServiceImpl implements AppTestService {

	@Resource(name="sqlSessionDAO")
	private SqlSessionDAO sqlSessionDAO;

	@Override
	@SuppressWarnings("unchecked")
	public List<Map> select(String queryId, Map map) throws Exception {
		return  sqlSessionDAO.select(queryId, map);
	}
}
