package myspring.batch.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import myspring.batch.listener.TaskletCustomStepListener;

public class ReportFieldSetMapper implements FieldSetMapper<Report> {
	private static final Logger logger = Logger.getLogger(TaskletCustomStepListener.class);
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Report mapFieldSet(FieldSet fieldSet) throws BindException {
		Report report1 = new Report();
		report1.setId(fieldSet.readInt(0));
		report1.setSales(fieldSet.readBigDecimal(1));
		report1.setQty(fieldSet.readInt(2));
		report1.setStaffName(fieldSet.readString(3));
		String date = fieldSet.readString(4);
		try {
			report1.setDate(dateFormat.parse(date)); // default format yyyy-MM-dd
		} catch (ParseException e) {
			logger.info("parse error : ", e);
		}
		
		return report1;
	}
}
