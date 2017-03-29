package myspring.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myspring.app.service.Board1Service;
import myspring.app.vo.Board1TransferVO;
import myspring.app.vo.Board1VO;
import myspring.app.dao.Board1DAO;

@Service("board1Service")
public class Board1ServiceImpl implements Board1Service {
	private static final Logger logger = LoggerFactory.getLogger(Board1ServiceImpl.class);
	
	@Autowired
	private Board1DAO board1Dao;
	
	@Override
	@Transactional
	public List<Board1VO> selectListBoard1()  throws Exception {
		return board1Dao.selectListBoard1();
	}

	@Override
	@Transactional
	public Board1VO selectOneBoard1(Integer idx)  throws Exception {
		return board1Dao.selectOneBoard1(idx);
	}

	@Override
	@Transactional
	public Map<String, Object> mapProcedureListBoard1(Map map) throws Exception { 
		logger.info(map.toString());
		board1Dao.mapProcedureListBoard1(map); 
		return map;
	}

	@Override
	@Transactional
	public Board1TransferVO procedureListBoard1(Board1TransferVO container) throws Exception {
		logger.info(container.toString());	
		board1Dao.procedureListBoard1(container);
		return container;
	}
	
	@Override
	@Transactional
	public Board1TransferVO anonymousBlockListBoard1(Board1TransferVO container) throws Exception {
		logger.info(container.toString());	
		board1Dao.anonymousBlockListBoard1(container);
		return container;
	}
}
