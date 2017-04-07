package myspring.sample.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import myspring.sample.service.Board2Service;
import myspring.sample.vo.Board2VO;

@Controller
public class Board2Controller {

    private static final Logger logger = LoggerFactory.getLogger(Board2Controller.class);

    @Autowired
    private Board2Service board2Service;

    @RequestMapping(value = "/sample/selectOneBoard2", method = RequestMethod.GET)
    public String selectOneBoard2(Locale locale, Model model) throws Exception {
        logger.info("Welcome selectOneBoard2! the client locale is "+ locale.toString());
        logger.info("Autowired :  "+ board2Service.selectOneBoard2());

        model.addAttribute("val", (Board2VO)board2Service.selectOneBoard2() );

        return "sample/selectOneBoard2";
    }

    @RequestMapping(value = "/sample/selectListBoard2", method = RequestMethod.GET)
    public ModelAndView selectListBoard2(Locale locale, Model model) throws Exception {    	
		logger.info("Welcome selectListBoard2! The client locale is {}.", locale);
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("sample/selectListBoard2"); // 이동할 jsp 이름

    	List<Board2VO> list = board2Service.selectListBoard2();
    	logger.info("selectListBoard2 result list >>>>>"+list.toString());
		view.addObject("list", list ); // jsp 로 넘길 데이터

		return view;
    }
}
