package myspring.sample.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import myspring.sample.common.CommandMap;

/*
 * HandlerMethodArgumentResolver를 상속하여 supportsParameter 메서드와 resolveArgument 메서드를 구현한다. 
 */
public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver{
	/*
	 * Resolver가 적용가능한지 검사하는 역할을 한다.
	 * 컨트롤러의 파라미터가 CommandMap 클래스인지 검사하도록 하였다.
	 */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return CommandMap.class.isAssignableFrom(parameter.getParameterType());
    }
    /*
     * 파라미터와 기타 정보를 받아서 실제 객체를 반환한다.
     * CommandMap 객체를 생성하여 request에 담겨있는 모든 키와 값을 commandMap에 저장하여 반환한다.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CommandMap commandMap = new CommandMap();
         
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Enumeration<?> enumeration = request.getParameterNames();
         
        String key = null;
        String[] values = null;
        while(enumeration.hasMoreElements()){
            key = (String) enumeration.nextElement();
            values = request.getParameterValues(key);                       
            if(values != null){
                commandMap.put(key, (values.length > 1) ? values:values[0] );
            }
        }
        return commandMap;
    }
}
