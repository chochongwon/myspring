package myspring.sample.dao.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class AbstractDAO {
	protected Log log = LogFactory.getLog(AbstractDAO.class);
	
	// @Autowired 은 xml에 선언했던 의존관계의 빈(bean)을 자동으로 주입한다.
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	protected void printQueryId(String queryId) {
		if(log.isDebugEnabled()){
			log.debug("\t QueryId  \t:  " + queryId);
		}
	}
	
	public Object insert(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	public Object update(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	public Object delete(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	public Object selectOne(String queryId){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	public Object selectOne(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId){
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectList(queryId,params);
	}
		
	/*
	 * Ajax는 Asynchronous JavaScript and Xml 의 약자로 
	 * 클라이언트(웹브라우저)와 서버의 비동기적 통신을 통한 데이터 전송을 이용하는 방법이다. 
	 * Ajax는 클라이언트와 서버가 내부적으로 데이터 통신을 하고 그 결과를 웹페이지에 프로그래밍적으로 반영한다. 
	 * 그 결과 화면의 로딩없이 그 결과를 보여줄 수 있다.
	 * jQuery에서는 Ajax 통신을 쉽게 하는 ajax() 함수를 제공하고 있다. 
	 */
	@SuppressWarnings("unchecked")
	public Object selectListPagingAjax(String queryId, Object params){
	    printQueryId(queryId);
	    Map<String,Object> map = (Map<String,Object>)params;
	     
	    String strPageIndex = (String)map.get("PAGE_INDEX");
	    String strPageRow = (String)map.get("PAGE_ROW");
	    int nPageIndex = 0;
	    int nPageRow = 20;
	     
	    if(StringUtils.isEmpty(strPageIndex) == false){
	        nPageIndex = Integer.parseInt(strPageIndex)-1;
	    }
	    if(StringUtils.isEmpty(strPageRow) == false){
	        nPageRow = Integer.parseInt(strPageRow);
	    }
	    map.put("START", (nPageIndex * nPageRow) + 1);
	    map.put("END", (nPageIndex * nPageRow) + nPageRow);
	     
	    return sqlSession.selectList(queryId, map);
	}

	/*
	 * 전자정부 프레임워크의 페이징처리
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map selectListPagingEgov(String queryId, Object params){
	    printQueryId(queryId);
	     
	    Map<String,Object> map = (Map<String,Object>)params;
	    /*
	     * 전자정부의 PaginationInfo 클래스에 여러가지 값을 설정하고 그 값을 이용해서 화면에 출력할 것을 계산하기도 한다.
	     */
	    PaginationInfo paginationInfo = null;	    
	    /*
	     * currentPageno는 현재 페이지 번호를 의미한다. 
	     * 예상치 못한 상황에서 이 값이 없을 경우 에러가 발생하는걸 방지하기 위한 부분이다.
	     */
	    if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
	        map.put("currentPageNo","1");
	     
	    paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
	    /*
	     * 한 페이지에 몇개의 행을 보여줄 것인지를 설정한다. 
	     * 만약 화면에서 특정한 값을 보내준다면 그에 맞는 행을 보여주고, 그 값이 없으면 15개를 보여주도록 하였다.
	     */
	    if(map.containsKey("PAGE_ROW") == false || StringUtils.isEmpty(map.get("PAGE_ROW")) == true){
	        paginationInfo.setRecordCountPerPage(15);
	    }
	    else{
	        paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("PAGE_ROW").toString()));
	    }
	    /*
	     * 한번에 보여줄 페이지의 크기를 지정하였다. [이전] 1~10 [다음] 식으로 나올 때 1~10의 10개를 설정한 것이다.
	     */
	    paginationInfo.setPageSize(10);
	     
	    int start = paginationInfo.getFirstRecordIndex();
	    int end = start + paginationInfo.getRecordCountPerPage();
	    /*
	     * 쿼리를 실행을 위해 시작과 끝 값을 계산해서 파라미터에 추가한다.
	     */
	    map.put("START",start+1);
	    map.put("END",end);
	     
	    params = map;
	    
	    Map<String,Object> returnMap = new HashMap<String,Object>();
	    List<Map<String,Object>> list = sqlSession.selectList(queryId,params);
	    
	    /*
	     * 페이징 처리에서 만약 조회된 결과값이 없으면 그에 해당하는 결과를 화면에 표시할 수 있도록 
	     * TOTAL_COUNT라는 값을 추가해주었다. 
	     * 반환변수 result는 paginationInfo 클래스와 조회 후 결과리스트를 반환한다.
	     */
	    if(list.size() == 0){
	        map = new HashMap<String,Object>();
	        map.put("TOTAL_COUNT",0); 
	        list.add(map);
	         
	        if(paginationInfo != null){
	            paginationInfo.setTotalRecordCount(0);
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    else{
	        if(paginationInfo != null){
	            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("TOTAL_COUNT").toString()));
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    returnMap.put("result", list);
	    return returnMap;
	}
}
