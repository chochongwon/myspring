package myspring.sample.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import myspring.sample.common.CommandMap;
import myspring.sample.service.SampleService;

// @Controller은 Controller 객체임을 선언한다.
@Controller
public class SampleController {
    Logger log = Logger.getLogger(this.getClass());
    
    // @Resource 은 필요한 빈(bean)을 수동으로 등록하라는 것이다.
    @Resource(name="sampleService")
    private SampleService sampleService;

    /*
     * TEST URL : http://localhost:8080/myspring/sample/testMapArgumentResolver.do?aaa=temp
     */
    @RequestMapping(value="/sample/testMapArgumentResolver.do")
    public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setView(new RedirectView("index?method=index"));
        mv.addObject("parameter","test");
        
        if(commandMap.isEmpty() == false){
            Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
            Entry<String,Object> entry = null;
            while(iterator.hasNext()){
                entry = iterator.next();
                log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
            }
        }
        return mv;
    }

    
    // @RequestMapping은 요청URL을 의미한다.
	@RequestMapping(value="/sample/openBoardListNone.do")
    public ModelAndView openBoardList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/sample/pagingNone/boardList");
    	
    	List<Map<String,Object>> list = sampleService.selectBoardList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	return mv;
    }
	
	@RequestMapping(value="/sample/openBoardList.do")
	public ModelAndView openBoardListPagingAjax(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("/sample/boardList");
	     
	    return mv;
	}
	
	/*
	 * 아래는 JSON 형식의 데이터가 올바른지 여부를 검사하고 포맷팅도 해주는 사이트다.
	 * http://jsonlint.com/ 
	 */
	@RequestMapping(value="/sample/selectBoardListPagingAjax.do")
	public ModelAndView selectBoardListPagingAjax(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("jsonView");
	     
	    List<Map<String,Object>> list = sampleService.selectBoardListPagingAjax(commandMap.getMap());
	    mv.addObject("list", list);
	    if(list.size() > 0){
	        mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
	    }
	    else{
	        mv.addObject("TOTAL", 0);
	    }
	     
	    return mv;
	}
	
	@RequestMapping(value="/sample/openBoardListPagingEgov.do")
    public ModelAndView openBoardListPagingEgov(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/sample/pagingEgov/boardList");
    	
    	Map<String,Object> resultMap = sampleService.selectBoardListPagingEgov(commandMap.getMap());
    	
    	// jsp에서 <ui:pagination paginationInfo="${paginationInfo}" 부분에서 쓰임
        mv.addObject("paginationInfo", (PaginationInfo)resultMap.get("paginationInfo"));
        mv.addObject("list", resultMap.get("result"));
    	
    	return mv;
    }
	
	@RequestMapping(value="/sample/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/boardWrite");
		
		return mv;
	}
	
	@Transactional
    @RequestMapping(value="/sample/insertBoard.do")
    public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		
		int result = sampleService.insertBoard(commandMap.getMap(), request);
    	log.info("result="+result);
		
        return mv;
    }

	@Transactional
    @RequestMapping(value="/sample/openBoardDetail.do")
    public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardDetail");

        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
        mv.addObject("map", map.get("map"));
        mv.addObject("list", map.get("list"));
         
        return mv;
    }
	
	@RequestMapping(value="/sample/openBoardUpdate.do")
	public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("/sample/boardUpdate");
	     
	    Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
	     
	    return mv;
	}

	@Transactional
	@RequestMapping(value="/sample/updateBoard.do")
	public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
	    ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
	     
	    int result = sampleService.updateBoard(commandMap.getMap(), request);
	    log.info("result="+result);
	    
	    mv.addObject("IDX", commandMap.get("IDX"));
	    return mv;
	}

	@Transactional
	@RequestMapping(value="/sample/deleteGbBoard.do")
	public ModelAndView deleteGbBoard(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
	     
	    int result = sampleService.deleteGbBoard(commandMap.getMap());
	    log.info("result="+result);

	    mv.addObject("IDX", commandMap.get("IDX"));
	    return mv;
	}
	
	@Transactional
	@RequestMapping(value="/sample/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
	     
	    int result = sampleService.deleteBoard(commandMap.getMap());
	    log.info("result="+result);

	    mv.addObject("IDX", commandMap.get("IDX"));
	    return mv;
	}
}