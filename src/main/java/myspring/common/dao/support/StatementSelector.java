package myspring.common.dao.support;

import myspring.common.enums.RowType;

/**
 * batch 처리를 위한 RowType Statement Selector Interface.
 * <p>
 * Dataset 의 RowType 에 따라 실행할 SQL Statment name을 selection 한다.
 * </p>
 *
 * @see
 * @author 
 */
public interface StatementSelector {
    /**
     * RowType 에 따른 SQL Statement 명을 리턴한다.
     *
     * @param type Dataset 의 등록/수정/삭제 여부.
     * @return RowType에 따라 실행할 SQL Statement 명.
     */
    public String select(RowType type);
}
