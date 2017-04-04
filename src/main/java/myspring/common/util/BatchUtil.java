package myspring.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;

import myspring.common.batch.core.SimpleJobRegistry;

public abstract class BatchUtil {
    /**
     * Commons Logger for this Class
     */
    private final static Log logger = LogFactory.getLog(BatchUtil.class);
    
    public static List<Map<String, String>> getBatchInfoDataset(SimpleJobRegistry jobRegistry) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(String jobName : jobRegistry.getJobNames()) {
            Map<String, String> row = new HashMap<String, String>();
            Job job = jobRegistry.getJob(jobName);
            row.put("SYSTEM_ID", jobRegistry.getServerId());
            row.put("JOB_NAME", jobName);
            row.put("JOB_CLASS", job.getClass().getName());
            row.put("JOB_CLASS_SIMPLE_NAME", job.getClass().getSimpleName());
            row.put("JOB_RESTARTABLE", Boolean.toString(job.isRestartable()));
            row.put("JOB_PARAMETERS_INCREMENTER", String.valueOf(job.getJobParametersIncrementer()));
            row.put("JOB_PARAMETERS_VALIDATOR", String.valueOf(job.getJobParametersValidator()));
            list.add(row);
        }
        return list;
    }
}
