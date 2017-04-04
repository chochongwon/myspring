package myspring.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.validation.ValidationUtils;

/**
 * Date 관련 Utility Class
 * Apache Commons Lang 페키지의 DateUtils 클래스를 상속받아 구현한다.
 * 날짜 포멧 변경은 역시 Commons Lang 페키지의 DateFormatUtils 를 사용한다.
 */
public abstract class DateUtil extends DateUtils {

    /**
     * 오라클식으로 이야기하면 YYYYMMDDHH24MISS 날짜 포멧 
     */
    public static final String DEFAULT_PATTERN = "yyyyMMddHHmmss";
    /**
     * @return 현재 시각을 담고 있는 Date 객체를 리턴
     */
    public static Date getDate() {
        return new GregorianCalendar().getTime();
    }

    /**
     * @return 현재 시각을 문자열로 리턴. format은 oracle style 로 이야기하면 (YYYYMMDDHH24MISS)
     */
    public static String getDateString() {
        return getDateString(DEFAULT_PATTERN);
    }
    
    /**
     * @param 날짜 포멧 java.util.SimpleDateFormat 에서 지정된 Pattern 을 따른 문자열
     * @return 현재 시각을 날짜 포멧을 적용한 문자열로 리턴 
     * @see java.util.SimpleDateFormat
     */
    public static String getDateString(String pattern) {
        Calendar cal = new GregorianCalendar();
        return DateFormatUtils.format(cal, pattern);
    }
    
    /**
     * Date 객체를 디폴트 페턴을 적용하여 문자열로 변환
     * @param date java.util.Date 객체
     * @return 디폴트 날짜 페턴을 적용하여 변환된 문자열 리턴 
     */
    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }
    
    /**
     * Date 객체를 Pattern 을 적용하여 문자열로 변환
     * @param date java.util.Date 객체
     * @param pattern java.util.SimpleDateFormat 에 지정된 날짜 변환 페턴 문자열
     * @return 날짜 페턴을 적용하여 변환된 문자열 리턴
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }
    
    /**
     * String 객체를 Pattern 을 적용하여 문자열로 변환 (validate용)
     * @param java.lang.String 객체
     * @param pattern java.util.SimpleDateFormat 에 지정된 날짜 변환 페턴 문자열
     * @return 날짜 페턴을 적용하여 해당 인자가 적당한 날짜 형식인지 판단함 (날짜형식 불량시 null리턴)
     */
    public static String parse(String strDate, String pattern) {
        SimpleDateFormat f = new SimpleDateFormat(pattern);

        try {
            f.setLenient(false);  //체크를 빡세게 함    
            Date d = f.parse(strDate);
            return strDate;
        }
        catch(ParseException e)
        {
           return null;
        }
    }

    
    
    /**
     * DateUtil Test
     */
    public static void main(String[] args) {
        //현재 일자
        System.out.println(getDateString()); //20100205192221
        System.out.println(getDateString("yyyy-MM-dd HH:mm:ss")); //2010-02-05 19:22:21
        
        //날짜 연산
        Date today = getDate();
        Date tomorrow = addDays(today, 1);
        Date yesterday = addDays(today, -1);
        
        System.out.println("오늘:" + format(today, "yyyy-MM-dd"));
        System.out.println("내일:" + format(tomorrow, "yyyy-MM-dd"));
        System.out.println("어제:" + format(yesterday, "yyyy-MM-dd"));
        System.out.println("담달 1일:" + format(yesterday, "yyyy-MM-dd"));
        
        Date nextMonth = addMonths(today, 1);
        nextMonth = setDays(nextMonth, 1);
        System.out.println("담달 1일:"+format(nextMonth, "yyyy-MM-dd"));
        
        Date endDate = addDays(nextMonth, -1); //다음달 1일에서 하루를 뺀다.
        System.out.println("이달 말일:"+format(endDate, "yyyy-MM-dd"));
        
        System.out.println("형변환"+ parse("00000000", "yyyyMMdd"));
    }
}