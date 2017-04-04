package myspring.sample.dao;

import java.util.List;

import myspring.sample.vo.Board2VO;

public interface Board2DAO {
	
    Board2VO selectOneBoard2();

	List selectListBoard2();
}
