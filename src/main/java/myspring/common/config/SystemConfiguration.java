package myspring.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import static myspring.common.support.Constants.LOCALHOST;

/**
 * 환경설정 Properties 파일에 설정된 내용을 getConfig 메소드를 통해 제공한다.
 * <p>
 * Properties 파일 : config/properties/app.properties
 * 
 * 
 * @author 
 */
public class SystemConfiguration implements InitializingBean {
    private static final String VALUE = "VALUE";
    private static final String KEY = "KEY";
    protected final Log logger = LogFactory.getLog(getClass());
    
    private Properties properties;
    
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getConfig(String key) {
        return properties.getProperty(key); 
    }

    @Override
    public String toString() {
        return (properties != null) ? properties.toString() : super.toString();
    }

	@Override
	public void afterPropertiesSet() throws Exception {
        String webappRoot = StringUtils.replace(System.getProperty("webapp.root"), "\\", "/");
        String userDir = StringUtils.replace(System.getProperty("user.dir"), "\\", "/");
        String javaHome = StringUtils.replace(System.getProperty("java.home"), "\\", "/");
        
        properties.put("system.webapp.root", webappRoot != null ? webappRoot : "");
        properties.put("system.user.dir", userDir != null ? userDir : "");
        properties.put("system.java.home", javaHome != null ? javaHome : "");
        
        if(LOCALHOST.equals(properties.getProperty("cnapp.server.id"))) {
            String homeDrive = null;
            if(StringUtils.isNotEmpty(webappRoot)) {
                homeDrive = webappRoot.substring(0, 2);
            } else if(StringUtils.isNotEmpty(userDir)) {
                homeDrive = userDir.substring(0, 2);
            } else if(StringUtils.isNotEmpty(javaHome)) {
                homeDrive = javaHome.substring(0, 2);
            }
            /* /CNProjectHome/ -> c:/CNProjectHome/ */
            if(homeDrive != null) {
                for(Entry e: properties.entrySet()) {
                    if(StringUtils.startsWith(String.valueOf(e.getValue()), "/CNProjectHome/")) {
                        properties.put(e.getKey(), homeDrive + e.getValue());     
                    }
                }
            }
        }
            
        if(logger.isDebugEnabled()) {
            logger.debug("# SystemConfiguration configured properties info #");
            for(Entry e: properties.entrySet()) {
                logger.debug("# " + e.getKey() + " : " + e.getValue());
            }
        }
	}

}
