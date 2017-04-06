package myspring.sample.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

// @Component 은 이 객체의 관리를 스프링이 담당하도록 한다.
@Component("fileUtils")
public class FileUtils {
	private static final String filePath = "C:\\CNProjectHome\\eclipse\\eclipse-jee-neon-2\\workspace\\myspring\\src\\main\\webapp\\WEB-INF\\file_uploads\\";
	
	public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
        String boardIdx = (String)map.get("IDX");
        
        File file = new File(filePath);
        if(file.exists() == false){
        	file.mkdirs();
        }
        
        while(iterator.hasNext()){
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	if(multipartFile.isEmpty() == false){
        		originalFileName = multipartFile.getOriginalFilename();
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		
        		file = new File(filePath + storedFileName);
        		multipartFile.transferTo(file); // 지정된 경로에 파일을 생성한다.
        		
        		listMap = new HashMap<String,Object>();
        		listMap.put("BOARD_IDX", boardIdx);
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("STORED_FILE_NAME", storedFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        	}
        }
		return list;
	}
	
	/*
	 * parseInsertFileInfo 를 기반으로 수정을 위해 약간 변경됨
	 * 
	 */
	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
	    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
	    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
	     
	    MultipartFile multipartFile = null;
	    String originalFileName = null;
	    String originalFileExtension = null;
	    String storedFileName = null;
	     
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    Map<String, Object> listMap = null;
	     
	    String boardIdx = (String)map.get("IDX");
	    String requestName = null;
	    String idx = null;
	     
	     
	    while(iterator.hasNext()){
	        multipartFile = multipartHttpServletRequest.getFile(iterator.next());        
	        if(multipartFile.isEmpty() == false){
		        /*
		         *  첨부파일이 있는 경우는 기존(parseInsertFileInfo 메서드)과 동일한 방식으로 처리하고
		         *  추가로 SampleServiceImpl 에서 사용할 "IS_NEW"라는 키로 "Y"라는 값을 저장하였다. 
		         */	
	            originalFileName = multipartFile.getOriginalFilename();
	            originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	            storedFileName = CommonUtils.getRandomString() + originalFileExtension;
	             
	            multipartFile.transferTo(new File(filePath + storedFileName));
	             
	            listMap = new HashMap<String,Object>();
	            listMap.put("IS_NEW", "Y");
	            listMap.put("BOARD_IDX", boardIdx);
	            listMap.put("ORIGINAL_FILE_NAME", originalFileName);
	            listMap.put("STORED_FILE_NAME", storedFileName);
	            listMap.put("FILE_SIZE", multipartFile.getSize());
	            list.add(listMap);
	        }
	        else{
	        	/*
		         * 수정작업에서는 첨부파일이 없더라도 해당 파일은 저장이 될 수도 있고 아닐수도 있다는 것을 무시하면 안된다.
		         * 게시글에서 파일을 수정하지 않을 경우, 해당 multipartFile은 비어있다. 
		         * 그렇지만 그대로 놔두면 이미 파일은 지워져있기 때문에, 최종적으로는 파일이 없다고 나오게 된다. 
		         * 따라서, 파일정보가 없는 경우에는 해당 파일정보가 기존에 저장이 되어있던 내용인지 아니면 단순히 빈 파일인지 
		         * 구분해야한다. 
		         */
	            requestName = multipartFile.getName(); // html 태그에서 file 태그의 name 값을 가져온다.	            
	            /*
	             *  jsp에서 <input type="file" id="file_숫자" name="file_숫자"> 형식으로 선언했었다.
	             *  기존에 저장이 되어있던 파일의 경우, 
	             *  <input type="hidden" id="IDX" name="IDX_숫자" value="파일번호"> 태그를 생성했던것을 기억해보자.
	             *  그리고 신규로 생성이 된 파일의 경우, 위의 태그를 만들어주지 않았었다. 
	             *  따라서, 위 태그의 값이 있을 경우가 기존에 저장된 파일임을 알 수 있다.
	             *  "IDX_" 라는 키 값에 해당 태그의 네임에서 숫자를 가져와서 합쳐준다. 
	             *  그렇게 되면 IDX_1, IDX_2 등의 값을 가지게 되는 것이다.
	             */
	            idx = "IDX_"+requestName.substring(requestName.indexOf("_")+1); 
	            // IDX_숫자 키가 있다면 "IS_NEW"키값을 "N" 으로 기존에 저장이 되어있던 파일 정보임을 설정한다.
	            if(map.containsKey(idx) == true && map.get(idx) != null){
	                listMap = new HashMap<String,Object>();
	                listMap.put("IS_NEW", "N");
	                listMap.put("FILE_IDX", map.get(idx));
	                list.add(listMap);
	            }
	        }
	    }
	    return list;
	}

	public static void writer(String fileName, byte[] bytes, boolean flush) throws IOException {
      Writer out = new FileWriter(fileName, true);
      long a = System.currentTimeMillis();
      for (byte j : bytes) {
        out.write(j);
        if (flush) out.flush();
      }
      out.close();
      System.out.println("FileWriter with" + (flush? "":"out") + " flushing: " +
          (System.currentTimeMillis() - a));
    }

    public static void writer(String fileName, String text, boolean flush) throws IOException {
      Writer out = new FileWriter(fileName, true);
      //Writer out = new OutputStreamWriter(new FileOutputStream(fileName, true), "MS949");
      long a = System.currentTimeMillis();
      out.write(text);
      if (flush) out.flush();
      out.close();
      System.out.println("FileWriter with" + (flush? "":"out") + " flushing: " +
          (System.currentTimeMillis() - a));      
    }
    
    public static void stream(String fileName, byte[] bytes, boolean flush) throws IOException {
      OutputStream out = new FileOutputStream(fileName, true);
      long a = System.currentTimeMillis();
      for (byte j : bytes) {
        out.write(j);
        if (flush) out.flush();
      }
      out.close();
      System.out.println("FileOutputStream with" + (flush? "":"out") + " flushing: " +
          (System.currentTimeMillis() - a));
    }
    
}
