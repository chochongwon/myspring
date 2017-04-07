package myspring.sample.service;

import java.util.ArrayList;

import myspring.sample.vo.Board2VO;

public interface Board2Service {
    Board2VO selectOneBoard2() throws Exception; // 서비스가 데이터를 가져올때 Board2VO 로 가져오겠다는 의미
    
    public ArrayList<Board2VO> selectListBoard2() throws Exception;	
}
