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

}
