package myspring.common.dao.support;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import myspring.common.enums.RowType;

/**
 * Default Statement Name Selector Class.
 *
 * RowType 에 따른 statement 리턴.
 * <p>생성시 query ID 가 "OpQuery.saveQuery" 일때 default selector는 다음과 같이 리턴한다.
 * <ul>
 *  <li>I : OpQuery.insertQuery</li>
 *  <li>U : OpQUery.updateQuery</li>
 *  <li>D : OpQuery.deleteQuery</li>
 * </ul>
 * </p>
 * @author 
 */
public class DefaultStatementSelector implements StatementSelector {

    /**
     * RowType.INSERT Statement
     */
    private String insertStatement;
    /**
     * RowType.UPDATE Statement
     */
    private String updateStatement;
    /**
     * RowType.DELETE Statement
     */
    private String deleteStatement;

    private final static String INSERT_PREFIX = "insert";
    private final static String UPDATE_PREFIX = "update";
    private final static String DELETE_PREFIX = "delete";

    /**
     * DefaultStatementSelector 생성자.
     * namespace와 baseStatement를 구성한다.
     *
     * @param queryId selector 구성을 위한 쿼리 ID.
     */
    public DefaultStatementSelector(String queryId) {
        String[] arr = StringUtils.split(queryId, ".");
        if(arr == null || arr.length != 2) {
            throw new IllegalArgumentException("DefaultStatementSelector creation failed");
        }
        String nameSpace =arr[0];
        String baseStatement = stripPrefix(arr[1]);

        insertStatement = nameSpace + "." + INSERT_PREFIX + baseStatement;
        updateStatement = nameSpace + "." + UPDATE_PREFIX + baseStatement;
        deleteStatement = nameSpace + "." + DELETE_PREFIX + baseStatement;
    }
    /**
     * 쿼리 ID의 prefix를 제거하여 리턴.
     * <p>실제로는 첫번재 대문자를 만나면 거기서부터 substring하여 리턴한다.</p>
     * @return 처음 만나는 대문자부터 시작되는 String. 대문자가 하나도 없으면 그냥 input String.
     */
    private String stripPrefix(String str) {
        char[] chArr = str.toCharArray();
        for(int i=0; i<chArr.length; i++) {
            if(CharUtils.isAsciiAlphaUpper(chArr[i])) {
                return str.substring(i);
            }
        }
        return str;
    }
    /**
     * RowType 에 따른 statement 리턴.
     * nameSpace와 baseStatement에 prefix를 추가해 RowType별 statement id를 리턴한다.
     */
    @Override
    public String select(RowType type) {
        switch(type) {
            case INSERT: return insertStatement;
            case UPDATE: return updateStatement;
            case DELETE: return deleteStatement;
            default: throw new UnsupportedOperationException("MIRowType : " + type + " not supported");
        }
    }
}
