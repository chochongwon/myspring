package myspring.sample.dao.mapper2;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import myspring.sample.vo.Board1TransferVO;
import myspring.sample.vo.Board1VO;
import myspring.sample.vo.Board2VO;

public class Board1DAOImpl implements Board1DAO {
    private SqlSession sqlSession;
    
    private Board2VO board2VO = new Board2VO();
    private Board1TransferVO board1TransferVO = new Board1TransferVO();

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
	@Override
	public List<Board1VO> selectListBoard1() throws Exception {
        return sqlSession.selectList("Board1Service.selectListBoard1");
	}

	@Override
	public Board1VO selectOneBoard1(Integer idx) throws Exception {
        return (Board1VO) sqlSession.selectOne("Board1Service.selectOneBoard1");
	}

	@Override
	public void mapProcedureListBoard1(Map map) throws Exception {
        sqlSession.selectList("Board1Service.mapProcedureListBoard1", map);
	}

	@Override
	public void procedureListBoard1(Board1TransferVO container) throws Exception {
        sqlSession.selectList("Board1Service.procedureListBoard1", container);
	}

	@Override
	public void anonymousBlockListBoard1(Board1TransferVO container) throws Exception {
        sqlSession.selectList("Board1Service.anonymousBlockListBoard1", container);
	}
}
