package myspring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestInterceptorController {
	private static final Logger logger = LoggerFactory.getLogger(TestInterceptorController.class);

	@RequestMapping(value = "/app/testInterceptor.do", method = RequestMethod.GET)
	public String testInterceptor(Model model) {
		logger.info("call testInterceptor.do");
		return "app/testInterceptor";
	}
	
}
