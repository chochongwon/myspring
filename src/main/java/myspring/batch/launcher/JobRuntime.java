package myspring.batch.launcher;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import myspring.batch.listener.InMemoryJobListener;
import myspring.batch.run.InMemoryJobRun;

public class JobRuntime {
	private static final Logger logger = Logger.getLogger(JobRuntime.class);
	
	private JobLauncher jobLauncher;
	
	private Job job;
	
	public JobRuntime() {
		
	}
	
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public void launch() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addLong("batch-date",
				System.currentTimeMillis()).toJobParameters();
		
		JobExecution exec = jobLauncher.run(job, jobParameters);
		logger.info("Exit Status : " + exec.getStatus());
	}
}
