package myspring.batch.writer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import myspring.batch.model.IpInfo;

public class IpClassfier implements Classifier<IpInfo, ItemWriter<IpInfo>> {
    private Map<Boolean, ItemWriter<IpInfo>> writerMap = new HashMap<Boolean, ItemWriter<IpInfo>>();

	@Override
	public ItemWriter<IpInfo> classify(IpInfo ipInfo) {
        return writerMap.get(ipInfo.isFiltered());
	}

    public void setWriterMap(Map<Boolean, ItemWriter<IpInfo>> writerMap) {
        this.writerMap = writerMap;
    }
}
