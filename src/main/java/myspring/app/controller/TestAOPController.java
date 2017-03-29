package myspring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestAOPController {
	private static final Logger logger = LoggerFactory.getLogger(TestAOPController.class);
	
	@RequestMapping(value = "/app/testAOP.do", method = RequestMethod.GET)
	public String testAOP(Model model) {
		logger.info("call testAOP.do");
		
		// 에러발생용 테스트코드
		boolean flag = false;
		if(flag) {
			throw new RuntimeException("TestAOP Exception!");
		}
		
		return "app/testAOP";
	}
}
