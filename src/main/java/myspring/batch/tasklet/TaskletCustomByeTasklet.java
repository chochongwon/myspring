package myspring.batch.tasklet;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import myspring.batch.listener.TaskletCustomStepListener;

public class TaskletCustomByeTasklet implements Tasklet {
	private static final Logger logger = Logger.getLogger(TaskletCustomByeTasklet.class);

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		logger.info("Goodbye Spring Batch!!");
		return RepeatStatus.FINISHED;
	}

}
