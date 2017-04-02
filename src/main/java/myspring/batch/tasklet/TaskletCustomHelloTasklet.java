package myspring.batch.tasklet;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TaskletCustomHelloTasklet implements Tasklet {
	private static final Logger logger = Logger.getLogger(TaskletCustomHelloTasklet.class);

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		logger.info("Spring Batch Hellow Tasklet!!");
		return RepeatStatus.FINISHED;
	}

}
