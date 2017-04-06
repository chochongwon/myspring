package myspring.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import myspring.common.enums.RowType;

import static myspring.common.support.Constants.REXPERT_FILE_ID;
import static myspring.common.support.Constants.ROW_TYPE_COLUMN;

public abstract class CommonUtil {
    /**
     * Commons Logger for this Class
     */
    private final static Log logger = LogFactory.getLog(CommonUtil.class);

    /**
     * Dataset(List of Map) 을 전달 받아 모든 row에 column 정보가 존재하는지 체크한다.
     * @param list 검사할 Dataset
     * @param colNames 존재 여부 check할 column array
     * @throws IllegalArgumentException column 존재하지 않음. 또는 검사할 Dataset 이 null
     */
    public static void assertColumn(List<Map> list, String ... colNames) {
        if(list == null) {
            throw new IllegalArgumentException("dataset is null ");
        }
        for(Map row : list) {
            assertColumn(row, colNames);
        }
    }

    /**
     * Map 안에 column 정보가 존재하는지 체크한다.
     * @param map 검사할 Map
     * @param colNames 존재 여부 check할 column array
     * @throws IllegalArgumentException column 존재하지 않음.
     */
    public static void assertColumn(Map map, String ... colNames) {
        for(String name : colNames) {
            if(!map.containsKey(name)) {
                throw new IllegalArgumentException("column required : " + name);
            }
        }
    }

    /**
     * List<Map> 을 전달받아 각 map 에 돌아가며 item을 삽입해 준다.
     * Map 에 이미 추가할 key 값으로 item이 들어가 있었다면? replace된다.
     * <p>Syntax : injectColumnValue(list, key0, value0, key1, value1 ...)</p>
     *
     * @param list List<Map>
     * @param arguments list에 추가할 item의 key, value 값의 variable list
     */
    public static void injectColumnValues(List<Map> list, Object ... arguments) {
        for(Map row : list) {
            injectColumnValues(row, arguments);
        }
    }

    /**
     * Map 을 전달받아 각 map 에 돌아가며 item을 삽입해 준다.
     * Map 에 이미 추가할 key 값으로 item이 들어가 있었다면? replace된다.
     * <p>Syntax : injectColumnValue(map, key0, value0, key1, value1 ...)</p>
     *
     * @param arguments list에 추가할 item의 key, value 값의 variable list
     */
    @SuppressWarnings("unchecked")
    public static void injectColumnValues(Map map, Object ... arguments) {
        for(int i=0; i<arguments.length; i+=2) {
            if(i+1 >= arguments.length) {
                break;
            }
            map.put(arguments[i], arguments[i+1]);
        }
    }
    
    /**
     * List<Map> 을 전달받아 각 map 에 돌아가며 item을 삽입해 준다.
     * Map 에 이미 추가할 key 값으로 item이 들어가 있다면 무시한다.
     * <p>Syntax : injectColumnValue(list, key0, value0, key1, value1 ...)</p>
     *
     * @param list List<Map>
     * @param arguments list에 추가할 item의 key, value 값의 variable list
     */
    public static void injectColumnValuesNoReplace(List<Map> list, Object ... arguments) {
        for(Map row : list) {
            injectColumnValuesNoReplace(row, arguments);
        }
    }
    
    /**
     * Map 을 전달받아 각 map 에 돌아가며 item을 삽입해 준다.
     * Map 에 이미 추가할 key 값으로 item이 들어가 있다면 무시한다.
     * <p>Syntax : injectColumnValue(map, key0, value0, key1, value1 ...)</p>
     *
     * @param arguments list에 추가할 item의 key, value 값의 variable list
     */
    @SuppressWarnings("unchecked")
    public static void injectColumnValuesNoReplace(Map map, Object ... arguments) {
        for(int i=0; i<arguments.length; i+=2) {
            if(i+1 >= arguments.length) {
                break;
            }
            if(!map.containsKey(arguments[i])) {
                map.put(arguments[i], arguments[i+1]);
            }
        }
    }

    /**
     * RowType 머리글자를 받아 RowType enum 객체를 리턴한다.
     *
     * @param initialLetter - RowType 머리글자
     *                        <ul><li>Normal : N</li><li>Insert : I</li><li>Update : U</li><li>Delete : D</li></ul>
     * @return 머리글자에 대응하는 RowType 객체, 잘못하면 RowType.NORMAL
     */
    public static RowType getRowType(String initialLetter) {
        for(RowType i : RowType.values()) {
            if(i.initialLetter.equals(initialLetter)) {
                return i;
            }
        }

        return  RowType.NORMAL;
    }

    /**
     * Dataset Row 의 RowType 리턴
     * @param row Dataset List 의 Row 정보를 담은 Map 객체
     * @return RowType 객체
     */
    public static RowType getRowType(Map row) {
        return getRowType((String)row.get(ROW_TYPE_COLUMN));
    }

