package myspring.common.mybatis.support;

import static myspring.common.support.Constants.PAGE_NO_COLUMN;
import static myspring.common.support.Constants.TOTAL_COLUMN;
import static myspring.common.support.Constants.VAR_NEXT_PAGE_;
import static myspring.common.support.Constants.VAR_ROW_PER_PAGE_;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import myspring.common.mybatis.support.ColumnInjectionResultHandler;

public class PagingGridResultHandler extends ColumnInjectionResultHandler {
    /**
     * Commons Logging Instance.
     */
    protected final static Log logger = LogFactory.getLog(PagingGridResultHandler.class);

    /**
     * Page당 row수
     */
    private int rowPerPage;
    /**
     * 가져올 Page Number 1부터 시작한다.
     */
    private int pageNo;

    private final static String SQL_PREFIX_1 = " SELECT a.*, ";

    private final static String SQL_PREFIX_2 = " AS " + PAGE_NO_COLUMN;

    private final static String SQL_PREFIX_3 = " \nFROM ( SELECT a.*, COUNT(*) OVER() "+TOTAL_COLUMN+", ROWNUM AS rnum FROM ( \n";

    private final static String SQL_POSTFIX =  " \n) a ) a WHERE rnum BETWEEN ";

    /**
     * 주입을 위한 정보를 받아들인다.
     *
     * @param arguments - 주입할 column 정보 배열, key, value 페어로 죽죽...
     */
    public PagingGridResultHandler(Object ... arguments) {
        super(arguments);
    }
    
    public void setPagingInfo(int pageNo, int rowPerPage) {
        this.pageNo = (pageNo < 1) ? 1 : pageNo;
        this.rowPerPage = rowPerPage;
        if(logger.isDebugEnabled()) {
            logger.debug("setPagingInfo : " + this.pageNo + ", " + this.rowPerPage);
        }
    }

    public String convertPagingQuery(String sql) {
        int from = (pageNo - 1) * rowPerPage + 1;
        int to = pageNo * rowPerPage;

        StringBuffer sb = new StringBuffer();
        sb.append(SQL_PREFIX_1).append(pageNo).append(SQL_PREFIX_2).append(SQL_PREFIX_3)
          .append(sql)
          .append(SQL_POSTFIX).append(from).append(" AND ").append(to);
        return sb.toString();
    }

    public void setPagingInfo(Object parameterObject) {
        Map map = (Map)parameterObject;
        setPagingInfo(NumberUtils.toInt(map.get(VAR_NEXT_PAGE_).toString()),
                      NumberUtils.toInt(map.get(VAR_ROW_PER_PAGE_).toString()));
    }
}
