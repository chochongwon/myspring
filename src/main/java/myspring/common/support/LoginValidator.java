package myspring.common.support;

import javax.servlet.http.HttpServletRequest;

import myspring.common.domain.User;
import myspring.common.enums.LoginValidation;

public interface LoginValidator {
    public LoginValidation validate(HttpServletRequest req, User user);
}
