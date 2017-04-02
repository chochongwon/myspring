package myspring.batch.listener;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import myspring.batch.launcher.JobRuntime;

public class TaskletCustomJobListener implements JobExecutionListener {
	private static final Logger logger = Logger.getLogger(TaskletCustomJobListener.class);

	@Override
	public void afterJob(JobExecution exec) {
		logger.info(">>> Job Executed: " + exec.getJobInstance().getJobName() + " : Batch Status: " + exec.getStatus());
	}

	@Override
	public void beforeJob(JobExecution exec) {
		logger.info(">>> Job To Be Executed: " + exec.getJobInstance().getJobName());
	}

}
