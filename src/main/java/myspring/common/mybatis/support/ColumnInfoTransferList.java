package myspring.common.mybatis.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import myspring.common.data.WebUtil;
/**
 * 단순 ColumnInfo 전달을 위한 목적으로 태어났다.
 *
 * @author 
 * @param <E>
 */
public class ColumnInfoTransferList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 3041493454756720755L;
    
    /**
     * Commons Logger for this Class
     */
    private final static Log logger = LogFactory.getLog(ColumnInfoTransferList.class);
    
    private List<ColumnInfo> columnInfoList;

    /**
     * Default Constructor
     */
    public ColumnInfoTransferList() {
    }

    /**
     * @param c - the collection whose elements are to be placed into this list.
     */
    public ColumnInfoTransferList(Collection<? extends E> c) {
        super(c);
    }

    /**
     * @param initialCapacity - the initial capacity of the list.
     */
    public ColumnInfoTransferList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @return the columnInfoList
     */
    public List<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    /**
     * @param columnInfoList the columnInfoList to set
     */
    public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }

    /**
     * injection column 정보를 columninfo 에 삽입
     * @deprecated 쓸일 없음
     */
    @SuppressWarnings("unchecked")
    public void injectColumnInfo(Object ... injectColumns) throws Exception {
        // inject column info
        for(int i=0; i<injectColumns.length; i+=2) {
            columnInfoList.add(new ColumnInfo(injectColumns[i].toString(), WebUtil.getColumnType(injectColumns[i+1].getClass())));
            if(logger.isDebugEnabled()) {
                logger.debug("Column Info Injected : " + injectColumns[i]+", " + String.class.getName());
            }
        }

        // inject column value
        for(Map map : (List<Map>)this) {
            for(int i=0; i<injectColumns.length; i+=2) {
                if(i+1 >= injectColumns.length) {
                    break;
                }
                if(!map.containsKey(injectColumns[i])) {
                    map.put(injectColumns[i], injectColumns[i+1]);
                }
            }
        }
    }
}
