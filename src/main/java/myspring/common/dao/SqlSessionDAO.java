package myspring.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import myspring.common.dao.support.DefaultStatementSelector;
import myspring.common.dao.support.UpsertStatementSelector;
import myspring.common.mybatis.CustomSqlSessionDaoSupport;
import myspring.common.util.AssertUtil;
/**
 * 시스템 범용 DAO class.
 * <p>
 * 개별 dao 선언 없이 SqlMapClient 의 기능을 사용하기 위해 만든 범용 DAO class.
 * 단순, CustomSqlSessionDaoSupport 의 wrapper class 이자 편의성을 제공하기 위한 Class 이다.
 * <p>
 * 별도 dao 선언이 필요하다면 CustomSqlSessionDaoSupport 를 상속하여 구현해야 한다.
 * 
 * @author ddam40
 */
public class SqlSessionDAO extends CustomSqlSessionDaoSupport {

    /**
     * Commons Loggin Instance
     */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * SqlMap 조회 처리 Template Method.
     * 
     * <p>select type의 SqlMap statement 를 실행하고 결과를 List&lt;Map&gt; 형태로 반환한다.
     * <p>파라미터 세팅을 위한 Object를 전달받아 사용한다.
     * <p>일반적으로 parameter Object 는 Map 형태이며, 리턴되는 객체는 dataset으로 변환하기 위한 List&lt;Map&gt; 객체이다.
     *  
     * @param statementName SqlMap Statement Id
     * @param parameterObject 파라미터 세팅을 위한 Object 객체 
     * @return 조회 결과 List&lt;Map&gt;
     */
    @Override
    public List<Map> select(String statementName, Object parameterObject) throws Exception {
        return super.select(statementName, parameterObject);
    }

    /**
     * SqlMap 조회 처리 Template Method.
     * 
     * <p>select type의 SqlMap statement 를 실행하고 결과를 List&lt;Map&gt; 형태로 반환한다.
     * <p>parameter 전달이 필요 없는 경우에 사용한다.
     * @param statementName SqlMap Statement Id
     * @return 조회 결과 List&lt;Map&gt;
     */
    @Override
    public List<Map> select(String statementName) throws Exception {
        return super.select(statementName);
    }

    /**
     * SqlMap 단건 조회 처리 Template Method.
     * <p>조회 결과는 반드시 1row 여야 한다. 하나 이상의 Record가 리턴된다면 예외가 발생한다.
     * @param statementName SqlMap Statement Id
     * @return 조회 결과 Object 
     */
    @Override
    public Object selectOne(String statementName) throws Exception {
        return super.selectOne(statementName); 
    }

    /**
     * SqlMap 단건 조회 처리 Template Method.
     * <p>조회 결과는 반드시 1row 여야 한다. 하나 이상의 Record가 리턴된다면 예외가 발생한다.
     * <p>파라미터 세팅을 위한 Object를 전달받아 사용한다. 
     * @param statementName SqlMap Statement Id
     * @param parameterObject 파라미터 세팅을 위한 Object 객체 
     * @return 조회 결과 Object 
     */
    @Override    
    public Object selectOne(String statementName, Object parameterObject) throws Exception {
        return super.selectOne(statementName, parameterObject); 
    }
    
    @Override
    public List queryForList(String statementName) {
        return super.queryForList(statementName);
    }

