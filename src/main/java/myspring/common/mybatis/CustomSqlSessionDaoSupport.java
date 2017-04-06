package myspring.common.mybatis;


import static myspring.common.support.Constants.CHECKBOX_COLUMN;
import static myspring.common.support.Constants.DECRYPT_COULMN;
import static myspring.common.support.Constants.PAGE_NO_COLUMN;
import static myspring.common.support.Constants.ROW_TYPE_COLUMN;
import static myspring.common.support.Constants.VAR_CURR_KEY_;
import static myspring.common.support.Constants.VAR_CURR_PAGE_;
import static myspring.common.support.Constants.VAR_NEXT_KEY_;
import static myspring.common.support.Constants.VAR_NEXT_PAGE_;
import static myspring.common.support.Constants.VAR_PAGE_INDEX_SCALE;
import static myspring.common.support.Constants.VAR_ROW_PER_PAGE_;
import static myspring.common.support.Constants.VIRTUAL_COULMN;
import static myspring.common.support.Constants.ZERO;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.BatchExecutorException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import myspring.common.dao.support.StatementSelector;
import myspring.common.enums.RowType;
import myspring.common.mybatis.support.ColumnInfoResultHandler;
import myspring.common.mybatis.support.ColumnInfoTransferList;
import myspring.common.mybatis.support.ColumnInjectionResultHandler;
import myspring.common.mybatis.support.MaxHandleRowException;
import myspring.common.mybatis.support.PagingGridResultHandler;
import myspring.common.support.Constants;
import myspring.common.util.CommonUtil;


/**
 * 시스템 DAO 구현을 위한 최상위 계층 클래스. 
 * DAO 클래스 구현을 위해서 반드시 상속받아야 한다.
 * <p>
 * Spring 의 DaoSupport 패전을 사용하며, mybatis SqlSession 의 template 메소드를 제공한다.
 * <p>
 * select 쿼리시에 ColumnInjectionRowHandler 를 사용하여 기본 컬럼을 삽입하는 역할을 수행한다.
 * <p>
 * batch 쿼리를 지원한다.
 * @author ddam40
 */
public class CustomSqlSessionDaoSupport extends DaoSupport {
    protected final Log logger = LogFactory.getLog(getClass());

	private SqlSessionTemplate sqlSessionTemplate;
	
    /**
     * batch 처리를 위한 최소 row count.
     * 이것보다 작으면 그냥 case by case 처리한다.
     */
    public final static int MIN_BATCH_COUNT = 1000; //FIXME - procedure 의 batch 사용이 안된다. 

    /**
     * QueryForList 의 경우 Max 제한
     */
    private static int MAX_MAX_HANDLE_ROW = 100000;
    
