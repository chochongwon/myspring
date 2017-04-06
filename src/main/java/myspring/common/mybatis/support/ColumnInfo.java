package myspring.common.mybatis.support;

import java.io.Serializable;
import java.sql.Types;

/**
 * ResultSetMetaData 로부터 뽑은 Column 정보 저장.
 *
 * @author 댐뽀리
 */
@SuppressWarnings("serial")
public class ColumnInfo implements Serializable{
    public final static short DEFAULT_TYPE = Types.VARCHAR;
    public final static int DEFAULT_SIZE = 256;
    public String columnName;

    /**
     * MiPlatform column Type
     */
    public short columnType;

    public int columnSize;

    public ColumnInfo(String columnName, short columnType, int columnSize) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnSize = columnSize;
    }

    public ColumnInfo(String columnName, short columnType) {
        this(columnName, columnType, DEFAULT_SIZE);
    }

    public ColumnInfo(String columnName) {
        this(columnName, DEFAULT_TYPE, DEFAULT_SIZE);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Column Info[").append(columnName).append(", ")
          .append(columnType).append(", ")
          .append(columnSize).append("]");
        return sb.toString();
    }
}
