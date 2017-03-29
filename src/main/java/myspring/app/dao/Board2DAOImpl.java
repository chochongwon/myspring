package myspring.app.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import myspring.app.vo.Board2VO;

@Repository
public class Board2DAOImpl implements Board2DAO {

    private SqlSession sqlSession;
    private Board2VO julkyuVO = new Board2VO();

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Board2VO selectOneBoard2() {
        return (Board2VO) sqlSession.selectOne("Board2Service.selectOneBoard2");
    }
    
    public List selectListBoard2() {
        return sqlSession.selectList("Board2Service.selectListBoard2");
    }
}
