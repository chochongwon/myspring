package myspring.app.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import myspring.app.service.AppTestService;
import myspring.sample.common.CommandMap;

@Controller
public class AppTestController {
    Logger log = Logger.getLogger(this.getClass());
    
    // @Resource 은 필요한 빈(bean)을 수동으로 등록하라는 것이다.
    @Resource(name="appTestService")
    private AppTestService appTestService;


	@RequestMapping(value="/app/openBoardListNone.do")
    public ModelAndView openBoardList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/sample/pagingNone/boardList");
    	
    	List<Map> list = appTestService.select("AppTestService.selectBoardList", commandMap.getMap());
    	mv.addObject("list", list);
    	
    	return mv;
    }
}
