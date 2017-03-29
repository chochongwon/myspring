package myspring.app.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	SessionLocaleResolver localeResolver;
	
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/app/i18n.do", method = RequestMethod.GET)
	public String i18n(Locale locale, HttpServletRequest request, Model model) {
		logger.info("Welcome i18n! The client locale is {}.", locale);
		
		locale = localeResolver.resolveLocale(request);
		
		// localeResolver 로부터 Locale 을 출력해 봅니다. 
		logger.info("Session locale is {}.", localeResolver.resolveLocale(request)); 

		logger.info("site.title : {}", messageSource.getMessage("site.title", null, "default text", locale));
		logger.info("site.count : {}", messageSource.getMessage("site.count", new String[] {"첫번째"}, "default text", locale));
		//logger.info("msg.first : {}", messageSource.getMessage("msg.first", null, "default text", locale)); 
		logger.info("not.exist : {}", messageSource.getMessage("not.exist", null, "default text", locale));
		//logger.info("not.exist 기본값 없음 : {}", messageSource.getMessage("not.exist", null, locale));

		model.addAttribute("siteCount", messageSource.getMessage("msg.first", null, locale));
		
		return "app/i18n";
	}
	
	
	@RequestMapping(value = "/app/home.do", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("app/home"); // 이동할 jsp 이름
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
		String formattedDate = dateFormat.format(date);		
		view.addObject("serverTime", formattedDate ); // jsp 로 넘길 데이터
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		
		HttpServletRequest request = attr.getRequest();
		
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String cPath = request.getContextPath();
		String sPath = request.getServletPath();
		
		String nodeId = System.getProperty("jboss.server.name");
		String hostName = System.getProperty("java.rmi.server.hostname");
		
		String variableString = (String)session.getAttribute("SESSION_TEST_VARIABLE");
		int count = 0;
		if(variableString != null)
		{
			count = Integer.parseInt(variableString);
			count++;
		}
		session.setAttribute("SESSION_TEST_VARIABLE", String.valueOf(count));
				
		view.addObject("basePath", basePath);
		view.addObject("cPath", cPath);
		view.addObject("sPath", sPath);		
		view.addObject("sessionId", session.getId() );
		view.addObject("session", session);
		view.addObject("nodeId", nodeId);
		view.addObject("hostName", hostName);
		view.addObject("count", count);
		
		return view;
	}	

	@RequestMapping(value = "/app/login.do", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		logger.info("Welcome login! The client locale is {}.", locale);
		
		return "app/login";
	}
    @RequestMapping(value="/app/testLoggerInterceptor.do")
    public ModelAndView openSampleList(Map<String,Object> commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("");
        logger.debug("인터셉터 테스트");
         
        return mv;
    }
}