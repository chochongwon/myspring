package myspring.app.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myspring.app.dao.Board2DAO;
import myspring.app.vo.Board2VO;


@Service
public class Board2ServiceImpl implements Board2Service {

    @Autowired
    private Board2DAO board2DAO;

    public Board2VO selectOneBoard2() {
        return (Board2VO)board2DAO.selectOneBoard2();
    }
    
	public ArrayList<Board2VO> selectListBoard2() {
		return (ArrayList<Board2VO>)board2DAO.selectListBoard2();
	}
}
