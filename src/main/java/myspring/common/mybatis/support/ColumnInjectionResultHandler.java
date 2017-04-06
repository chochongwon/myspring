package myspring.common.mybatis.support;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import myspring.common.data.WebUtil;
import myspring.common.util.CommonUtil;

/**
 * 조회결과에 강제로 Column 정보 삽입을 위한 Custom RowHandler.
 * <p>
 * handleRow 처리시에 강제로 Column 정보 주입한다.
 * <p>
 * <code>ColumnInfoResultHandler</code> 로부터 상속 받는것도 주목하자.
 * </p>
 * @author 
 */
public class ColumnInjectionResultHandler extends ColumnInfoResultHandler {

    /**
     * Commons Logging Instance.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    private Object[] injectColumns;

    /**
     * 주입을 위한 정보를 받아들인다.
     *
     * @param arguments - 주입할 column 정보 배열, key, value 페어로 죽죽...
     */
    public ColumnInjectionResultHandler(Object ... arguments) {
        this.injectColumns = arguments;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getList() {
        List list = super.getList();
        CommonUtil.injectColumnValues(list, injectColumns);
        return list;
    }

    @Override
    public List<ColumnInfo> getColumnInfoList() {
        List<ColumnInfo> columnInfoList = super.getColumnInfoList();

        if(columnInfoList != null && !ArrayUtils.isEmpty(injectColumns)) {
            
            for(int i=0; i<injectColumns.length; i+=2) {
                if(i+1 >= injectColumns.length) {
                    break;
                }
                ColumnInfo info = new ColumnInfo(String.valueOf(injectColumns[i]), WebUtil.getColumnType(injectColumns[i+1].getClass()));
                if(!columnInfoList.contains(info)) {
                    columnInfoList.add(info);
                }
            }
        }
        return columnInfoList;
    }
}
