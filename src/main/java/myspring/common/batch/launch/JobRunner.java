package myspring.common.batch.launch;

import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.InitializingBean;

public interface JobRunner extends InitializingBean {

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
    public JobExecution run(String jobName, Map parameters) throws NoSuchJobException, JobExecutionException;
    
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
    public JobExecution run(String jobName, Properties parameters) throws NoSuchJobException, JobExecutionException;
    
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
    public JobExecution rerun(String jobName, Map parameters) throws NoSuchJobException, JobExecutionException;

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
    public JobExecution rerun(String jobName, Properties parameters) throws NoSuchJobException, JobExecutionException;

}
