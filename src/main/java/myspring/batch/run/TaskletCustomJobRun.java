package myspring.batch.run;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import myspring.batch.launcher.JobRuntime;

public class TaskletCustomJobRun {
	private static final Logger logger = Logger.getLogger(JobRuntime.class);

	public static void main(String[] args) {
		try {
			String[] springConfig  =
				{
					"jobs/taskletCustomJob.xml"
				};

			ApplicationContext context =
					new ClassPathXmlApplicationContext(springConfig);

			JobRuntime jobRuntime = (JobRuntime) context.getBean("jobRuntime");
			jobRuntime.start();
			
		} catch (Exception e) {
			logger.info("Launcher has failed", e);
			System.exit(-1);
		}
		logger.info("Launcher has done");
	}

}
