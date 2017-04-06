package myspring.common.util;

import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    static final double HALF_SECOND = 5.7870370370370367E-006D;
    static final long MIN_DATE = 0L;
    static final long MAX_DATE = 3652424L;
    static final int MONTH_DAYS[] = {
        0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 
        304, 334, 365
    };

    private DateConverter()
    {
    }

    public static Date toDate(double d)
    {
        if(d > 3652424D || d < 0.0D)
            return null;
        Calendar calendar = Calendar.getInstance();
        boolean flag = true;
        double d1 = d;
        d1 += d <= 0.0D ? -5.7870370370370367E-006D : 5.7870370370370367E-006D;
        long l2 = (long)d1;
        d1 = Math.abs(d1);
        long l3 = (long)((d1 - Math.floor(d1)) * 86400D);
        long l6 = l2 / 146097L;
        l2 %= 146097L;
        long l7 = (l2 - 1L) / 36524L;
        long l8;
        long l9;
        if(l7 != 0L)
        {
            l2 = (l2 - 1L) % 36524L;
            l8 = (l2 + 1L) / 1461L;
            if(l8 != 0L)
            {
                l9 = (l2 + 1L) % 1461L;
            } else
            {
                flag = false;
                l9 = l2;
            }
        } else
        {
            l8 = l2 / 1461L;
            l9 = l2 % 1461L;
        }
        long l10;
        if(flag)
        {
            l10 = (l9 - 1L) / 365L;
            if(l10 != 0L)
                l9 = (l9 - 1L) % 365L;
        } else
        {
            l10 = l9 / 365L;
            l9 %= 365L;
        }
        int i2 = (int)(l6 * 400L + l7 * 100L + l8 * 4L + l10);
        if(l10 == 0L && flag)
        {
            if(l9 == 59L)
            {
                byte byte1 = 2;
                byte byte0 = 29;
                int i;
                int k;
                int i1;
                if(l3 == 0L)
                {
                    i1 = k = i = 0;
                } else
                {
                    i = (int)l3 % 60;
                    long l4 = l3 / 60L;
                    k = (int)l4 % 60;
                    i1 = (int)l4 / 60;
                }
                calendar.set(1, i2);
                calendar.set(2, byte1 - 1);
                calendar.set(5, byte0);
                calendar.set(11, i1);
                calendar.set(12, k);
                calendar.set(13, i);
                return calendar.getTime();
            }
            if(l9 >= 60L)
                l9--;
        }
        l9++;
        int l1;
        for(l1 = (int)(l9 >> 5) + 1; l9 > (long)MONTH_DAYS[l1]; l1++);
        int k1 = (int)(l9 - (long)MONTH_DAYS[l1 - 1]);
        int j;
        int l;
        int j1;
        if(l3 == 0L)
        {
            j1 = l = j = 0;
        } else
        {
            j = (int)l3 % 60;
            long l5 = l3 / 60L;
            l = (int)l5 % 60;
            j1 = (int)l5 / 60;
        }
        calendar.set(1, i2);
        calendar.set(2, l1 - 1);
        calendar.set(5, k1);
        calendar.set(11, j1);
        calendar.set(12, l);
        calendar.set(13, j);
        return calendar.getTime();
    }

    public static double toDouble(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(1);
        int j = calendar.get(2) + 1;
        int k = calendar.get(5);
        int l = calendar.get(11);
        int i1 = calendar.get(12);
        int j1 = calendar.get(13);
        return toDouble(i, j, k, l, i1, j1);
    }

    private static double toDouble(int i, int j, int k, int l, int i1, int j1)
    {
        boolean flag = (i & 3) == 0 && (i % 100 != 0 || i % 400 == 0);
        long l1 = (((long)i * 365L + (long)(i / 4)) - (long)(i / 100)) + (long)(i / 400) + (long)MONTH_DAYS[j - 1] + (long)k;
        if(j <= 2 && flag)
            l1--;
        double d = (double)((long)l * 3600L + (long)i1 * 60L + (long)j1) / 86400D;
        return (double)l1 + (l1 < 0L ? -d : d);
    }
}