    /**
     * mass selection logging 임계치 
     */
    private static int CRITICAL_HANDLE_ROW = 5000;

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
    public final void setSqlSession(SqlSessionFactoryBean sqlSession) {
    	SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate((SqlSessionFactory) sqlSession);
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    /**
     * bean 초기화시에 호출됨.
     * 현재 하는 일 없음.
     */
    @Override
    protected void initDao() throws Exception {
        super.initDao();
    }
    
    /**
     * PagingGrid Request 인지 검사한다.
     * @param parameterObject
     * @return
     */
    protected static boolean isPagingGridRequest(Object parameterObject) {
        if(parameterObject instanceof Map) {
            Map map = (Map)parameterObject;
            
            if(map.containsKey(VAR_NEXT_PAGE_) && map.containsKey(VAR_ROW_PER_PAGE_)
                    && NumberUtils.isNumber(map.get(VAR_NEXT_PAGE_).toString())
                    && NumberUtils.isNumber(map.get(VAR_ROW_PER_PAGE_).toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * RomanticPagingGrid Request 인지 검사한다.
     * @param parameterObject
     * @return
     */
    protected static boolean isRomanticPagingGridRequest(Object parameterObject) {
        if(parameterObject instanceof Map) {
            Map map = (Map)parameterObject;
            if(map.containsKey(VAR_NEXT_KEY_) && map.containsKey(VAR_CURR_KEY_) && map.containsKey(VAR_PAGE_INDEX_SCALE)) {
                return true;
            }
        }
        return false;
    }

	@SuppressWarnings("rawtypes")
    protected List queryForList(String statementName) {
        return queryForList(statementName, null);
    }

	@SuppressWarnings("rawtypes")
    protected List queryForList(String statementName, Object parameterObject) {
        if(logger.isDebugEnabled()) {
            logger.debug("query id : [" + statementName + "] parameters : " + parameterObject);
        }        
        List<?> result = sqlSessionTemplate.selectList(statementName, parameterObject, new RowBounds(0, MAX_MAX_HANDLE_ROW));
        if(logger.isDebugEnabled()) {
            logger.debug("query id : [" + statementName + "] row count : " + result.size());
        }
        return result;
    }

    public void select(String statementName, Object parameterObject, ResultHandler handler) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("query id : [" + statementName + "] parameters : " + parameterObject);
        }

        sqlSessionTemplate.select(statementName, parameterObject, handler);

        if(logger.isDebugEnabled()) {
            logger.debug("query id : [" + statementName + "] with ResultHandler completed");
        }
    }

    private List<Map> selectList(String statementName, Object parameterObject, ColumnInfoResultHandler handler) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("query id : [" + statementName + "] parameters : " + parameterObject);
        }        

        sqlSessionTemplate.select(statementName, parameterObject, handler);

        List<Map> result = handler.getColumnInfoTransferList();
        if(logger.isDebugEnabled()) {
            logger.debug("query id : [" + statementName + "] row count : " + result.size() + ", column count : " + ((ColumnInfoTransferList)result).getColumnInfoList().size());
        }

        if(result.size() > Constants.MAX_DATASET_HANDLE_ROW) {
            throw new MaxHandleRowException(Constants.MAX_DATASET_HANDLE_ROW + " 건 이상은 조회할 수 없습니다. 조회 조건을 다시 설정해 주세요. ["+result.size()+"]");
        }
        return result;
    }

    /**
     * SqlMap 조회 처리 Template Method.
     * 
     * <p>select type의 SqlMap statement 를 실행하고 결과를 List&lt;Map&gt; 형태로 반환한다.
     * @param statementName SqlMap Statement Id
     * @return 조회 결과 List&lt;Map&gt;
     */
    protected List<Map> select(String statementName) throws Exception {
        return select(statementName, null);
    }

    /**
     * SqlMap 조회 처리 Template Method.
     * 
     * <p>select type의 SqlMap statement 를 실행하고 결과를 List&lt;Map&gt; 형태로 반환한다.
     * <p>파라미터 세팅을 위한 Object를 전달받아 사용한다.
     * <p>ColumnInjectionRowHandler 를 사용하여 dataset을 위한 Rowtype, checkbox, virtual Column을 injection 한다. 
     * @param statementName SqlMap Statement Id
     * @param parameterObject 파라미터 세팅을 위한 Object 객체 
     * @return 조회 결과 List&lt;Map&gt;
     */
    protected List<Map> select(String statementName, Object parameterObject) throws Exception {
        if(isPagingGridRequest(parameterObject)) {
            if(isRomanticPagingGridRequest(parameterObject)) {
                return selectList4RomanticPagingGrid(statementName, parameterObject);
            }
            return selectList4PagingGrid(statementName, parameterObject);
        }
        
        ColumnInjectionResultHandler rowHandler = new ColumnInjectionResultHandler(
                ROW_TYPE_COLUMN, RowType.NORMAL.initialLetter,
                DECRYPT_COULMN, "0",
                VIRTUAL_COULMN, "N",
                CHECKBOX_COLUMN, "0");

        return selectList(statementName, parameterObject, rowHandler);
    }

    /**
     * PagingGrid 처리
     * @param statementName
     * @param parameterObject
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private List<Map> selectList4PagingGrid(String statementName, Object parameterObject) throws Exception {
        Map map = (Map)parameterObject;

        if(logger.isDebugEnabled()) {
            int pageNo = NumberUtils.toInt(map.get(VAR_NEXT_PAGE_).toString());
            int rowPerPage = NumberUtils.toInt(map.get(VAR_ROW_PER_PAGE_).toString());

            logger.debug("Paging Grid Request Encounter !!!");
            logger.debug("selectList4PagingGrid with pageNo : "+pageNo+", rowPerPage : " + rowPerPage);
        }

        PagingGridResultHandler rowHandler = new PagingGridResultHandler(
                ROW_TYPE_COLUMN, RowType.NORMAL.initialLetter,
                DECRYPT_COULMN, "0",
                VIRTUAL_COULMN, "N",
                CHECKBOX_COLUMN, "0");
        
        rowHandler.setPagingInfo(parameterObject);
        
        return selectList(statementName, parameterObject, rowHandler);
    }

    /**
     * RomanticPagingGrid 처리
     * @param statementName
     * @param parameterObject
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private List<Map> selectList4RomanticPagingGrid(String statementName, Object parameterObject) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("Romantic Paging Grid Request Encounter !!!");
        }

        Map map = (Map)parameterObject;
        int prevPageNo = NumberUtils.toInt(map.get(VAR_CURR_PAGE_).toString());
        int pageNo = NumberUtils.toInt(map.get(VAR_NEXT_PAGE_).toString());
        int indexScale = NumberUtils.toInt(map.get(VAR_PAGE_INDEX_SCALE).toString());
        int rowPerPage = NumberUtils.toInt(map.get(VAR_ROW_PER_PAGE_).toString());

        // RomanticPaging Mode
        String romanticMode = "P"; //Play
        int prevWindow = (prevPageNo - 1) / indexScale + 1;
        int nextWindow = (pageNo - 1) / indexScale + 1;
        if(nextWindow == 1) {
            ((Map)parameterObject).remove(VAR_CURR_KEY_);
        } else {
            if(prevWindow > nextWindow) {
                romanticMode = "R"; //Reverse
            } else if(prevWindow < nextWindow) {
                romanticMode = "F"; //Forward
            }
        }

        // Rownum from to
        int romanticPageNo = (pageNo - 1) % indexScale;
        if(romanticMode.equals("R")) {
            romanticPageNo = 0;
        }
        int from = romanticPageNo  * rowPerPage + 1;
        int to = (romanticPageNo + 1) * rowPerPage;

        // Trunk Size
        int trunkSize = rowPerPage * indexScale + 1;

        CommonUtil.injectColumnValues((Map)parameterObject, "ROMANTIC_PAGING", romanticMode,
                                                            "ROMANTIC_FROM", Integer.valueOf(from),
                                                            "ROMANTIC_TO", Integer.valueOf(to),
                                                            "ROMANTIC_TRUNK", Integer.valueOf(trunkSize));

        ColumnInjectionResultHandler rowHandler = new ColumnInjectionResultHandler(
                ROW_TYPE_COLUMN, RowType.NORMAL.initialLetter,
                VIRTUAL_COULMN, "N",
                CHECKBOX_COLUMN, "0",
                PAGE_NO_COLUMN, pageNo,
                ROW_TYPE_COLUMN, RowType.NORMAL.initialLetter,
                DECRYPT_COULMN, "0",
                CHECKBOX_COLUMN, ZERO);

        List<Map> list = selectList(statementName, parameterObject, rowHandler);
        
        if(romanticMode.equals("R")) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * SqlMap 단건 조회 처리 Template Method.
     * <p>조회 결과는 반드시 1row 여야 한다. 하나 이상의 Record가 리턴된다면 예외가 발생한다.
     * @param statementName SqlMap Statement Id
     * @return 조회 결과 Object 
     */
    protected Object selectOne(String statementName) throws Exception {
        return sqlSessionTemplate.selectOne(statementName);
    }

    /**
     * SqlMap 단건 조회 처리 Template Method.
     * <p>조회 결과는 반드시 1row 여야 한다. 하나 이상의 Record가 리턴된다면 예외가 발생한다.
     * <p>파라미터 세팅을 위한 Object를 전달받아 사용한다. 
     * @param statementName SqlMap Statement Id
     * @param parameterObject 파라미터 세팅을 위한 Object 객체 
     * @return 조회 결과 Object 
     */
    protected Object selectOne(String statementName, Object paramterObject) throws Exception {
        return sqlSessionTemplate.selectOne(statementName, paramterObject);
    }

    /**
     * SqlMap 등록 처리 Template Method.
     * 
     * <p>insert type의 SqlMap Statement 를 실행하고, insert 된 rowCount를 반환 한다.
     * @param statementName sqlMap Statement id
     * @return insert 된 rowCount
     */
    protected int insert(String statementName) throws Exception {
        return sqlSessionTemplate.update(statementName);
    }

    /**
     * SqlMap 등록 처리 Template Method.
     * 
     * <p>insert type의 SqlMap Statement 를 실행하고, insert 된 rowCount를 반환 한다.
     * <p>파라미터 세팅을 위한 Object를 전달받아 사용한다.
     * @param statementName sqlMap Statement id
     * @param parameterObject 파라미터 세팅을 위한 Object 객체
     * @return insert 된 rowCount
     */
    protected int insert(String statementName, Object parameterObject) throws Exception {
        return sqlSessionTemplate.update(statementName, parameterObject);
    }

    /**
     * SqlMap 수정 처리 Template Method.
     * 
     * <p>update type의 SqlMap Statement 를 실행하고, update 된 rowCount를 반환 한다.
     * @param statementName sqlMap Statement id
     * @return update 된 rowCount
     */
    protected int update(String statementName) throws Exception {
        return sqlSessionTemplate.update(statementName);
    }

    /**
     * SqlMap 수정 처리 Template Method.
     * 
     * <p>update type의 SqlMap Statement 를 실행하고, update 된 rowCount를 반환 한다.
     * <p>파라미터 세팅을 위한 Object를 전달받아 사용한다.
     * @param statementName sqlMap Statement id
     * @param parameterObject 파라미터 세팅을 위한 Object 객체
     * @return update 된 rowCount
     */
    protected int update(String statementName, Object parameterObject) throws Exception {
        return sqlSessionTemplate.update(statementName, parameterObject);
    }

    /**
     * SqlMap 삭제 처리 Template Method.
     * 
     * <p>delete type의 SqlMap Statement 를 실행하고, delete 된 rowCount를 반환 한다.
     * @param statementName sqlMap Statement id
     * @return delete 된 rowCount
     */
    protected int delete(String statementName) throws Exception {
        return sqlSessionTemplate.delete(statementName);
    }

    /**
     * SqlMap 삭제 처리 Template Method.
     * 
     * <p>delete type의 SqlMap Statement 를 실행하고, delete 된 rowCount를 반환 한다.
     * <p>파라미터 세팅을 위한 Object를 전달받아 사용한다.
     * @param statementName sqlMap Statement id
     * @param parameterObject 파라미터 세팅을 위한 Object 객체
     * @return delete 된 rowCount
     */
    protected int delete(String statementName, Object parameterObject) throws Exception {
        return sqlSessionTemplate.delete(statementName, parameterObject);
    }

    /**
     * Multi row 에 대한 batch 처리.
     * <p>
     * SqlMapDAO.batch 사용 대신, RowType 에 따른 caseBycase 처리
     * RowType 에 따른 Statement를 사용되기 때문에 RowType 이 NORMAL 인 row는 제거한 후 batch 실행한다.
     *
     * @param StatementSelector - RowType별 statement id 리턴해줄 Selector
     * @param list - 처리 대상 Dataset
     * @return total row count for INSERT, UPDATE, or DELETE statements
     * @throws Exception
     */
    protected int unitBatch(StatementSelector selector, List<Map> list) throws Exception {
        if(list == null || list.isEmpty()) {
            //throw new IllegalArgumentException("list is empty");
            logger.warn("Argument Warning : list is empty, nothing happend");
            return 0;
        }

        //RowType.NORMAL 제거
        list = CommonUtil.getRowTypeSafeList(list);

        int cnt = 0;
        for(Map row : list) {
            RowType type = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));

            String sql = selector.select(type);

            cnt += branchExecuteSQL(row, type, sql);
        }

