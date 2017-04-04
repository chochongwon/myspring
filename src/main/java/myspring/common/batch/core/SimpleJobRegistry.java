package myspring.common.batch.core;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.configuration.JobRegistry;

public interface SimpleJobRegistry extends JobRegistry {
    public List<Map<String, String>> getJobInfoDataset() throws Exception;
    
    public String getServerId();
    
    public void setServerId(String serverId);
}
