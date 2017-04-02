package myspring.batch.writer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import myspring.batch.dao.OrderDao;
import myspring.batch.model.OrderBean;

public class OrderWriter implements ItemWriter<OrderBean> {
	private static final Logger logger = Logger.getLogger(OrderWriter.class);
	
	private OrderDao dao;
	
	public void setDao(OrderDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void write(List<? extends OrderBean> orders) throws Exception {
		for(OrderBean order : orders){
			dao.save(order);
		}	
		logger.info("Order Writed To DB: " + orders + "\n");
	}

}
