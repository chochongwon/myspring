package myspring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestFilterController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);	

	/*
	 * 테스트는 http://localhost:8080/myspring/app/testFilter.do?param=<test> 로 한다.
	 * 입력 파라미터(param) 에서 <, > 를 제거한다.
	 */
	@RequestMapping(value = "/app/testFilter.do", method = RequestMethod.GET)
	public String testFilter(Model model) {
		logger.info("call testFilter.do");
		return "app/testFilter";
	}
}
