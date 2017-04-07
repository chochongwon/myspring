package myspring.sample.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myspring.sample.dao.mapper2.Board2DAO;
import myspring.sample.vo.Board2VO;


@Service
public class Board2ServiceImpl implements Board2Service {

    @Autowired
    private Board2DAO board2DAO;

    public Board2VO selectOneBoard2() throws Exception {
        return (Board2VO)board2DAO.selectOneBoard2();
    }
    
	public ArrayList<Board2VO> selectListBoard2() throws Exception {
		return (ArrayList<Board2VO>)board2DAO.selectListBoard2();
	}
}
