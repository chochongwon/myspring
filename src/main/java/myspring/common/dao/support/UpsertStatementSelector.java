package myspring.common.dao.support;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import myspring.common.enums.RowType;

/**
 * Upsert Statement Name Selector Class.
 *
 * Merge Into query 사용을 위한 Statement Name Selector.
 *
 * <p>생성시 query ID 가 "OpQuery.saveQuery" 일때 default selector는 다음과 같이 리턴한다.
 * <ul>
 *  <li>I : OpQuery.upsertQuery</li>
 *  <li>U : OpQUery.upsertQuery</li>
 *  <li>D : OpQuery.deleteQuery</li>
 * </ul>
 * </p>
 * @author 
 */
public class UpsertStatementSelector implements StatementSelector {

    /**
     * RowType.INSERT RowType.UPDATE Statement
     */
    private String upsertStatement;
    /**
     * RowType.DELETE Statement
     */
    private String deleteStatement;

    private final static String UPSERT_PREFIX = "upsert";
    private final static String DELETE_PREFIX = "delete";

    /**
     * UpsertStatementSelector 생성자.
     * namespace와 baseStatement를 구성한다.
     *
     * @param queryId selector 구성을 위한 쿼리 ID.
     */
    public UpsertStatementSelector(String queryId) {
        String[] arr = StringUtils.split(queryId, ".");
        if(arr == null || arr.length != 2) {
            throw new IllegalArgumentException("UpsertStatementSelector creation failed");
        }
        String nameSpace =arr[0];
        String baseStatement = stripPrefix(arr[1]);

        upsertStatement = queryId;
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
            case INSERT:
            case UPDATE:
                return upsertStatement;
            case DELETE:
                return deleteStatement;
            default:
                throw new UnsupportedOperationException("MIRowType : " + type + " not supported");
        }
    }

}
