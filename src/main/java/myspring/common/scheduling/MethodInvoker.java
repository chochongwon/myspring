package myspring.common.scheduling;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.support.MethodInvokingRunnable;

import myspring.common.util.AssertUtil;

/**
 * Method Invoking Utility
 * TaskExecutor 를 이용해 오브젝트 메소드 invoke
 * TaskExecutor 에 따라 비동기/동기 방식 Method invoke
 * @author 
 *
 */
public class MethodInvoker implements InitializingBean {
    /** TaskExecutor */
    private TaskExecutor taskExecutor;

    /**
     * @return the taskExecutor
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    /**
     * @param taskExecutor the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
    
    /**
     * 비동기 메소드 호출
     * @param targetObject 메소드 호출 대상 Object
     * @param targetMethod 호출 메소드 명
     * @param arguments 메소드 파라미터 리스트
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public void invoke(Object targetObject, String targetMethod, Object ... arguments) throws ClassNotFoundException, NoSuchMethodException {
        MethodInvokingRunnable task = new MethodInvokingRunnable();
        task.setTargetObject(targetObject);
        task.setTargetMethod(targetMethod);
        task.setArguments(arguments);
        task.prepare();
        taskExecutor.execute(task);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
        AssertUtil.notNull(taskExecutor, "taskExecutor must not null");
	}

}
