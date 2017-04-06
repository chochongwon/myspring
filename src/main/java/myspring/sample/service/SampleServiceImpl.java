package myspring.sample.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import myspring.sample.dao.mapper.SampleDAO;
import myspring.sample.util.FileUtils;

// @Service 은 Service객체임을 선언한다.
@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	Logger log = Logger.getLogger(this.getClass());
	
	// @Resource 은 필요한 빈(bean)을 수동으로 등록하라는 것이다.
	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO;
	
    @Resource(name="fileUtils")
    private FileUtils fileUtils;
    
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	}

	@Override
	public List<Map<String, Object>> selectBoardListPagingAjax(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardListPagingAjax(map);
	}
	
	@Override
	public Map<String, Object> selectBoardListPagingEgov(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardListPagingEgov(map);
	}
	
	@Override
	public int insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		int result = sampleDAO.insertBoard(map);	
		
		
	    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
	    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
	    MultipartFile multipartFile = null;
	    while(iterator.hasNext()){
	        multipartFile = multipartHttpServletRequest.getFile(iterator.next());
	        if(multipartFile.isEmpty() == false){
	            log.debug("------------- file start -------------");
	            log.debug("name : "+multipartFile.getName());
	            log.debug("filename : "+multipartFile.getOriginalFilename());
	            log.debug("size : "+multipartFile.getSize());
	            log.debug("-------------- file end --------------\n");
	        }
	    }
	    	
		
        List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
        for(int i=0, size=list.size(); i<size; i++){
            sampleDAO.insertFile(list.get(i));
        }
        
	    return result;
	}

	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		log.info("map="+map);		
		int result = sampleDAO.updateHitCnt(map);
		log.info("result="+result);	
		
	    Map<String, Object> resultMap = new HashMap<String,Object>();
	    Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
	    resultMap.put("map", tempMap);
	     
	    List<Map<String,Object>> list = sampleDAO.selectFileList(map);
	    resultMap.put("list", list);

	    return resultMap;
	}
	
	@Override
	public int updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception{
	    int result = sampleDAO.updateBoard(map);
	    
	    sampleDAO.deleteFileList(map); // 해당 게시글에 해당하는 첨부파일을 전부 삭제처리(DEL_GB = 'Y')를 하는 역할을 한다.
	    List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request); // request에 담겨있는 파일 정보를 list로 변환한다.
	    
	    // 파일을 하나씩 insert & update 한다. 
	    // list 에서 IS_NEW값을 이용해 신규저장여부를 판단한다.
	    Map<String,Object> tempMap = null;
	    for(int i=0, size=list.size(); i<size; i++){
	        tempMap = list.get(i);
	        if(tempMap.get("IS_NEW").equals("Y")){
	            sampleDAO.insertFile(tempMap);
	        }
	        else{
	            sampleDAO.updateFile(tempMap);
	        }
	    }
	    
	    return result;
	}

	@Override
	public int deleteGbBoard(Map<String, Object> map) throws Exception {
		return sampleDAO.deleteGbBoard(map);
	}
	
	@Override
	public int deleteBoard(Map<String, Object> map) throws Exception {
		return sampleDAO.deleteBoard(map);
	}

}
