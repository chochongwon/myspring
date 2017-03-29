package myspring.app.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import myspring.app.service.Board1Service;
import myspring.app.vo.Board1TransferVO;
import myspring.app.vo.Board1VO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class Board1Controller {
	private static final Logger logger = LoggerFactory.getLogger(Board1Controller.class);
	
	@Resource(name = "board1Service")
	private Board1Service board1Service;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/app/selectListBoard1.do", method = RequestMethod.GET)
	@Transactional
	public ModelAndView selectListBoard1(Locale locale, Model model) throws Exception {
		logger.info("Welcome selectListBoard1! The client locale is {}.", locale);
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("app/selectListBoard1"); // 이동할 jsp 이름

		List<Board1VO> list = board1Service.selectListBoard1();
		logger.info(list.toString());		
		
		view.addObject("list", list ); // jsp 로 넘길 데이터
		
		return view;
	}	
	
	@RequestMapping(value = "/app/mapProcedureListBoard1.do")
	public String mapProcedureListBoard1(Locale locale, Model model) throws Exception {
		logger.info("Welcome mapProcedureListBoard1! The client locale is {}.", locale);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idx", new Integer(1)); 
		map.put("crea_id", "Admin");
		
		Map<String, Object> resultMap = board1Service.mapProcedureListBoard1(map);
		logger.info(resultMap.toString());	
		
	    model.addAttribute("list", map.get("resultList"));
	    model.addAttribute("crea_id", map.get("crea_id"));
	    return "app/mapProcedureListBoard1";
	}

	@RequestMapping(value = "/app/procedureListBoard1.do")
	public String procedureListBoard1(Locale locale, Model model) throws Exception {
		logger.info("Welcome procedureListBoard1! The client locale is {}.", locale);

		Board1TransferVO container = new Board1TransferVO();
		container.setIdx(1);
		container.setCrea_id("Admin");
		
		Board1TransferVO resultContainer = board1Service.procedureListBoard1(container);
		logger.info(resultContainer.toString());	
		
		model.addAttribute("list", resultContainer.getResultList());
		model.addAttribute("crea_id", resultContainer.getCrea_id());
		return "app/procedureListBoard1";
	}
	
	@RequestMapping(value = "/app/anonymousBlockListBoard1.do")
	public String anonymousBlockListBoard1(Locale locale, Model model) throws Exception {
		logger.info("Welcome anonymousBlockListBoard1! The client locale is {}.", locale);

		Board1TransferVO container = new Board1TransferVO();
		container.setIdx(1);
		container.setCrea_id("Admin");
		
		Board1TransferVO resultContainer = board1Service.anonymousBlockListBoard1(container);
		logger.info(resultContainer.toString());	
		
		model.addAttribute("list", resultContainer.getResultList());
		model.addAttribute("crea_id", resultContainer.getCrea_id());
		return "app/procedureListBoard1";
	}
	
	@RequestMapping(value = "/app/tiles/selectListBoard1Tiles.tiles", method = RequestMethod.GET)
	@Transactional
	public ModelAndView selectListBoard1Tiles(Locale locale, Model model) throws Exception {
		logger.info("Welcome selectListBoard1Tiles! The client locale is {}.", locale);
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("app/tiles/selectListBoard1Tiles"); // 이동할 jsp 이름

		List<Board1VO> list = board1Service.selectListBoard1();
		logger.info(list.toString());		
		
		view.addObject("list", list ); // jsp 로 넘길 데이터

		return view;
	}	

	@RequestMapping(value = "/app/tiles/selectOneBoard1Tiles.tiles", method = RequestMethod.GET)
	@Transactional
	public ModelAndView selectOneBoard1Tiles(@RequestParam Integer idx, Model model) throws Exception {
		logger.info("Welcome selectOneBoard1Tiles! idx="+idx);
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("app/tiles/selectOneBoard1Tiles"); // 이동할 jsp 이름

		Board1VO vo = board1Service.selectOneBoard1(idx);
		logger.info(vo.toString());
		
		view.addObject("vo", vo ); // jsp 로 넘길 데이터

		return view;
	}	

	@RequestMapping(value = "/app/tiles/main.tiles", method = RequestMethod.GET)
	public ModelAndView main(Locale locale, Model model) throws Exception {
		logger.info("Welcome Main! The client locale is {}.", locale);
		
		ModelAndView view = new ModelAndView(); // view 객체
		view.setViewName("app/tiles/main"); // 이동할 jsp 이름
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
		String formattedDate = dateFormat.format(date);		
		view.addObject("serverTime", formattedDate ); // jsp 로 넘길 데이터
				
		return view;
	}	
	
	@RequestMapping(value = "/app/tiles/tilesNoMenu.tiles", method = RequestMethod.GET)
	public String noMenu(Model model) {
	   return "/nomenu/tilesNoMenu/This is NoMenu Title";
	}

	@RequestMapping(value = "/app/tiles/tilesDynamic.tiles", method = RequestMethod.GET)
	public String dynamic(Model model) {
	    return "/dynamic/tilesNoMenu/This is Dynamic Title";
	}

}
