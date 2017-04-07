package myspring.sample.dao.mapper2;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import myspring.sample.vo.Board2VO;

@Repository
public class Board2DAOImpl implements Board2DAO {

    private SqlSessionTemplate sqlSessionTemplate;
    private Board2VO board2VO = new Board2VO();

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public Board2VO selectOneBoard2() throws Exception {
        return (Board2VO) sqlSessionTemplate.selectOne("Board2Service.selectOneBoard2");
    }
    
    public List selectListBoard2() throws Exception {
        return sqlSessionTemplate.selectList("Board2Service.selectListBoard2");
    }
}
