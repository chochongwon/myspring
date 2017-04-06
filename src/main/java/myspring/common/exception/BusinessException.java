package myspring.common.exception;

/**
 * Business Logic 예외처리 Exception 최상위 클래스.
 * 업무 로직 수행 중 발생하는 오류에 대한 처리는 Business Exception을 발생하여 처리한다.
 * 오류내용에 대한 메시지가 지정되어야 하며, 이 메시지는 사용자에게 전달된다.
 * 
 * 또한, 서비스 메소드에서 BusinessException 이 throw 될 때, 관련 Transaction 은 rollback 처리 된다.
 * 
 * @author ddam40
 */
public class BusinessException extends RuntimeException {

    /**
     * default serial version id 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Exception Error Code
     */
    private int errorCode = -1;
    
    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * getter of errorCode
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }
    
    /**
     * default constructor
     */
    public BusinessException() {
    }

    /**
     * 메시지를 포함한 BusinessException 객체를 생성합니다. 
     * @param msg 메시지
     */
    public BusinessException(String msg) {
        super(msg);
    }

    /**
     * 객체 생성시 원인이 되는 Throwable 객체를 지정합니다. 
     * Throwable 객체를 wrapping 하여 BusinessException 객체를 생성하고자 할때 사용합니다.
     * 
     * @param cause BusinessException 생성 원인이 되는 Throwable 객체.
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * 메시지와 원인이 되는 Throwable 객체를 지정하여 BusinessException 객체를 생성합니다.
     * 
     * @param msg 메시지
     * @param cause BusinessException 생성 원인이 되는 Throwable 객체.
     */
    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 에러 코드와 메시지를 지정하여 BusinessException 객체를 생성합니다.
     * 
     * @param errorCode 에러 코드
     * @param msg 메시지
     */
    public BusinessException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
    
    /**
     * 에러 코드와 메시지, 원인이 되는 Throwable 객체를 지정하여 BusinessException 객체를 생성합니다.
     * 
     * @param errorCode 에러코드
     * @param msg 메시지
     * @param cause BusinessException 생성 원인이 되는 Throwable 객체.
     */
    public BusinessException(int errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }
}
