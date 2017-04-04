package myspring.sample.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// AOP 임을 알리는 annotation
@Aspect
public class TestAdvice {
	private static final Logger logger = LoggerFactory.getLogger(TestAdvice.class);
	
	// 공통적으로 사용될 포인트컷을 지정한다.
	// com.test.spring 패키지 안의 Controller로 끝나는 클래스의 모든 메소드에 적용된다.
	@Pointcut("execution(* myspring.sample.controller.*Controller.*(..))")
	public void commonPointcut() { }
	
	// Before Advice 다. 위에서 정의한 공통 포인트컷을 사용한다.
	@Before("commonPointcut()")
	public void beforeMethod(JoinPoint jp) throws Exception {
		logger.info("beforeMethod() called.....");
		
		// 호출될 메소드의 인자들을 얻을 수 있다.
		Object arg[] = jp.getArgs();
		
		// 인자의 갯수 출력
		logger.info("args length : " + arg.length);
		
		// 첫 번째 인자의 클래스명 출력
		logger.info("arg0 name : " + arg[0].getClass().getName());
		
		// 호출될 메소드명 출력
		logger.info("method name : " + jp.getSignature().getName());
	}
	
	// After Advice
	@After("commonPointcut()")
	public void afterMethod(JoinPoint jp) throws Exception {
		logger.info("afterMethod() called.....");
	}
	
	// After Returning Advice
	// 이 어드바이스는 반환값을 받을 수 없다.
	@AfterReturning(pointcut="commonPointcut()", returning="returnString")
	public void afterReturningMethod(JoinPoint jp, String returnString) throws Exception {		
		logger.info("afterReturningMethod() called.....");
		// 호출된 메소드 반환값 출력
		logger.info("afterReturningMethod() returnString : " + returnString);
	}
	
	// Around Advice
	// 포인트컷을 직접 지정
	@Around("execution(* myspring.sample.controller.*Controller.*(..))")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("aroundMethod() before called.....");

		Object result = pjp.proceed();
		logger.info("aroundMethod() after called.....");

		return result;
	}
	
	// 예외가 발생했을 때 Advice
	@AfterThrowing(pointcut="commonPointcut()", throwing="exception")
	public void afterThrowingMethod(JoinPoint jp, Exception exception) throws Exception {
		logger.info("afterThrowingMethod() called.....");

		// 발생한 예외메시지를 출력
		logger.info(exception.getMessage());
	}
}
