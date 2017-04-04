package myspring.common.batch.core;

import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;

public class CustomJobParametersConverter extends DefaultJobParametersConverter {
    public JobParameters getJobParameters(Map map) {
        if(map == null || map.isEmpty()) {
            return new JobParameters();
        }
        
        Properties prop = new Properties();
        prop.putAll(map);
        
        return getJobParameters(prop);
    }
}
