package myspring.common.mybatis.support;

import myspring.common.exception.BusinessException;
import myspring.common.exception.ExceptionLoggingRequired;

public class MaxHandleRowException extends BusinessException implements ExceptionLoggingRequired {
    private static final long serialVersionUID = 1L;

    public MaxHandleRowException() {
    }

    public MaxHandleRowException(String msg) {
        super(msg);
    }

    public MaxHandleRowException(Throwable cause) {
        super(cause);
    }

    public MaxHandleRowException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
