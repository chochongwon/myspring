package myspring.common.batch.core;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import myspring.common.util.DateUtil;

public class SimpleJobParametersIncrementer implements JobParametersIncrementer {
    private final static String BATCH_RUN_TOKEN = "batch.run.token";

	@Override
	public JobParameters getNext(JobParameters jobParameters) {
        String token = DateUtil.getDateString() + RandomStringUtils.randomAlphanumeric(10);
        
        if (jobParameters == null || jobParameters.isEmpty()) {
            return new JobParametersBuilder().addString(BATCH_RUN_TOKEN, token).toJobParameters();
        }
        
        return new JobParametersBuilder(jobParameters).addString(BATCH_RUN_TOKEN, token).toJobParameters();
	}

}
