package myspring.app.service;

import java.util.List;
import java.util.Map;

public interface AppTestService {
	List<Map> select(String queryId, Map map) throws Exception;
}
