package myspring.common.enums;

public enum LoginValidation {
    VALIDATE_OK(0, ""),
    ERR_NO_USER_INFO(1, "E_COM_0013"),
    ERR_PASSWORD_NOT_EXIST(2, "E_COM_0016"),
    ERR_PASSWORD_MISMATCH(3, "E_COM_0014"),
    ERR_PWD_CHANGE_EXPIRED(4, "E_COM_0015"),
    // ERR_RETIRED_USER(5, "E_COM_0017"),
    ERR_PASSWORD_GRADE(6, "E_COM_0033"),
    ERR_PWD_INPUT_LIMITED(7, "E_COM_0034");
    
    public int code;
    public String message;
    
    LoginValidation(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
