package myspring.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TestInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(TestInterceptor.class);
	
	// 컨트롤러 실행 직전에 수행된다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("preHandle call........");
		if(handler instanceof HandlerMethod) {
			// HanlderMethod 는 후에 실행할 컨트롤러의 메소드이다.
			// 메소드명, 인스턴스, 타입, 사용된 Annotation 들을 확인할 수 있다.
			HandlerMethod method = (HandlerMethod) handler;
			logger.info("handler method name : " + method.getMethod().getName());
		}
		return true;
	}
	
	// 컨트롤러 실행 직후에 수행된다.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) 
		throws Exception {
		logger.info("postHandle call.........");
	}
	
	// View 렌더링이 끝난 직후에 수행된다.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
		throws Exception {
		logger.info("afterCompletion call.......");
	}
	
	// 비동기 호출 시 수행된다.
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) 
		throws Exception {
		logger.info("afterConcurrentHandlingStarted call........");
	}
}
