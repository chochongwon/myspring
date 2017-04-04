package myspring.common.support;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import myspring.common.domain.User;
import myspring.common.enums.LoginValidation;

public class LoginValidatorSupport implements InitializingBean, LoginValidator {
    protected final Log logger = LogFactory.getLog(getClass());
    
    private List<LoginValidator> validatorList;

	@Override
	public LoginValidation validate(HttpServletRequest req, User user) {
        for(LoginValidator validator : validatorList) {
            LoginValidation result = validator.validate(req, user);
            if(logger.isDebugEnabled()) {
                logger.debug("login validation result : " + result.code+"," + result.message);
            }
            if(result != LoginValidation.VALIDATE_OK) {
                return result;
            }
        }
        return LoginValidation.VALIDATE_OK;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
        validatorList = new ArrayList<LoginValidator>();
        
        //존재하지 않는 사용자ID
        validatorList.add(new LoginValidator() {
            public LoginValidation validate(HttpServletRequest req, User user) {
                return (user == null) ? LoginValidation.ERR_NO_USER_INFO : LoginValidation.VALIDATE_OK;
            }
        });
                
        //패스워드 등록 필요
        validatorList.add(new LoginValidator() {
            public LoginValidation validate(HttpServletRequest req, User user) {
                if(user.getPassword() == null) {
                    return LoginValidation.ERR_PASSWORD_NOT_EXIST;
                }
                return LoginValidation.VALIDATE_OK;
            }
        });
        
        //패스워드 불일치
        validatorList.add(new LoginValidator() {
            public LoginValidation validate(HttpServletRequest req, User user) {
                if(!StringUtils.equals(user.getMatchPwd(), user.getPassword())) {
                    return LoginValidation.ERR_PASSWORD_MISMATCH;
                }
                return LoginValidation.VALIDATE_OK;
            }
        });
        
        // 패스워드 변경기간 만료
        validatorList.add(new LoginValidator() {
            public LoginValidation validate(HttpServletRequest req, User user) {
                return (user.isPasswordExpired()) ? LoginValidation.ERR_PWD_CHANGE_EXPIRED : LoginValidation.VALIDATE_OK;
            }
        });
        
        // 패스워드 조합검증
        validatorList.add(new LoginValidator() {
            public LoginValidation validate(HttpServletRequest req, User user) {
                if(!StringUtils.equals(user.getGradePwd(), "1")) {
                    return LoginValidation.ERR_PASSWORD_GRADE;
                }
                return LoginValidation.VALIDATE_OK;
            }
        });
	}

}
