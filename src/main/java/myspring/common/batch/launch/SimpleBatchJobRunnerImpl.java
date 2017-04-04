package myspring.common.batch.launch;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;

import myspring.common.batch.core.CustomJobParametersConverter;
import myspring.common.util.AssertUtil;

/**
 * Simple Batch Job Runner
 * JobLauncer 를 이용하여 Batch Job 을 구동시킨다.
 * 등록된 JobLauncher 의 TaskExecutor 에 따라 동기/비동기 방식으로 동작할 수 있다.
 */
public class SimpleBatchJobRunnerImpl implements JobRunner {
    
    private static final String ILLEGAL_STATE_MSG = "Illegal state (only happens on a race condition): %s with name=%s and parameters=%s";
    
    private JobRegistry jobRegistry;
    
    private JobLauncher jobLauncher;
    
    private JobRepository jobRepository;
    
    private CustomJobParametersConverter jobParametersConverter;

    /**
     * Commons Logger for this Class
     */
    private final static Log logger = LogFactory.getLog(SimpleBatchJobRunnerImpl.class);

    public void setJobRegistry(JobRegistry jobRegistry) {
        this.jobRegistry = jobRegistry;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    public void setJobParametersConverter(CustomJobParametersConverter jobParametersConverter) {
        this.jobParametersConverter = jobParametersConverter;
    }

    /**
     * Batch Job 을 비동기적으로 실행한다.
     * 이미 완료된 Job 은 실행할 수 없다.
     * 
     * @param jobName Batch Job 이름
     * @param parameters Batch Parameter Map
     * @return JobExecution 객체
     * @throws NoSuchJobException Job 이름에 해당하는 Job을 JobRegistry 로부터 찾지 못할때
     * @throws JobExecutionException Job 실행중 발생
     */
	@Override
	public JobExecution run(String jobName, Map parameters) throws NoSuchJobException, JobExecutionException {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        return run(jobName, jobParameters);
	}

    /**
     * Batch Job 을 비동기적으로 실행한다.
     * 이미 완료된 Job 은 실행할 수 없다.
     * 
     * @param jobName Batch Job 이름
     * @param parameters Batch Parameter Properties
     * @return JobExecution 객체
     * @throws NoSuchJobException Job 이름에 해당하는 Job을 JobRegistry 로부터 찾지 못할때
     * @throws JobExecutionException Job 실행중 발생
     */
	@Override
	public JobExecution run(String jobName, Properties parameters) throws NoSuchJobException, JobExecutionException {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        return run(jobName, jobParameters);
	}
    
    /**
     * Batch Job 을 비동기적으로 실행한다.
     * 이미 완료된 Job 은 실행할 수 없다.
     * 
     * @param jobName Batch Job 이름
     * @param jobParameters Batch Job Parameters
     * @return JobExecution 객체
     * @throws NoSuchJobException Job 이름에 해당하는 Job을 JobRegistry 로부터 찾지 못할때
     * @throws JobExecutionException Job 실행중 발생
     */    
    private JobExecution run(String jobName, JobParameters jobParameters) throws NoSuchJobException, JobExecutionException {
        Job job = jobRegistry.getJob(jobName);
        if(logger.isDebugEnabled()) {
            logger.debug("job found : " + job);
        }
        
        if(logger.isDebugEnabled()) {
            logger.debug("launch job with job parameters : " + jobParameters);
        }
        return jobLauncher.run(job, jobParameters);        
    }

    /**
     * Batch Job을 비동기적으로 (재)실행한다.
     * 이미 완료된 작업의 경우에도 실행할 수 있다.
     * 단, JobParameterIncrementer 가 지정되어 있어야 한다.
     * 
     * @param jobName Batch Job 이름
     * @param parameters Batch Parameter Map
     * @return JobExecution 객체
     * @throws NoSuchJobException 
     * @throws JobExecutionException 
     */
	@Override
	public JobExecution rerun(String jobName, Map parameters) throws NoSuchJobException, JobExecutionException {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        return rerun(jobName, jobParameters);
	}

    /**
     * Batch Job을 비동기적으로 (재)실행한다.
     * 이미 완료된 작업의 경우에도 실행할 수 있다.
     * 단, JobParameterIncrementer 가 지정되어 있어야 한다.
     * 
     * @param jobName Batch Job 이름
     * @param parameters Batch Parameter Properties
     * @return JobExecution 객체
     * @throws NoSuchJobException 
     * @throws JobExecutionException 
     */
	@Override
	public JobExecution rerun(String jobName, Properties parameters) throws NoSuchJobException, JobExecutionException {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        return rerun(jobName, jobParameters);
	}

    /**
     * Batch Job을 비동기적으로 (재)실행한다.
     * 이미 완료된 작업의 경우에도 실행할 수 있다.
     * 단, JobParameterIncrementer 가 지정되어 있어야 한다.
     * 
     * @param jobName Batch Job 이름
     * @param parameters Batch Job Parameter Object
     * @return JobExecution 객체
     * @throws NoSuchJobException 
     * @throws JobExecutionException 
     */
    private JobExecution rerun(String jobName, JobParameters jobParameters) throws NoSuchJobException, JobExecutionException {
        JobExecution jobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if(jobExecution != null ) {
            BatchStatus status = jobExecution.getStatus();
            if(status == BatchStatus.COMPLETED || status == BatchStatus.ABANDONED) {
                Job job = jobRegistry.getJob(jobName);
                if(logger.isDebugEnabled()) {
                    logger.debug("job found : " + job);
                }
               
                JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
                if (incrementer == null) {
                    throw new JobParametersNotFoundException("No job parameters incrementer found for job=" + jobName);
                }
                jobParameters = incrementer.getNext(jobParameters);
                
                if(logger.isDebugEnabled()) {
                    logger.debug("launch job with job parameters : " + jobParameters);
                }
                try {
                    return jobLauncher.run(job, jobParameters);
                }
                catch (JobExecutionAlreadyRunningException e) {
                    throw new UnexpectedJobExecutionException(String.format(ILLEGAL_STATE_MSG, "job already running", jobName, jobParameters), e);
                }
                catch (JobRestartException e) {
                    throw new UnexpectedJobExecutionException(String.format(ILLEGAL_STATE_MSG, "job not restartable", jobName, jobParameters), e);
                }
                catch (JobInstanceAlreadyCompleteException e) {
                    throw new UnexpectedJobExecutionException(String.format(ILLEGAL_STATE_MSG, "job instance already complete", jobName, jobParameters), e);
                }            
            }
        }
        return run(jobName, jobParameters);
    }    

	@Override
	public void afterPropertiesSet() throws Exception {
        AssertUtil.notNull(jobRepository, "JobRepository must not null");
        AssertUtil.notNull(jobLauncher, "JobLauncher must not null");
        AssertUtil.notNull(jobRegistry, "JobRegistry must not null");
        AssertUtil.notNull(jobParametersConverter, "JobParametersConverter must not null");
	}

}
