package myspring.batch.listener;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class TaskletCustomStepListener implements StepExecutionListener {
	private static final Logger logger = Logger.getLogger(TaskletCustomStepListener.class);

	@Override
	public ExitStatus afterStep(StepExecution exec) {
		logger.info("### Step Executed: " + exec.getStepName());
		return null;
	}

	@Override
	public void beforeStep(StepExecution exec) {
		logger.info("### Step To Be Executed: " + exec.getStepName());
	}

}
