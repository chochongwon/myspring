package myspring.batch.run;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import myspring.batch.launcher.JobRuntime;

public class InMemoryJobRun {
	private static final Logger logger = Logger.getLogger(JobRuntime.class);

	public static void main(String[] args) {
		try {
			String[] springConfig  =
				{
					"jobs/inMemoryJob.xml"
				};

			ApplicationContext context =
					new ClassPathXmlApplicationContext(springConfig);

			JobRuntime jobRuntime = (JobRuntime) context.getBean("jobRuntime");
			jobRuntime.launch();
			
		} catch (Exception e) {
			logger.info("Launcher has failed", e);
			System.exit(-1);
		}
		logger.info("Launcher has done");
	}

}
