package myspring.sample.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import myspring.sample.common.AbstractDAO;

// @Repository 은 이클래스가 DAO임을 선언하고 sampleDAO 라는 이름으로 작성한다.
// sampleServiceImpl에서 @Resource(name="sampleDAO")로 bean을 수동으로 등록하였고, 
// 거기서 사용된 빈이 방금 작성한 SampleDAO 클래스다.
@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO {
	
	// @SuppressWarning 은 일반적인 컴파일러 경고를 제외시키도록 옵션을 줄 수 있다.
	// 1. all : 모든 경고를 억제
	// 2. cast : 캐스트 연산자 관련 경고 억제
	// 3. dep-ann : 사용하지 말아야 할 주석 관련 경고 억제
	// 4. deprecation : 사용하지 말아야 할 메소드 관련 경고 억제
	// 5. fallthrough : switch문에서의 break 누락 관련 경고 억제
	// 6. finally : 반환하지 않는 finally 블럭 관련 경고 억제
	// 7. null : null 분석 관련 경고 억제
	// 8. rawtypes : 제네릭을 사용하는 클래스 매개 변수가 불특정일 때의 경고 억제
	// 9. unchecked : 검증되지 않은 연산자 관련 경고 억제
	// 10. unused : 사용하지 않는 코드 관련 경고 억제
	// 11. 기타 hiding, incomplete-switch, nls, restriction, serial, static-access 등
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map)  throws Exception {
		// 모든 쿼리는 "Mapper NAMESPACE . SQL ID" 의 구조로 구성된다.
		// sample.selectBoardList 는 쿼리이름, map 는 변수들
		return (List<Map<String, Object>>)selectList("sample.selectBoardList", map);
    }
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardListPagingAjax(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectListPagingAjax("sample.selectBoardListPaging", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardListPagingEgov(Map<String, Object> map) {
		return (Map<String, Object>)selectListPagingEgov("sample.selectBoardListPaging", map);
	}
	
	public int insertBoard(Map<String, Object> map)  throws Exception{
		return (Integer)insert("sample.insertBoard", map);
	}

	public int updateHitCnt(Map<String, Object> map) throws Exception{
		return (Integer)update("sample.updateHitCnt", map);		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception{
	    return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
	}

	public int updateBoard(Map<String, Object> map) throws Exception{
		return (Integer)update("sample.updateBoard", map);
	}

	public int deleteGbBoard(Map<String, Object> map) throws Exception{
		return (Integer)update("sample.deleteGbBoard", map);
	}

	public int deleteBoard(Map<String, Object> map) throws Exception{
		return (Integer)delete("sample.deleteBoard", map);
	}
	
	public int insertFile(Map<String, Object> map) throws Exception{
		return (Integer)insert("sample.insertFile", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("sample.selectFileList", map);
	}
	
	public int deleteFileList(Map<String, Object> map) throws Exception{
		return (Integer)update("sample.deleteFileList", map);
	}
	 
	public int updateFile(Map<String, Object> map) throws Exception{
		return (Integer)update("sample.updateFile", map);
	}

}