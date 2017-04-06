package myspring.common.mybatis;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;

public class ExecutorSynchronizationManager {

	private static final Log logger = LogFactory.getLog(ExecutorSynchronizationManager.class);
	
	private static final ThreadLocal<ExecutorType> currentExecutorType = new ThreadLocal<ExecutorType>();
	
	public static ExecutorType getCurrentExecutorType() {
		return currentExecutorType.get();
	}
	
	public static void beginBatchExecutorMode() {
		currentExecutorType.set(ExecutorType.BATCH);
		if(logger.isDebugEnabled()){
			logger.debug("Begin JDBC Batch execute mode for current SqlSession in current thread!");
		}
	}
	
	public static void endBatchExecutorMode() {
		currentExecutorType.remove();
		if(logger.isDebugEnabled()){
			logger.debug("End JDBC Batch execute mode for current SqlSession in current thread!");
		}
	}
}