    @Override
    public List queryForList(String statementName, Object parameterObject) {
        return super.queryForList(statementName, parameterObject);
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
    @Override
    public int insert(String statementName, Object parameterObject) throws Exception {
        return super.insert(statementName, parameterObject);
    }

    /**
     * SqlMap 등록 처리 Template Method.
     * 
     * <p>insert type의 SqlMap Statement 를 실행하고, insert 된 rowCount를 반환 한다.
     * @param statementName sqlMap Statement id
     * @return insert 된 rowCount
     */
    @Override
    public int insert(String statementName) throws Exception {
        return super.insert(statementName);
    }
    
    /**
     * SqlMap 등록 batch 처리 Template Method.
     * <p>insrt type의 SqlMap Statement 를 파라미터로 전달된 List 의 size 만큼 반복 처리한다.
     * <p>insert 된 전체 rowCount 를 반환 한다.
     * 
     * @param statementName SqlMap Statement id
     * @param parameterList 다건 처리를 위한 파라미터 List
     * @return 전체 등록된 rowCount
     */
    public int insert(String statementName, List parameterList) throws Exception {
        return batch(statementName, parameterList);
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
    @Override
    public int update(String statementName, Object parameterObject) throws Exception {
        return super.update(statementName, parameterObject);
    }

    /**
     * SqlMap 수정 처리 Template Method.
     * 
     * <p>update type의 SqlMap Statement 를 실행하고, update 된 rowCount를 반환 한다.
     * @param statementName sqlMap Statement id
     * @return update 된 rowCount
     */
    @Override
    public int update(String statementName) throws Exception {
        return super.update(statementName);
    }
    
    /**
     * SqlMap 수정 batch 처리 Template Method.
     * <p>update type의 SqlMap Statement 를 파라미터로 전달된 List 의 size 만큼 반복 처리한다.
     * <p>update 된 전체 rowCount 를 반환 한다.
     * 
     * @param statementName SqlMap Statement id
     * @param parameterList 다건 처리를 위한 파라미터 List
     * @return 전체 수정된 rowCount
     */    
    public int update(String statmentName, List parameterList) throws Exception {
        return batch(statmentName, parameterList);
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
    @Override
    public int delete(String statementName, Object parameterObject) throws Exception {
        return super.delete(statementName, parameterObject);
    }

    /**
     * SqlMap 삭제 처리 Template Method.
     * 
     * <p>delete type의 SqlMap Statement 를 실행하고, delete 된 rowCount를 반환 한다.
     * @param statementName sqlMap Statement id
     * @return delete 된 rowCount
     */
    @Override
    public int delete(String statementName) throws Exception {
        return super.delete(statementName);
    }
    
    /**
     * SqlMap 삭제 batch 처리 Template Method.
     * <p>delete type의 SqlMap Statement 를 파라미터로 전달된 List 의 size 만큼 반복 처리한다.
     * <p>delete 된 전체 rowCount 를 반환 한다.
     * 
     * @param statementName SqlMap Statement id
     * @param parameterList 다건 처리를 위한 파라미터 List
     * @return 전체 삭제된 rowCount
     */      
    public int delete(String statementName, List parameterList) throws Exception {
        return batch(statementName, parameterList);
    }

    /**
     * 단일 Statement 에 대한 batch 처리 메소드.
     * 
     * 단일 Statement 에 대해 Rowtype 과 무관하게 parameterList 만큼 쿼리를 수행한다.
     * @param statementName SqlMap Statement id
     * @param parameterList 처리 대상 parameter list
     * @return 전체 처리 건수
     */    
    @Override
    public int batch(String statementName, List parameterList) throws Exception {
        return super.batch(statementName, parameterList);
    }
    
    /**
     * Multi row 에 대한 batch 처리.
     * SqlMapDAO.batch 사용 대신, RowType 에 따른 caseBycase 처리
     *
     * @return total row count for INSERT, UPDATE, or DELETE statements
     */
    @Override
    public int unitBatch(String statementName, List parameterList) throws Exception {
        return super.unitBatch(statementName, parameterList);
    }
    
    /**
     * Dataset List 의 rowType 에 따라 insert/update/delete 문을 자동 batch 처리 한다.
     * 
     * rowType 에 따른 query id 는 파라미터로 전달된 statementName의 첫 마디를 변형하여 사용한다.
     * 예) save("namespace.saveQuery", list); 의 각 rowType 별 쿼리명은 다음과 같다.
     * <ul>
     *   <li>RowType.Insert : "namespace.insertQuery"
     *   <li>RowType.Update : "namespace.updateQuery"
     *   <li>RowType.Delete : "namespace.deleteQuery"
     * </ul>
     *
     * @param statmentName 대표 Statement id
     * @param parameterList batch 파라미터 리스트
     * @return total row count for INSERT, UPDATE, or DELETE statements
     */
    public int save(String statementName, List parameterList) throws Exception {
        return super.batch(new DefaultStatementSelector(statementName), parameterList);
    }
    
    /**
     * Dataset 의 rowType 에 따라 insert/update/delete 문을 자동 batch 처리 한다.
     * 
     * rowType 에 따른 query id 는 파라미터로 전달된 statementName의 첫 마디를 변형하여 사용한다.
     * 예) save("namespace.saveQuery", list); 의 각 rowType 별 쿼리명은 다음과 같다.
     * <ul>
     *   <li>RowType.Insert : "namespace.insertQuery"
     *   <li>RowType.Update : "namespace.updateQuery"
     *   <li>RowType.Delete : "namespace.deleteQuery"
     * </ul>
     *
     * @param statmentName 대표 Statement id
     * @param parameterList batch 파라미터 리스트
     * @return total row count for INSERT, UPDATE, or DELETE statements
     */
    public int save(String statementName, Map parameter) throws Exception {
        AssertUtil.notNull(parameter);
        List<Map> list = new ArrayList<Map>();
        list.add(parameter);
        return save(statementName, list);
    }    
    
    /**
     * Dataset List 의 rowType 에 따라 merge into 문과 delete 문을 자동 batch 처리 한다.
     * 
     * rowType 에 따른 query id 는 파라미터로 전달된 statementName 의 첫 마디를 변형하여 사용한다.
     * 예) upsert("namespace.upsertQuery", list);
     * <ul>
     *   <li>RowType.Insert : "namespace.upsertQuery"
     *   <li>RowType.Update : "namespace.upsertQuery"
     *   <li>RowType.Delete : "namespace.deleteQuery"
     * </ul>
     * 즉, rowType 이 Insert / Update 인 경우는 전달받은 statementName 을 변형없이 사용하고,
     * Delete 인 경우만 첫 마디를 delete 로 변형하여 사용한다.
     * 
     * @param statementName Merge Into 문의 Statement Id
     * @param parameterList batch 파라미터 리스트
     * @return total row count for MERGE INTO or DELETE Statements.
     */
    public int upsert(String statementName, List parameterList) throws Exception {
        return super.batch(new UpsertStatementSelector(statementName), parameterList);
    }

    /**
     * Dataset 의 rowType 에 따라 merge into 문과 delete 문을 자동 batch 처리 한다.
     * 
     * rowType 에 따른 query id 는 파라미터로 전달된 statementName 의 첫 마디를 변형하여 사용한다.
     * 예) upsert("namespace.upsertQuery", list);
     * <ul>
     *   <li>RowType.Insert : "namespace.upsertQuery"
     *   <li>RowType.Update : "namespace.upsertQuery"
     *   <li>RowType.Delete : "namespace.deleteQuery"
     * </ul>
     * 즉, rowType 이 Insert / Update 인 경우는 전달받은 statementName 을 변형없이 사용하고,
     * Delete 인 경우만 첫 마디를 delete 로 변형하여 사용한다.
     * 
     * @param statementName Merge Into 문의 Statement Id
     * @param parameterList batch 파라미터 리스트
     * @return total row count for MERGE INTO or DELETE Statements.
     */
    public int upsert(String statementName, Map parameter) throws Exception {
        AssertUtil.notNull(parameter);
        List<Map> list = new ArrayList<Map>();
        list.add(parameter);
        return upsert(statementName, list);        
    }
}
