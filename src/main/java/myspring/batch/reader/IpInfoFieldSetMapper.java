package myspring.batch.reader;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import myspring.batch.model.IpInfo;

public class IpInfoFieldSetMapper implements FieldSetMapper<IpInfo> {

	@Override
	public IpInfo mapFieldSet(FieldSet fs) throws BindException {
        String ip = fs.readString("ip");

        return new IpInfo(ip);
	}

}
