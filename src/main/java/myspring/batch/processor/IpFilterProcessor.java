package myspring.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import myspring.batch.model.IpInfo;

public class IpFilterProcessor implements ItemProcessor<IpInfo, IpInfo> {

	@Override
	public IpInfo process(IpInfo item) throws Exception {
        if (item.getIp().startsWith("219.")) {
            item.setFiltered(true);
        }
		return item;
	}
}
