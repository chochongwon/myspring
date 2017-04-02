package myspring.batch.reader;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import myspring.batch.model.OrderBean;

public class OrderFieldSetMapper implements FieldSetMapper<OrderBean> {

	@Override
	public OrderBean mapFieldSet(FieldSet fs) throws BindException {
		if (fs == null) {
			return null;
		}
		
		OrderBean order = new OrderBean();		
		order.setIsbn(fs.readString("isbn"));
		order.setQuantity(fs.readInt("quantity"));
		order.setPrice(fs.readDouble("price"));
		order.setCustomer(fs.readString("customer"));
		
		return order;
	}

}
