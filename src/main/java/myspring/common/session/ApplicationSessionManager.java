package myspring.common.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import myspring.common.domain.User;

public class ApplicationSessionManager {
    /**
     * Commons Logger for this Class
     */
    private final static Log logger = LogFactory.getLog(ApplicationSessionManager.class);
    
    private Map<String, User> loginUserSessionMap = new HashMap<String, User>();
    
    public void addLoginUserSession(User user) {
        if(logger.isDebugEnabled()) {
            logger.debug("add user session : " + user);
        }
        loginUserSessionMap.put(user.getSessionId(), user);
    }
    
    public void removeLoginUserSession(User user) {
        loginUserSessionMap.remove(user.getSessionId());
        
        if(logger.isDebugEnabled()) {
            logger.debug("remove user session : " + user);
        }
    }
    
    public User getLiveUserSession(String sessionId) {
        return loginUserSessionMap.get(sessionId);
    }

}
