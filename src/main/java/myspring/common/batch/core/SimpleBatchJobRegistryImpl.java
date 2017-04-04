package myspring.common.batch.core;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.configuration.support.MapJobRegistry;

import myspring.common.util.BatchUtil;

public class SimpleBatchJobRegistryImpl extends MapJobRegistry implements SimpleJobRegistry {

    private String serverId;
    
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
    
    public String getServerId() {
        return serverId;
    }
    /**
     * JobRegistry 에 등록된 Job 정보를 List<Map> 형태로 리턴
     */
    public List<Map<String, String>> getJobInfoDataset() throws Exception {
        return BatchUtil.getBatchInfoDataset(this);
    }
}
