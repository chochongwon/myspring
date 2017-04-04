package myspring.common.domain;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;

import myspring.common.session.ApplicationSessionManager;

/**
 * 로그인 사용자 정보 Domain Object
 */
public class User implements Serializable, HttpSessionBindingListener {

    private transient static final String APPLICATION_SESSION_MANAGER_BEAN_NAME = "applicationSessionManager";

    private final static long serialVersionUID = 1L;
    
    private transient ApplicationContext applicationContext;
    
    /**
     * controller-servlet.xml 에 등록된 handlerMapping 접근을 위한 applicationContext id
     */
    private final static String CONTROLLER_SERVLET_CONTEXT_NAME = FrameworkServlet.SERVLET_CONTEXT_PREFIX + "controller";    

    /** Session ID */
    private String sessionId;
    
    /** 회사 코드 */
    private String companyCode;
    
    /** 회사명 */
    private String companyName;
    
    /** 사용자 ID */
    private String userId;
    
    /** 사용자명 */
    private String userNm;

    /** 사원코드 */
    private String staffCode;
    
    /** 암호화된 사원코드 */
    private String encryptStaffCode;
    
    /** 사원명 */
    private String staffName;
    
    /** 패스워드 */
    private String password;
    
    /** 로그인시 매칭할 패스워드 */
    private String matchPwd;
    
    /** 패스워드 변경일시 */
    private String passwordModifiedDate;
    
    /** 패스워드 expired flag */
    private boolean passwordExpired;
    
    /** 패스워드 보안등급 */
    private String gradePwd;
    
    /** 로그인 시각 */
    private String loginTime;
    
    /** 로그인 IP Address */
    private String ipAddress;
    
    /** 사용자 컴퓨터 이름 */
    private String computerName;
    
    /** 세션무효화 여부 */
    private String sessionInvalidate;
       

	/**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 사용자 ID getter method.
     * @return 사용자 ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 사용자 ID setter method.
     * 
     * @param userId 사용자 ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * 사용자명 getter method.
     * @return 사용자명
     */
    public String getUserNm() {
        return userNm;
    }

    /**
     * 사용자명 setter method.
     * @param userNm 사용자명
     */
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    /**
     * 사원코드 getter method.
     * @return the staffCode
     */
    public String getStaffCode() {
        return staffCode;
    }

    /**
     * 사원코드 setter method.
     * @param staffCode the staffCode to set
     */
    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }
    
    /**
     * 암호화된 사원코드 getter method.
     * @return the encryptStaffCode
     */
    public String getEncryptStaffCode() {
        return encryptStaffCode;
    }

    /**
     * 암호화된 사원코드 setter method.
     * @param encryptStaffCode the encryptStaffCode to set
     */
    public void setEncryptStaffCode(String encryptStaffCode) {
        this.encryptStaffCode = encryptStaffCode;
    }
    
    /**
     * 사원명 getter method.
     * @return the staffName
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * 사원명 setter method.
     * @param staffName the staffName to set
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    /**
     * 패스워드 getter method.
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 패스워드 setter method.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 로그인 패스워드 getter method.
     * @return the loginPwd
     */
    public String getMatchPwd() {
        return matchPwd;
    }    
    
    /**
     * 패스워드 setter method.
     * @param matchPwd the matchPwd to set
     */
    public void setMatchPwd(String matchPwd) {
        this.matchPwd = matchPwd;
    }     
    
    
    public String getPasswordModifiedDate() {
        return passwordModifiedDate;
    }

    public void setPasswordModifiedDate(String passwordModifiedDate) {
        this.passwordModifiedDate = passwordModifiedDate;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(String passwordExpired) {
        if(StringUtils.equalsIgnoreCase(passwordExpired, "TRUE")) {
            this.passwordExpired = true;    
        } else {
            this.passwordExpired = false;
        }
    }

    /**
     * 로그인 패스워드 getter method.
     * @return the gradePwd
     */
    public String getGradePwd() {
        return gradePwd;
    }    
    
    /**
     * 패스워드 setter method.
     * @param gradePwd the gradePwd to set
     */
    public void setGradePwd(String gradePwd) {
        this.gradePwd = gradePwd;
    }   
    
    /**
     * 로그인 시각 getter method.
     * @return the loginTime
     */
    public String getLoginTime() {
        return loginTime;
    }

    /**
     * 로그인 시각 setter method.
     * @param loginTime the loginTime to set
     */
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 로그인 IP Address getter method.
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 로그인 IP Address setter method.
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 컴퓨터 이름 getter method.
     * @return the computerName
     */
    public String getComputerName() {
        return computerName;
    }

    /**
     * 컴퓨터 이름 setter method
     * @param computerName the computerName to set
     */
    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    /**
     * 세션무효화여부 getter method.
     * @return the sessionInvalidate
     */
    public String getSessionInvalidate() {
        return sessionInvalidate;
    }
    
    /**
     * 세션무효화여부 setter method
     * @param seSessionInvalidate the orhiOfficeCode to set
     */
    public void setSessionInvalidate(String sessionInvalidate) {
        this.sessionInvalidate = sessionInvalidate;
    }

    @Override
    public String toString() {
        try {
            return BeanUtils.describe(this).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.toString();
    }
    
    /**
     * Session Binding event callback
     */
    public void valueBound(HttpSessionBindingEvent event) {
        this.sessionId = event.getSession().getId();
        
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        //WebApplicationContext context = (WebApplicationContext) (event.getSession().getServletContext().getAttribute(CONTROLLER_SERVLET_CONTEXT_NAME));
        if(context != null &&  context.containsBean(APPLICATION_SESSION_MANAGER_BEAN_NAME)) {
            ApplicationSessionManager sessionManager = (ApplicationSessionManager) context.getBean(APPLICATION_SESSION_MANAGER_BEAN_NAME);
            sessionManager.addLoginUserSession(this);
        }
    }

    /**
     * Session unbinding event callback
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        if(context != null &&  context.containsBean(APPLICATION_SESSION_MANAGER_BEAN_NAME)) {
            ApplicationSessionManager sessionManager = (ApplicationSessionManager) context.getBean(APPLICATION_SESSION_MANAGER_BEAN_NAME);
            sessionManager.removeLoginUserSession(this);
        }
    }
}
