package myspring.common.enums;

import static myspring.common.support.Constants.ROWTYPE_DELETE;
import static myspring.common.support.Constants.ROWTYPE_INSERT;
import static myspring.common.support.Constants.ROWTYPE_NORMAL;
import static myspring.common.support.Constants.ROWTYPE_UPDATE;

public enum RowType {
    NORMAL(ROWTYPE_NORMAL),
    INSERT(ROWTYPE_INSERT),
    UPDATE(ROWTYPE_UPDATE),
    DELETE(ROWTYPE_DELETE);

    /**
     * RowType 머리글자
     * <ul><li>Normal : N</li><li>Insert : I</li><li>Update : U</li><li>Delete : D</li></ul>
     */
    public String initialLetter;

    /**
     * default Constructor
     * @param initialLetter - RowType 머리글자
     */
    RowType(String initialLetter) {
        this.initialLetter = initialLetter;
    }

    @Override
    public String toString() {
        return initialLetter;
    }
}
