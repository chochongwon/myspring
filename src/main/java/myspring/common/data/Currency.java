package myspring.common.data;

import java.math.BigDecimal;

public class Currency extends BigDecimal {


    public Currency()
    {
        super(0.0D);
    }

    public Currency(BigDecimal bigdecimal)
    {
        super(bigdecimal != null ? bigdecimal.doubleValue() : 0.0D);
    }

    public Currency(double d)
    {
        super(d);
    }

    public Currency(float f)
    {
        super(f);
    }

    public Currency(String s)
    {
        super(s);
    }

    public boolean equals(float f)
    {
        return compareTo(new Currency(f)) == 0;
    }

    public boolean equals(double d)
    {
        return compareTo(new Currency(d)) == 0;
    }

    public Currency add(Currency currency)
    {
        return new Currency(super.add(currency));
    }

    public Currency subtract(Currency currency)
    {
        return new Currency(super.subtract(currency));
    }

    public Currency multiply(Currency currency)
    {
        return new Currency(super.multiply(currency));
    }

    public Currency multiply(int i)
    {
        return multiply(new Currency(i));
    }

    public Currency divide(Currency currency)
        throws ArithmeticException, IllegalArgumentException
    {
        return new Currency(super.divide(currency, 4, 4));
    }

    public Currency divide(int i)
        throws ArithmeticException, IllegalArgumentException
    {
        return divide(new Currency(i));
    }

    public Currency absCurrency()
    {
        return signum() >= 0 ? this : negateCurrency();
    }

    public Currency negateCurrency()
    {
        return new Currency(negate());
    }

    public String toString()
    {
        return scale() != 4 ? setScale(4, 4).toString() : super.toString();
    }

    public static Currency fromInt64(int i, int j)
    {
        long l = 4294967295L & (long)i;
        long l1 = 4294967295L & (long)j;
        long l2 = l << 32 | l1;
        String s = Long.toString(l2);
        int k = s.length();
        if(k < 4)
            return new Currency(0.0F);
        if(k == 4)
            s = "0." + s;
        else
            s = s.substring(0, k - 4) + "." + s.substring(k - 4);
        return new Currency(s);
    }

    public static int[] toInt64(Currency currency)
    {
        int ai[] = new int[2];
        String s = currency.toString();
        int i = s.indexOf('.');
        if(i >= 0)
            s = s.substring(0, i) + s.substring(i + 1, s.length());
        long l = Long.parseLong(s);
        ai[0] = (int)(l >> 32);
        ai[1] = (int)(4294967295L & l);
        return ai;
    }
}
