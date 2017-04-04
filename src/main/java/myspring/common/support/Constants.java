package myspring.common.support;

/**
 * 상수 선언 전용 Class.
 * JDK5의 static import 를 사용하자.
 * Constant Interface Pattern 이 열라 Antipattern이라고 하니 이번에는 static import를 써보자.
 * 뭐, 거기서 거기겠지만...
 *
 * @author 댐뽀리
 */
public final class Constants {
    public final static String MS949 = "MS949";
    public final static String EUC_KR = "euc-kr";
    public final static String UTF8 = "UTF8";
    
    public final static int TYPE_OBJECT = 0;
    public final static int TYPE_STRING = 1;
    
    /**
     * datset 조회시 rowtype setting 용 컬럼명
     */
    public final static String ROW_TYPE_COLUMN = "_ROWTYPE_";

    /**
     * dataset 조회시 checkbox setting 용 컬럼명
     */
    public final static String CHECKBOX_COLUMN = "_CHECK_";

    /**
     * dataset 조회시 가상 데이터 여부 check 용 컬럼명
     */
    public final static String VIRTUAL_COULMN = "_VIRTUAL_";

    /**
     * dataset 조회시 개인정보 보호 관련 flag 용 컬럼명  
     */
    public final static String DECRYPT_COULMN = "_DECRYPT_";    
    
    /**
     * Paging Grid 조회시 total count 컬럼명
     */
    public final static String TOTAL_COLUMN = "TOTAL_CNT";
    /**
     * Paging Grid 조회시 current Page No 컬럼명
     */
    public final static String PAGE_NO_COLUMN = "PAGE_NO_";

    /**
     * Combo dataset 의 code column
     */
    public final static String COMBO_CODE_COLUMN = "CODE";

    /**
     * Combo dataset 의 data column
     */
    public final static String COMBO_DATA_COLUMN = "NAME";

    /**
     * 나모 관리번호
     */
    public final static String NAMO_MGT_NO = "NAMO_MGT_NO";

    /**
     * 첨부파일 관리번호
     */
    public final static String FILE_MGT_NO = "FILE_MGT_NO";

    /**
     * 숫자 0
     */
    public final static int ZERO = 0;

    /**
     * Default output Dataset Name
     */
    public final static String FIRST_OUT_DS_NM = "_FIRST_OUT_DS_NM_";

    /**
     * Default input Dataset Name
     */
    public final static String FIRST_IN_DS_NM = "_FIRST_IN_DS_NM_";
    
    public final static String PLATFORM_PAGE_URL = "_PLATFORM_PAGE_URL_";
    
    public final static String PLATFORM_MENU_ID = "_PLATFORM_MENU_ID_";
    
    public final static String REXPERT_FILE_ID = "_REXPERT_FILE_ID_";

    public final static String ROWTYPE_INSERT = "I";
    public final static String ROWTYPE_UPDATE = "U";
    public final static String ROWTYPE_DELETE = "D";
    public final static String ROWTYPE_NORMAL = "N";

    /**
     * Paging Grid Request Parameter Name for current page number
     */
    public final static String VAR_CURR_PAGE_ = "_var_currPage_";
    /**
     * Paging Grid Request Parameter Name for next page number
     */
    public final static String VAR_NEXT_PAGE_ = "_var_nextPage_";
    /**
     * Paging Grid Request Parameter Name for rownum per one page
     */
    public final static String VAR_ROW_PER_PAGE_ = "_var_rowPerPage_";
    /**
     * Romantic Paging Grid Request Parameter Name
     */
    public final static String VAR_CURR_KEY_ = "currRomanticKey";
    public final static String VAR_NEXT_KEY_ = "nextRomanticKey";
    public final static String VAR_PAGE_INDEX_SCALE = "_var_pageIndexSacle_";

    public final static String JSESSIONID = "JSESSIONID";
    /**
     * 사용자 세션 정보 attribute key
     */
    public final static String USER_SESSION_OBJECT = "_USER_SESSION_OBJECT_";
    
    /** INPT_PRSN column alias */
    public final static String INPT_PRSN = "INPT_PRSN";
    
    /** UPDT_PRSN column alias */
    public final static String UPDT_PRSN = "UPDT_PRSN";

    /** MiPlatform Transaction Callback Error Code key */
    public final static String ACTION_SUBMIT_ERROR_CODE = "errorCode";
    /** MiPlatform Transaction Callback Error Message key */
    public final static String ACTION_SUBMIT_ERROR_MESSAGE = "errorMsg";
    /** MiPlatform Transaction Callback Debug Message key */
    public final static String ACTION_SUBMIT_DEBUG_MESSAGE = "debugMsg";
    
    /** 서버 네임. 로컬 개발 서버 */
    public final static String LOCALHOST = "localhost";
    /** 서버 네임. 개발 서버 */
    public final static String APPDEV = "dev";
    /** 서버 네임. 운영 서버 APP1*/
    public final static String APPREAL1 = "app1";
    /** 서버 네임. 운영 서버 APP2*/
    public final static String APPREAL2 = "app2";
    /** 서버 네임. 개발 서버 */
    public final static String BATCHDEV1 = "batchdev";
    /** 서버 네임. 개발 서버 */
    public final static String BATCHDEV2 = "batchdev2";
    /** 서버 네임. 운영 서버 */
    public final static String BATCHREAL1 = "batch1";
    /** 서버 네임. 운영 서버 */
    public final static String BATCHREAL2 = "batch2";
    
    /** System Error Message 시스템에 문제가 발생하였습니다. 잠시 후 다시 사용해 주십시오.*/
    public final static String MSG_E_COM_0001 = "시스템 장애가 발생하였습니다. 전산팀에 문의해 주십시오.";
    /** System Error Message 중복된 자료를 입력하셨습니다. */
    public final static String MSG_E_COM_0002 = "중복된 자료를 입력하셨습니다.";
    /** System Error Message 입력한 값이 너무 큽니다. 다시 입력해 주십시오. */
    public final static String MSG_E_COM_0003 = "입력한 값이 너무 큽니다. 다시 입력해 주십시오.";
    /** System Error Message 등록된 자료가 없습니다.*/
    public final static String MSG_E_COM_0004 = "등록된 자료가 없습니다.";
    /** System Error Message 연결된 다른 자료가 존재하여 삭제할 수 없습니다.*/
    public final static String MSG_E_COM_0005 = "연결된 다른 자료가 존재하여 삭제할 수 없습니다.";
    /** System Error Message 조회 범위가 매우 넓습니다. 검색조건을 확인해 주십시오.*/
    public final static String MSG_E_COM_0006 = "조회 범위가 매우 넓습니다. 검색조건을 확인해 주십시오.";
    
    public final static String SERVICE_EXTENSION_DO = "do";
    
    public final static String SERVICE_EXTENSION_DOX = "dox";
    
    /**
     *  조회 max 제한
     */
    public static int MAX_DATASET_HANDLE_ROW = 20000;
}