        if(list.size()>0 && cnt == 0) {
            logger.warn("row count for INSERT, UPDATE, or DELETE statements is zero");
        }
        return cnt;
    }

    /**
     * Multi row 에 대한 batch 처리.
     * SqlMapDAO.batch 사용 대신, RowType 에 따른 caseBycase 처리
     *
     * @return total row count for INSERT, UPDATE, or DELETE statements
     * @throws Exception
     */
    protected int unitBatch(String statementName, List<Map> list) throws Exception {
        if(list == null || list.isEmpty()) {
            //throw new IllegalArgumentException("list is empty");
            logger.warn("Argument Warning : list is empty, nothing happend");
            return 0;
        }

        int cnt = 0;
        for(Map row : list) {
            RowType type = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));
            cnt += branchExecuteSQL(row, type, statementName);
        }
        if(list.size()>0 && cnt == 0) {
            logger.warn("row count for INSERT, UPDATE, or DELETE statements is zero");
        }
        return cnt;
    }

    /**
     * RowType 에 따른 sql execution 분기 처리
     * @return row count for INSERT, UPDATE, or DELETE statements
     * @throws Exception
     */
    private int branchExecuteSQL(Map row, RowType type, String sql) throws Exception {
        int cnt = 0;
        switch(type) {
            case INSERT:
                cnt = insert(sql, row);
                break;
            case UPDATE:
                cnt = update(sql, row);
                break;
            case DELETE:
                cnt = delete(sql, row);
                break;
            default:
                cnt = update(sql, row);
        }
        return cnt;
    }

    /**
     * 단일 Statement 에 대한 batch 처리 메소드.
     * 
     * 단일 Statement 에 대해 Rowtype 과 무관하게 parameterList 만큼 쿼리를 수행한다.
     * MIN_BATCH_COUNT 보다 대량 처리의 경우에는 batch 처리한다.
     * @param statementName SqlMap Statement id
     * @param parameterList 처리 대상 parameter list
     * @return 전체 처리 건수
     */
    protected int batch(String statementName, List parameterList) throws Exception {
        Assert.notNull(parameterList);
        if(parameterList.isEmpty()) {
            //throw new IllegalArgumentException("list is empty"); 
            logger.warn("Argument Warning : list is empty, nothing happend");
            return 0;
        }
        
        if(parameterList.size() < MIN_BATCH_COUNT) {
            return unitBatch(statementName, parameterList);
        }        

        return executeBatch(getBatchItemMap(statementName, parameterList));        
    }    
    /**
     * StatementSelector를 이용하여 RowType별 Statment id 세팅후에 SqlMapDAO.batch 호출.
     * <p>대상 row가 MIN_BATCH_COUNT 이하이면, 그냥 caseBycase로 처리한다.</p>
     * RowType 에 따른 Statement를 사용되기 때문에 RowType 이 NORMAL 인 row는 제거한 후 batch 실행한다.
     *
     * @param StatementSelector RowType별 statement id 리턴해줄 Selector
     * @param parameterList batch 파라미터
     * @return total row count for INSERT, UPDATE, or DELETE statements
     */
    @SuppressWarnings("unchecked")
    protected int batch(StatementSelector selector, List<Map> parameterList) throws Exception {
        Assert.notNull(parameterList);
        if(parameterList.isEmpty()) {
            //throw new IllegalArgumentException("list is empty");
            logger.warn("Argument Warning : list is empty, nothing happend");
            return 0;
        }

        if(parameterList.size() < MIN_BATCH_COUNT) {
            return unitBatch(selector, parameterList);
        }

        if(logger.isDebugEnabled()) {
            int iCnt = 0, uCnt = 0, dCnt = 0;
            for(Map row : parameterList) {
                RowType type = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));
                switch(type) {
                    case INSERT: iCnt++; break;
                    case UPDATE: uCnt++; break;
                    case DELETE: dCnt++; break;
                }
            }
            logger.debug("######################");
            logger.debug("# batch Execution Info");
            logger.debug("# insert statement : " + selector.select(RowType.INSERT) + ", rows : "+ iCnt);
            logger.debug("# update statement : " + selector.select(RowType.UPDATE) + ", rows : "+ uCnt);
            logger.debug("# delete statement : " + selector.select(RowType.DELETE) + ", rows : "+ dCnt);
            logger.debug("######################");
        }

        Map<String, List> batchItemMap = getBatchItemMap(selector, parameterList);
        int cnt = executeBatch(batchItemMap);

        if(parameterList.size()>0 && cnt == 0) {
            logger.warn("row count for INSERT, UPDATE, or DELETE statements is zero");
        }

        return cnt;
    }

    /**
     * batch 처리를 위한 SqlMap Satement id 와 parameter list 의 Map 객체를 생성해 리턴한다.
     * 
     * @param statementName SqlMap Statement id
     * @param list parameter list
     * @return SqlMap Statement id 를 key 로 갖고, parameter list 를 value 로 갖는 Map 객체
     */
    private Map<String, List> getBatchItemMap(String statementName, List<Map> list) {
        Map<String, List> batchItemMap = new HashMap<String, List>();
        batchItemMap.put(statementName, list);
        return batchItemMap;
    }

    /**
     * batch 처리를 위한 SqlMap Statement id 와 parameter list 의 Map 객체를 생성해 리턴한다.
     * parameter list 를 MiPlatform 의 row type 에 따라 분류하는 역할을 한다.
     *  
     * @param selector RowType 에 따른 SqlMap query id를 리턴해주는 StatementSelector 객체
     * @param list RowType 정보를 포함한 parameter List 객체
     */
    @SuppressWarnings("unchecked")
    private Map<String, List> getBatchItemMap(StatementSelector selector, List<Map> list) {
        Map<String, List> batchItemMap = new HashMap<String, List>();
        
        List<Map> insertList = new ArrayList();
        List<Map> updateList = new ArrayList();
        List<Map> deleteList = new ArrayList();
        for(Map row : list) {
            RowType rowType = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));
            switch(rowType) {
                case INSERT:
                    insertList.add(row);
                    break;
                case UPDATE:
                    updateList.add(row);
                    break;
                case DELETE:
                    deleteList.add(row);
                    break;
                default:
                    break;
            }
        }
        batchItemMap.put(selector.select(RowType.INSERT), insertList);
        batchItemMap.put(selector.select(RowType.UPDATE), updateList);
        batchItemMap.put(selector.select(RowType.DELETE), deleteList);
        
        return batchItemMap;
    }
    
    /**
     * Mybatis용 batch 처리 구현 메소드.
     * 
     * 묶음처리를 위해 묶음별로 DB에 반영되야하므로 Transactional 어노테이션을 사용함 
     * 
     * @param batchItemMap batch 처리 대상 statement id 와 parameter list를 담은 Map 객체
     * @return 전체 batch 처리 건수
     * 
     */
    @Transactional
    private int executeBatch(final Map<String, List> batchItemMap) throws Exception {
    	MyBatisConnect connect = new MyBatisConnect();
    	SqlSessionTemplate sqlSessionTemplate  = connect.batchSqlSessionTemplate();

        if(logger.isDebugEnabled()) {
            logger.debug("batch executor start batch");
        }
        
        Iterator<String> iter = batchItemMap.keySet().iterator();
        while(iter.hasNext()) {
            String statementName = iter.next();
            if(logger.isDebugEnabled()) {
                logger.debug("batch executor batch statement : " + statementName);
            }
            List parameterList = batchItemMap.get(statementName);
            for(Object param : parameterList) {
            	sqlSessionTemplate.update(statementName, param);
            	// batchSqlSessionTemplate.update(“mapper.updateInfos", params);
            }
        }
        
    	sqlSessionTemplate.commit();    	
        try {
            int updateCount = 0;
            List<BatchResult> results;
            results = (List<BatchResult>)sqlSessionTemplate.flushStatements();   
            for(BatchResult br : results) {
                for(int code : br.getUpdateCounts()) {
                    if(code == Statement.SUCCESS_NO_INFO) {
                        updateCount++;
                    }
                }
            }     	
            if(logger.isDebugEnabled()) {
                logger.debug("batch executor update count : " + updateCount);
            }
            return Integer.valueOf(updateCount);
        }catch(BatchExecutorException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		//do something..
	}

}
