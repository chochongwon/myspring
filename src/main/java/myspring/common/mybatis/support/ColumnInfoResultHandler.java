package myspring.common.mybatis.support;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import myspring.common.data.WebUtil;

public class ColumnInfoResultHandler extends DefaultResultHandler {
    /**
     * Commons Logging Instance.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * Column Info 저장
     */
    private List<ColumnInfo> columnInfoList;

    /**
     * default Constructor.
     */
    public ColumnInfoResultHandler() {
        columnInfoList = new ArrayList<ColumnInfo>();
    }

    /**
     * ResultSetMetaData 로부터 Column Info 추출.
     *
     * @param rs - ResultSet
     * @throws SQLException
     */
    public void extractColumnInfo(ResultSet rs) throws SQLException {
        if(logger.isDebugEnabled()) {
            logger.debug("extractColumnInfo from ResultSetMetaData");
        }
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        for (int i=1; i<=numberOfColumns; i++) {
            String colName = rsmd.getColumnName(i);
            int colType = rsmd.getColumnType(i);
            int colSize = rsmd.getColumnDisplaySize(i);

            columnInfoList.add(new ColumnInfo(colName, WebUtil.getColumnType(colType), colSize));
        }
    }

    /**
     * ColumnInfo list 리턴
     * @return List<ColumnInfo>
     */
    public List<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    /**
     * data list 와 함께 ColumnInfo 를 포함한 list를 리턴한다.
     * @return ColumnInfoTransferList 객체
     */
    @SuppressWarnings("unchecked")
    public List<Map> getColumnInfoTransferList() {
        ColumnInfoTransferList<Map> list = new ColumnInfoTransferList(getList());
        list.setColumnInfoList(getColumnInfoList());
        return list;
    }
    

}
