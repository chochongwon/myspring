package myspring.sample.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import myspring.sample.common.CommandMap;
import myspring.sample.service.CommonService;
 
@Controller
public class CommonController {
    Logger log = Logger.getLogger(this.getClass());
     
    @Resource(name="commonService")
    private CommonService commonService;
    
    /*
     * 첨부파일을 다운로드할 수 있도록 response에 전달한다.
     * 아직은 예외처리나 보안이 적용되어 있지 않다.
     */
    @RequestMapping(value="/common/downloadFile.do")
    public void downloadFile(CommandMap commandMap, HttpServletResponse response) throws Exception{
    	// 첨부파일의 정보를 읽어 온다.
        Map<String,Object> map = commonService.selectFileInfo(commandMap.getMap());
        String storedFileName = (String)map.get("STORED_FILE_NAME");
        String originalFileName = (String)map.get("ORIGINAL_FILE_NAME");
         
        // 파일저장위치에서 해당 첨부파일을 읽어서 bytep[] 형태로 변환한다.
        byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\CNProjectHome\\eclipse\\eclipse-jee-neon-2\\workspace\\myspring\\src\\main\\webapp\\WEB-INF\\file_uploads\\"+storedFileName));
         
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        // attachment 는 첨부파일을 의미한다. 파일을 서버로 전송할 때는 multipart-form/data 로 한다. 
        // 주의할 점은 띄어쓰기에 주의하고 마지막에 \를 빼먹지 말자.
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
         
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
