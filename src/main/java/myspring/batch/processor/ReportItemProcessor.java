package myspring.batch.processor;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import myspring.batch.listener.InMemoryStepListener;
import myspring.batch.model.Report;

public class ReportItemProcessor implements ItemProcessor<Report, Report> {
	private static final Logger logger = Logger.getLogger(InMemoryStepListener.class);
	
	@Override
	public Report process(Report item) throws Exception {
		logger.info("Processing..." + item);
		return item;
	}
}
