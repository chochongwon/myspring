package myspring.sample.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/*
 * action-servlet.xml과 context-aspect.xml에서 <aop:aspectj-autoproxy/>를 사용했었는데 
 * @Aspect 어노테이션을 통해서 bean을 등록시켜주는 역할을 한다.
 * @Aspect 어노테이션을 이용하면 어드바이스에 포인트컷을 직접 지정해 줄 수 있다.
 * 만약, 이런 방식이 되지 않는다면, 포인트컷과 어드바이스를 따로따로 정의해야 한다.
 */
@Aspect
public class LoggerAspect {
    protected Log log = LogFactory.getLog(LoggerAspect.class);
    static String name = "";
    static String type = "";
    
    // Controller, Service, DAO가 실행될 때, 어떤 계층의 어떤 메서드가 실행되었는지를 보여주는 역할을 한다.
    @Around("execution(* myspring..controller.*Controller.*(..)) or execution(* myspring..service.*Impl.*(..)) or execution(* myspring..dao.*DAO.*(..))")
    public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
        type = joinPoint.getSignature().getDeclaringTypeName();
         
        if (type.indexOf("Controller") > -1) {
            name = "Controller  \t:  ";
        }
        else if (type.indexOf("Service") > -1) {
            name = "ServiceImpl  \t:  ";
        }
        else if (type.indexOf("DAO") > -1) {
            name = "DAO  \t\t:  ";
        }
        log.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
        return joinPoint.proceed();
    }

}
