package myspring.sample.common;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/*
 * mybatis xml 에서 아래와 같이 사용가능하다.

<select id="testSQL" parameterType="map" resultType="hashmap">
    SELECT * FROM TB_TEST
    WHERE
        1=1
        <!-- @패키지.클래스명@호출할메소드(파라메터) -->
        <if test="@myspring.sample.common.MyComparator@isEmpty(keyword)">
            AND KEYWORD = #{keyword}
        </if>
</select>
 */
public class MyComparator {
    public static boolean isEmpty(Object obj){
        if( obj instanceof String ) return obj==null || "".equals(obj.toString().trim());
        else if( obj instanceof List ) return obj==null || ((List)obj).isEmpty();
        else if( obj instanceof Map ) return obj==null || ((Map)obj).isEmpty();
        else if( obj instanceof Object[] ) return obj==null || Array.getLength(obj)==0;
        else return obj==null;
    }
     
    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }
}