    /**
     * Dataset List 의 특정 RowType 만 걸러낸다.
     * 주의 : Shallow Copy 가 적용된다. Deep Copy 를 원하는 경우 cloneRowTypeList 메소드를 사용하자.
     *
     * @param list - Dataset List
     * @param type - 골라낼 RowType
     * @return 지정된 RowType 에 해당되는 Row로만 구성된 List 객체
     */
    public static List<Map> getRowTypeList(List<Map> list, RowType ... types) {
        List<Map> result = new ArrayList<Map>();
        for(Map row : list) {
            RowType rowType = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));
            for(RowType tt : types) {
                if(rowType.equals(tt)) {
                    result.add(row);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Dataset List 로부터 RowType.NORMAL 인 row를 제거한 리스트를 리턴한다.
     */
    public static List<Map> getRowTypeSafeList(List<Map> list) {
        List<Map> result = new ArrayList<Map>();
        for(Map row : list) {
            RowType rowType = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));
            switch(rowType) {
                case INSERT:
                case UPDATE:
                case DELETE:
                    result.add(row);
                    break;
                default:
                    break;
            }
        }
        return result;
    }
    /**
     * Dataset List 로부터 RowType.INSERT 에 해당하는 row만 추출한다.
     */
    public static List<Map> getRowTypeInsertList(List<Map> list) {
        return getRowTypeList(list, RowType.INSERT);
    }
    /**
     * Dataset List 로부터 RowType.UPDATE 에 해당하는 row만 추출한다.
     */
    public static List<Map> getRowTypeUpdateList(List<Map> list) {
        return getRowTypeList(list, RowType.UPDATE);
    }
    /**
     * Dataset List 로부터 RowType.DELETE 에 해당하는 row만 추출한다.
     */
    public static List<Map> getRowTypeDeleteList(List<Map> list) {
        return getRowTypeList(list, RowType.DELETE);
    }
    /**
     * Dataset List 로부터 RowType.NORMAL 에 해당하는 row만 추출한다.
     */
    public static List<Map> getRowTypeNormalList(List<Map> list) {
        return getRowTypeList(list, RowType.NORMAL);
    }

    /**
     * Dataset List 의 특정 RowType 만 걸러낸 새로운 List<Map> 객체를 리턴한다.
     * 주의 : Deep Copy 가 적용된다. Shallow Copy 를 원하는 경우 getRowTypeList 메소드를 사용하자.
     *
     * @param list - Dataset List
     * @param type - 골라낼 RowType
     * @throws IOException - DeepCopy 실패시
     * @return 지정된 RowType 에 해당되는 Row로만 구성된 새로운 List 객체
     */
    public static List<Map> cloneRowTypeList(List<Map> list, RowType ... types) throws IOException {
        List<Map> result = new ArrayList<Map>();
        for(Map row : list) {
            RowType rowType = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));
            for(RowType tt : types) {
                if(rowType.equals(tt)) {
                    Map copy = (Map)DeepCopyUtil.copy(row);
                    result.add(copy);
                    break;
                }
            }
        }
        return result;
    }    

    /**
     * Dataset List 로부터 RowType.NORMAL 인 row를 제거한 새로운 리스트를 리턴한다.
     * 주의 : Deep Copy 가 적용된다. Shallow Copy 를 원하는 경우 getRowTypeSafeList 메소드를 사용하자.
     */
    public static List<Map> cloneRowTypeSafeList(List<Map> list) throws IOException {
        List<Map> result = new ArrayList<Map>();
        for(Map row : list) {
            RowType rowType = CommonUtil.getRowType((String)row.get(ROW_TYPE_COLUMN));
            switch(rowType) {
                case INSERT:
                case UPDATE:
                case DELETE:
                    Map copy = (Map)DeepCopyUtil.copy(row);
                    result.add(copy);
                    break;
                default:
                    break;
            }
        }
        return result;
    }    

    /**
     * Dataset List 로부터 RowType.INSERT 에 해당하는 row만 추출한다.
     * 주의 : Deep Copy 가 적용된다. Shallow Copy 를 원하는 경우 getRowType 시리즈를 사용하자.
     */
    public static List<Map> cloneRowTypeInsertList(List<Map> list) throws IOException {
        return cloneRowTypeList(list, RowType.INSERT);
    }
    /**
     * Dataset List 로부터 RowType.UPDATE 에 해당하는 row만 추출한다.
     * 주의 : Deep Copy 가 적용된다. Shallow Copy 를 원하는 경우 getRowType 시리즈를 사용하자.
     */
    public static List<Map> cloneRowTypeUpdateList(List<Map> list) throws IOException {
        return cloneRowTypeList(list, RowType.UPDATE);
    }
    /**
     * Dataset List 로부터 RowType.DELETE 에 해당하는 row만 추출한다.
     * 주의 : Deep Copy 가 적용된다. Shallow Copy 를 원하는 경우 getRowType 시리즈를 사용하자.
     */
    public static List<Map> cloneRowTypeDeleteList(List<Map> list) throws IOException {
        return cloneRowTypeList(list, RowType.DELETE);
    }
    /**
     * Dataset List 로부터 RowType.NORMAL 에 해당하는 row만 추출한다.
     * 주의 : Deep Copy 가 적용된다. Shallow Copy 를 원하는 경우 getRowType 시리즈를 사용하자.
     */
    public static List<Map> cloneRowTypeNormalList(List<Map> list) throws IOException {
        return cloneRowTypeList(list, RowType.NORMAL);
    }

    public static void sort(List list, final String columnName) {
        sort(list, columnName, true);
    }
    public static void sort(List list, final String columnName, final boolean isAsc) {
        if(!CollectionUtils.isEmpty(list)) {
            Collections.sort(list, new Comparator<Map>() {
                public int compare(Map o1, Map o2) {
                    String name1 = (String) o1.get(columnName);
                    String name2 = (String) o2.get(columnName);
                    return (isAsc) ? name1.compareTo(name2) : name2.compareTo(name1);
                }
            });
        }
    }
    
}
