package myspring.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import myspring.common.config.SystemConfiguration;
import myspring.common.dao.SqlSessionDAO;
import myspring.common.util.AssertUtil;

/**
 * 모든 Service 클래스의 부모 클래스.<br/>
 * 시스템의 모든 Service 클래스는 AbstractService 로부터 상속받도록 선언한다.<br/>
 * 
 * @author 
 */
public class AbstractService implements InitializingBean {
    /**
     * logger instanct - 하위 서비스에서 선언하지 않도록 상위에 선언함.
     */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    @Qualifier("sqlSessionDAO")
    private SqlSessionDAO sqlSessionDAO;
    
    @Autowired
    private SystemConfiguration systemConfig;
    
	@Override
	public void afterPropertiesSet() throws Exception {
        AssertUtil.notNull(sqlSessionDAO, "sqlSessionDAO must not null. use spring DI plz...");
        AssertUtil.notNull(systemConfig, "SystemConfig must not null. use spring DI plz...");
	}

    /**
     * Mapper statement 실행을 위한 범용 DAO 객체인 SqlSessionDAO 의 refernce를 리턴한다.
     * @return SqlMapClientDAO 객체
     */
    public SqlSessionDAO getSqlSessionDAO() {
        return sqlSessionDAO;
    }
    
    /**
     * 시스템 환경설정 Configuration Property를 열람하기 위한 SystemConfiguration 의 reference를 리턴한다.
     * @return SystemConfiguration 객체
     */
    public SystemConfiguration getSystemConfig() {
        return systemConfig;
    }
    
}
