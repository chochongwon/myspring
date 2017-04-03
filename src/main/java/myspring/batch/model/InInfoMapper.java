package myspring.batch.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class InInfoMapper implements FieldSetMapper<IpInfo> {

	@Override
	public IpInfo mapFieldSet(FieldSet fs) throws BindException {
		String ip = fs.readString("ip");
		return  new IpInfo(ip);
	}

}
