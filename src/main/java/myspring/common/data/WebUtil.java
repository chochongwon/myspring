package myspring.common.data;

import static myspring.common.support.Constants.CHECKBOX_COLUMN;
import static myspring.common.support.Constants.FIRST_IN_DS_NM;
import static myspring.common.support.Constants.ROW_TYPE_COLUMN;
import static myspring.common.support.Constants.TYPE_OBJECT;
import static myspring.common.support.Constants.VIRTUAL_COULMN;
import static myspring.common.support.Constants.DECRYPT_COULMN;
import static myspring.common.support.Constants.COLTYPE_DATE;
import static myspring.common.support.Constants.COLTYPE_DECIMAL;
import static myspring.common.support.Constants.COLTYPE_INT;
import static myspring.common.support.Constants.COLTYPE_STRING;
import static myspring.common.support.Constants.COLTYPE_BLOB;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import myspring.common.mybatis.support.ColumnInfo;
import myspring.common.mybatis.support.ColumnInfoTransferList;

public class WebUtil {

    /**
     * Commons Logger for this Class
     */
    private final static Log logger = LogFactory.getLog(WebUtil.class);
    
    private final static int CLOB_CHAR_BUFFER_SIZE = 8 * 1024;
    
    /**
     * JDBC column type을 MiPlatform column type으로 변환하여 리턴
     *
     * @param colType JDBC 컬럼 타입
     */
    public static short getColumnType(int colType) {
        short rtn;
        switch(colType) {
            case Types.DECIMAL:
            case Types.NUMERIC:
            case Types.DOUBLE:
            case Types.FLOAT:
                rtn = COLTYPE_DECIMAL;
                break;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR :
                rtn = COLTYPE_STRING;
                break;
            case Types.SMALLINT:
            case Types.TINYINT:
            case Types.INTEGER:
                rtn = COLTYPE_INT;
                break;
            case Types.DATE:
            case Types.TIMESTAMP:
                rtn = COLTYPE_DATE;
                break;
            case Types.BLOB:
                rtn = COLTYPE_BLOB;
                break;
            default:
                rtn = COLTYPE_STRING;
        }
        return rtn;
    }
    /**
     * JDBC column type을 MiPlatform column type string 으로 변환하여 리턴
     *
     * @param colType JDBC 컬럼 타입
     */
    public static String getColumnTypeString(int colType) {
        String rtn = "STRING";
        switch(colType) {
            case Types.DECIMAL:
            case Types.NUMERIC:
            case Types.DOUBLE:
            case Types.FLOAT:
                rtn =  "DECIMAL";
                break;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR :
                rtn =  "STRING";
                break;
            case Types.SMALLINT:
            case Types.TINYINT:
            case Types.INTEGER:
                rtn =  "INT";
                break;
            case Types.DATE:
                rtn =  "DATE";
                break;
            case Types.BLOB:
                rtn =  "BLOB";
                break;
            default:
                rtn = "STRING";
        } 
        return rtn;
    }    
    
    /**
     * Java Class 유형을 MiPlatform column type으로 변환하여 리턴
     *
     * @param clazz Java Class Object
     */
    public static short getColumnType(Class clazz) {
        short rtn;
        if(clazz == null || clazz.equals(java.lang.String.class)) {
            rtn = COLTYPE_STRING;
        } else
        if(clazz.equals(java.lang.Integer.class) || clazz.equals(java.lang.Long.class)) {
            rtn = COLTYPE_INT;
        } else
        if(clazz.equals(java.lang.Float.class) || clazz.equals(java.lang.Double.class) || clazz.equals(java.math.BigDecimal.class)) {
            rtn = COLTYPE_DECIMAL;
        } else
        if(clazz.equals(java.sql.Blob.class) || clazz.toString().equals("class oracle.sql.BLOB")) {
            rtn = COLTYPE_BLOB;
        } else {
            rtn = COLTYPE_STRING;
        }
        
        //if("class oracle.sql.BLOB".equals(clazz.toString())) System.out.println("clazz BLOB equal");
        //else System.out.println("clazz is not equal BLOB");
        //System.out.println("clazz is " + clazz +", toString="+clazz.toString());
        //System.out.println("MiUtil getMIColumnType Class colType >>> "+rtn);
        return rtn;
    }

    /**
     * Java Class 유형을 column type string으로 변환하여 리턴
     *
     * @param clazz Java Class Object
     */
    public static String getColumnTypeString(Class clazz) {
        String rtn = "STRING";
        if(clazz == null || clazz.equals(java.lang.String.class)) {
            rtn = "STRING";
        } else
        if(clazz.equals(java.lang.Integer.class) || clazz.equals(java.lang.Long.class)) {
            rtn = "INT";
        } else
        if(clazz.equals(java.lang.Float.class) || clazz.equals(java.lang.Double.class) || clazz.equals(java.math.BigDecimal.class)) {
            rtn = "DECIMAL";
        } else
        if(clazz.equals(java.sql.Blob.class) || clazz.toString().equals("class oracle.sql.BLOB")) {
            rtn = "BLOB";
        } else {
            rtn = "STRING";
        }
        
        //System.out.println("clazz is " + clazz +", toString="+clazz.toString());
        //System.out.println("getColumnTypeString class colType >>> "+rtn);
        return rtn;
    }

    /**
     * Object 를 Variant 로 변환
     */
    public static Variant getVariant(Object value) {
        return new Variant(value == null ? "" : value);
    }
    

    /**
     * 컬럼명 배열을 받아 비어있는 ColumnInfoTransferList 를 리턴
     */
    public static List<Map> getColumnInfoTransferList(String[] columnNames) {
        
        List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        for(String columnName : columnNames) {
            columnInfoList.add(new ColumnInfo(columnName, COLTYPE_STRING, 256));
        }
        ColumnInfoTransferList<Map> list = new ColumnInfoTransferList<Map>();
        list.setColumnInfoList(columnInfoList);
        
        return list;
    }
}
