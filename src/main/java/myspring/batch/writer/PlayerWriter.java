package myspring.batch.writer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import myspring.batch.dao.PlayerDao;
import myspring.batch.model.PlayerBean;

public class PlayerWriter implements ItemWriter<PlayerBean> {
	private static final Logger logger = Logger.getLogger(PlayerWriter.class);
	
	private PlayerDao dao;
	
	public void setDao(PlayerDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void write(List<? extends PlayerBean> players) throws Exception {
		for(PlayerBean player: players) {
			dao.save(player);
		}
		logger.info("Player Writed To DB: " + players + "\n");
	}

}
