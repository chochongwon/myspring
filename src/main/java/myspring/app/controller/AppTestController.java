package myspring.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import myspring.app.service.AppTestService;
import myspring.sample.common.CommandMap;

@Controller
public class AppTestController {
    protected final Log logger = LogFactory.getLog(getClass()); 
    
    // @Resource 은 필요한 빈(bean)을 수동으로 등록하라는 것이다.
    @Resource(name="appTestService")
    private AppTestService appTestService;

	@RequestMapping(value = "/app/selectListBoard1.do", method = RequestMethod.GET)
	public ModelAndView selectListBoard1(Locale locale, Model model) throws Exception {
    	logger.info("Welcome selectOneBoard2! the client locale is "+ locale.toString());
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("sample/selectListBoard1"); // 이동할 jsp 이름
		Map map = new HashMap();
		map.put("idx", 514);
		map.put("crea_id", "N");
		List<Map> list = appTestService.selectListBoard1("AppTestService.selectListBoard1", map);
		logger.info(list.toString());
		
		view.addObject("list", list ); // jsp 로 넘길 데이터
		
		return view;
	}

    @RequestMapping(value = "/app/selectOneBoard2.do", method = RequestMethod.GET)
    public String selectOneBoard2(Locale locale, Model model) throws Exception {
    	logger.info("Welcome selectOneBoard2! the client locale is "+ locale.toString());

        model.addAttribute("val", appTestService.selectOneBoard2("AppTestService.selectOneBoard2") );

        return "sample/selectOneBoard2";
    }

    @RequestMapping(value = "/app/selectListBoard2.do", method = RequestMethod.GET)
    public ModelAndView selectListBoard2(Locale locale, Model model) throws Exception {
    	logger.info("Welcome selectListBoard2! the client locale is "+ locale.toString());
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("sample/selectListBoard2"); // 이동할 jsp 이름

    	List<Map> list = appTestService.selectListBoard2("AppTestService.selectListBoard2");
    	logger.info("selectListBoard2 result list >>>>>"+list.toString());
		view.addObject("list", list ); // jsp 로 넘길 데이터

		return view;
    }
    
	@RequestMapping(value="/app/openBoardListNone.do")
    public ModelAndView openBoardList(CommandMap commandMap, Locale locale) throws Exception{
    	logger.info("Welcome selectOneBoard2! the client locale is "+ locale.toString());
    	
    	ModelAndView mv = new ModelAndView("/sample/pagingNone/boardList");
    	
    	List<Map> list = appTestService.openBoardList("AppTestService.selectBoardList", commandMap.getMap());
    	mv.addObject("list", list);
    	
    	return mv;
    }


}
