package myspring.common.data;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import myspring.common.support.Constants;
import myspring.common.util.Base64Converter;
import myspring.common.util.DateConverter;

public class Variant {
    public static final short VT_EMPTY = 0;
    public static final short VT_I4 = 3;
    public static final short VT_R8 = 5;
    public static final short VT_CURRENCY = 6;
    public static final short VT_DATE = 7;
    public static final short VT_STR = 8;
    public static final short VT_BINARY = 13;
    public static final short VT_OBJECT = 16384;
    static final int BUFFER_SIZE = 8192;
    private short type;
    private Object value;


    public Variant()
    {
        type = 0;
    }

    public Variant(String s)
    {
        setString(s);
    }

    public Variant(int i)
    {
        setInteger(i);
    }

    public Variant(Integer integer)
    {
        setInteger(integer);
    }

    public Variant(double d)
    {
        setDouble(d);
    }

    public Variant(Double double1)
    {
        setDouble(double1);
    }

    public Variant(Currency currency)
    {
        setCurrency(currency);
    }

    public Variant(Date date)
    {
        setDate(date);
    }

    public Variant(byte abyte0[])
    {
        setBinary(abyte0);
    }

    public Variant(Object obj)
    {
        setObject(obj);
    }

    public static int toType(String s)
    {
        if(s.equalsIgnoreCase("null"))
            return 0;
        if(s.equalsIgnoreCase("EMPTY"))
            return 0;
        if(s.equalsIgnoreCase("INTEGER"))
            return 3;
        if(s.equalsIgnoreCase("DECIMAL"))
            return 5;
        if(s.equalsIgnoreCase("STRING"))
            return 8;
        if(s.equalsIgnoreCase("BINARY"))
            return 13;
        if(s.equalsIgnoreCase("DATE"))
            return 7;
        if(s.equalsIgnoreCase("CURRENCY"))
            return 6;
        return !s.equalsIgnoreCase("OBJECT") ? 0 : 16384;
    }

    public static int getTypeNo(String s)
    {
        return toType(s);
    }

    public int getType()
    {
        return type;
    }

    public void setType(short word0)
    {
        type = word0;
    }

    public String getTypeName()
    {
        if(type == 0)
            return "EMPTY";
        if(type == 3)
            return "INTEGER";
        if(type == 5)
            return "DECIMAL";
        if(type == 8)
            return "STRING";
        if(type == 13)
            return "BINARY";
        if(type == 7)
            return "DATE";
        if(type == 6)
            return "CURRENCY";
        if(type == 16384)
            return "OBJECT";
        else
            return "UNKNOWN";
    }

    public String getString()
    {
        if(value instanceof String)
            return (String)value;
        else
            return null;
    }

    public Integer getInteger()
    {
        if(value instanceof Integer)
            return (Integer)value;
        else
            return null;
    }

    public Double getDouble()
    {
        if(value instanceof Double)
            return (Double)value;
        else
            return null;
    }

    public Currency getCurrency()
    {
        if(value instanceof Currency)
            return (Currency)value;
        else
            return null;
    }

    public Date getDate()
    {
        if(value instanceof Date)
            return (Date)value;
        else
            return null;
    }

    public byte[] getBinary()
    {
        if(value instanceof byte[])
            return (byte[])value;
        else
            return null;
    }

    public Object getObject()
    {
        return value;
    }

    public void setEmpty()
    {
        type = 0;
        value = null;
    }

    public void setString(String s)
    {
        if(s == null)
        {
            setEmpty();
            return;
        } else
        {
            type = 8;
            value = s;
            return;
        }
    }

    public void setInteger(int i)
    {
        setInteger(new Integer(i));
    }

    public void setInteger(Integer integer)
    {
        if(integer == null)
        {
            setEmpty();
            return;
        } else
        {
            type = 3;
            value = integer;
            return;
        }
    }

    public void setDouble(double d)
    {
        setDouble(new Double(d));
    }

    public void setDouble(Double double1)
    {
        if(double1 == null)
        {
            setEmpty();
            return;
        } else
        {
            type = 5;
            value = double1;
            return;
        }
    }

    public void setCurrency(Currency currency)
    {
        if(currency == null)
        {
            setEmpty();
            return;
        } else
        {
            type = 6;
            value = currency;
            return;
        }
    }

    public void setDate(Date date)
    {
        if(date == null)
        {
            setEmpty();
            return;
        } else
        {
            type = 7;
            value = date;
            return;
        }
    }

    public void setDate(long l)
    {
        setDate(new Date(l));
    }

    public void setBinary(byte abyte0[])
    {
        if(abyte0 == null)
        {
            setEmpty();
            return;
        } else
        {
            type = 13;
            value = abyte0;
            return;
        }
    }

    public void setObject(Object obj)
    {
        if(obj == null)
        {
            setEmpty();
            return;
        }
        if(obj instanceof String)
            setString((String)obj);
        else
        if(obj instanceof Integer)
            setInteger((Integer)obj);
        else
        if(obj instanceof Double)
            setDouble((Double)obj);
        else
        if(obj instanceof Currency)
            setCurrency((Currency)obj);
        else
        if(obj instanceof Date)
            setDate((Date)obj);
        else
        if(obj instanceof byte[])
            setBinary((byte[])obj);
        else
        if(obj instanceof Variant)
        {
            copy((Variant)obj);
        } else
        {
            type = 16384;
            value = obj;
        }
    }

    public boolean isEmpty()
    {
        return type == 0;
    }

    public boolean isString()
    {
        return type == 8;
    }

    public boolean isInteger()
    {
        return type == 3;
    }

    public boolean isDouble()
    {
        return type == 5;
    }

    public boolean isCurrency()
    {
        return type == 6;
    }

    public boolean isDate()
    {
        return type == 7;
    }

    public boolean isBinary()
    {
        return type == 13;
    }

    public boolean isObject()
    {
        return type == 16384;
    }

    public void copy(Variant variant)
    {
        type = variant.type;
        value = variant.value;
    }

    public String asString()
    {
        switch(type)
        {
        case 0: // '\0'
            return "";

        case 8: // '\b'
            if(value instanceof String)
                return (String)value;
            else
                return value.toString();

        case 3: // '\003'
        case 5: // '\005'
        case 6: // '\006'
        case 16384: 
            if(value instanceof String)
                return (String)value;
            else
                return value.toString();

        case 7: // '\007'
            if(value instanceof Date)
                return Constants.DEFAULT_DATE_FORMATTER.format((Date)value);
            if(value instanceof String)
                return (String)value;
            else
                return value.toString();

        case 13: // '\r'
            if(value instanceof byte[])
                return Base64Converter.encode((byte[])value);
            if(value instanceof String)
                return (String)value;
            else
                return value.toString();
        }
        return String.valueOf(value);
    }

    public String toString()
    {
        return toString(true);
    }

    String toString(boolean flag)
    {
        String s = flag ? "Variant" : "";
        return s + "[type=" + getTypeName() + ", value=" + (type != 0 ? asString() : "null") + "]";
    }

    public void dump()
    {
        System.out.print("Type=[" + getTypeName() + "], ");
        if(type != 0)
            System.out.println("Value=[" + asString() + "]");
        else
            System.out.println("Value=[null]");
    }

    public boolean equals(Object obj)
    {
        Variant variant = (Variant)obj;
        if(type != variant.getType())
            return false;
        switch(type)
        {
        case 0: // '\0'
            return true;

        case 3: // '\003'
            return getInteger() == variant.getInteger();

        case 5: // '\005'
            return getDouble() == variant.getDouble();

        case 8: // '\b'
            return getString().compareTo(variant.getString()) == 0;

        case 13: // '\r'
            if(getBinary().length != variant.getBinary().length)
                return false;
            else
                return getBinary().equals(variant.getBinary());

        case 7: // '\007'
            return getDate().getTime() == variant.getDate().getTime();

        case 6: // '\006'
            return getCurrency().equals(variant.getCurrency());

        case 16384: 
            return getObject().equals(obj);
        }
        return false;
    }

    public void convertToType(short word0)
    {
        if(isEmpty())
            return;
        if(type == word0)
            return;
label0:
        switch(type)
        {
        case 0: // '\0'
            break;

        case 3: // '\003'
            switch(word0)
            {
            case 0: // '\0'
                setEmpty();
                break;

            case 5: // '\005'
                setDouble(((Integer)value).doubleValue());
                break;

            case 8: // '\b'
                setString(((Integer)value).toString());
                break;

            case 13: // '\r'
                int i = ((Integer)value).intValue();
                byte abyte0[] = new byte[4];
                abyte0[0] = (byte)(i >>> 24 & 255);
                abyte0[1] = (byte)(i >>> 16 & 255);
                abyte0[2] = (byte)(i >>> 8 & 255);
                abyte0[3] = (byte)(i & 255);
                setBinary(abyte0);
                break;

            case 7: // '\007'
                setDate(((Integer)value).longValue());
                break;

            case 6: // '\006'
                setCurrency(new Currency(((Integer)value).doubleValue()));
                break;

            case 16384: 
                type = 16384;
                break;
            }
            break;

        case 5: // '\005'
            switch(word0)
            {
            case 0: // '\0'
                setEmpty();
                break;

            case 3: // '\003'
                setInteger(((Double)value).intValue());
                break;

            case 8: // '\b'
                setString(((Double)value).toString());
                break;

            case 13: // '\r'
                long l = ((Double)value).longValue();
                byte abyte1[] = new byte[8];
                abyte1[0] = (byte)(int)(l >>> 56 & 255L);
                abyte1[1] = (byte)(int)(l >>> 48 & 255L);
                abyte1[2] = (byte)(int)(l >>> 40 & 255L);
                abyte1[3] = (byte)(int)(l >>> 32 & 255L);
                abyte1[4] = (byte)(int)(l >>> 24 & 255L);
                abyte1[5] = (byte)(int)(l >>> 16 & 255L);
                abyte1[6] = (byte)(int)(l >>> 8 & 255L);
                abyte1[7] = (byte)(int)(l & 255L);
                setBinary(abyte1);
                break;

            case 7: // '\007'
                setDate(((Double)value).longValue());
                break;

            case 6: // '\006'
                setCurrency(new Currency(((Double)value).doubleValue()));
                break;

            case 16384: 
                type = 16384;
                break;
            }
            break;

        case 8: // '\b'
            switch(word0)
            {
            case 8: // '\b'
            default:
                break;

            case 0: // '\0'
                setEmpty();
                break label0;

            case 3: // '\003'
                try
                {
                    setInteger(Integer.parseInt((String)value));
                }
                catch(NumberFormatException numberformatexception)
                {
                    setEmpty();
                }
                break label0;

            case 5: // '\005'
                try
                {
                    setDouble(Double.parseDouble((String)value));
                }
                catch(NumberFormatException numberformatexception1)
                {
                    setEmpty();
                }
                break label0;

            case 13: // '\r'
                setBinary(((String)value).getBytes());
                break label0;

            case 7: // '\007'
                try
                {
                    if(((String)value).length() == 8)
                        setDate(Constants.DEFAULT_SHORT_DATE_FORMATTER.parse((String)value));
                    else
                        setDate(Constants.DEFAULT_DATE_FORMATTER.parse((String)value));
                }
                catch(ParseException parseexception)
                {
                    setEmpty();
                }
                break label0;

            case 6: // '\006'
                try
                {
                    setCurrency(new Currency(Double.parseDouble((String)value)));
                }
                catch(NumberFormatException numberformatexception2)
                {
                    setEmpty();
                }
                break;

            case 16384: 
                type = 16384;
                break;
            }
            break;

        case 13: // '\r'
            setString(new String((byte[])value));
            convertToType(word0);
            break;

        case 7: // '\007'
            setString(asString());
            convertToType(word0);
            break;

        case 6: // '\006'
            setString(asString());
            convertToType(word0);
            break;

        case 16384: 
            setString(asString());
            convertToType(word0);
            break;

        default:
            setString(asString());
            convertToType(word0);
            break;
        }
    }

    public void readFrom(InputStream inputstream, String s)
        throws IOException
    {
        readFrom(new DataInputStream(inputstream), s);
    }

    public void readFrom(DataInputStream datainputstream, String s)
        throws IOException
    {
        readFrom(datainputstream, s, (short)3100);
    }

    void readFrom(DataInputStream datainputstream, String s, File file)
        throws IOException
    {
        readFrom(datainputstream, s, (short)3100);
    }

    void readFrom(DataInputStream datainputstream, String s, short word0)
        throws IOException
    {
        setType(datainputstream.readShort());
        if(getType() == 3)
            setInteger(datainputstream.readInt());
        else
        if(getType() == 5)
            setDouble(datainputstream.readDouble());
        else
        if(getType() == 7)
            setDate(DateConverter.toDate(datainputstream.readDouble()));
        else
        if(getType() == 6)
        {
            int i = datainputstream.readInt();
            int k = datainputstream.readInt();
            setCurrency(Currency.fromInt64(i, k));
        } else
        if(getType() == 8)
        {
            int j = 0;
            if(word0 >= 4000)
            {
                byte byte0 = datainputstream.readByte();
                if((byte0 & 128) != 0)
                {
                    j = byte0 << 24 & -16777216 | datainputstream.readByte() << 16 & 16711680 | datainputstream.readByte() << 8 & 65280 | datainputstream.readByte() & 255;
                    j &= 2147483647;
                } else
                {
                    j = byte0 << 8 & 65280 | datainputstream.readByte() & 255;
                }
            } else
            {
                j = datainputstream.readUnsignedShort();
            }
            byte abyte0[] = new byte[j];
            int i1 = 0;
            do
            {
                if(datainputstream.available() <= 0 && i1 >= j)
                    break;
                int j1 = datainputstream.read(abyte0, i1, abyte0.length - i1);
                if(j1 <= 0)
                    break;
                i1 += j1;
            } while(true);
            if(i1 != j)
                throw new IOException("Variant\uC758 \uBB38\uC790\uC5F4 \uAC12 \uC77D\uAE30 \uC624\uB958, length=" + j + ", count=" + i1);
            if(word0 >= 4000)
                setString(new String(abyte0, "utf-8"));
            else
            if(s == null)
                setString(new String(abyte0));
            else
                setString(new String(abyte0, s));
        } else
        if(getType() == 13)
        {
            long l = 0L;
            if(word0 >= 4000)
            {
                byte byte1 = datainputstream.readByte();
                if((byte1 & 128) != 0)
                {
                    l = byte1 << 24 & -16777216 | datainputstream.readByte() << 16 & 16711680 | datainputstream.readByte() << 8 & 65280 | datainputstream.readByte() & 255;
                    l &= 2147483647L;
                } else
                {
                    l = byte1 << 8 & 65280 | datainputstream.readByte() & 255;
                }
            } else
            {
                l = -1 & datainputstream.readInt();
            }
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(65536);
            byte abyte1[] = new byte[8192];
            long l1 = 0L;
            boolean flag = false;
            do
            {
                if(datainputstream.available() <= 0 && l1 >= l)
                    break;
                int k1;
                if(8192L > l - l1)
                    k1 = datainputstream.read(abyte1, 0, (int)(l - l1));
                else
                    k1 = datainputstream.read(abyte1, 0, 8192);
                if(k1 <= 0)
                    break;
                bytearrayoutputstream.write(abyte1, 0, k1);
                l1 += k1;
            } while(true);
            bytearrayoutputstream.close();
            if(l1 != l)
                throw new IOException("Variant\uC758 byte \uBC30\uC5F4 \uAC12 \uC77D\uAE30 \uC624\uB958, length=" + l + ", count=" + l1);
            setBinary(bytearrayoutputstream.toByteArray());
        }
    }

    void readFrom(DataInputStream datainputstream, String s, short word0, File file)
        throws IOException
    {
        short word1 = datainputstream.readShort();
        if(word1 == 3)
            setType((short)0);
        else
        if(word1 == 5)
            setType((short)0);
        else
        if(word1 == 7)
            setType((short)0);
        else
        if(word1 == 6)
            setType((short)0);
        else
        if(word1 == 8)
            setType((short)0);
        else
        if(word1 == 13)
        {
            long l = 0L;
            if(word0 >= 4000)
            {
                byte byte0 = datainputstream.readByte();
                if((byte0 & 128) != 0)
                {
                    l = byte0 << 24 & -16777216 | datainputstream.readByte() << 16 & 16711680 | datainputstream.readByte() << 8 & 65280 | datainputstream.readByte() & 255;
                    l &= 2147483647L;
                } else
                {
                    l = byte0 << 8 & 65280 | datainputstream.readByte() & 255;
                }
            } else
            {
                l = -1 & datainputstream.readInt();
            }
            FileOutputStream fileoutputstream = null;
            try
            {
                fileoutputstream = new FileOutputStream(file);
                byte abyte0[] = new byte[8192];
                long l1 = 0L;
                boolean flag = false;
                do
                {
                    if(datainputstream.available() <= 0 && l1 >= l)
                        break;
                    int i;
                    if(8192L > l - l1)
                        i = datainputstream.read(abyte0, 0, (int)(l - l1));
                    else
                        i = datainputstream.read(abyte0, 0, 8192);
                    if(i <= 0)
                        break;
                    fileoutputstream.write(abyte0, 0, i);
                    l1 += i;
                } while(true);
                fileoutputstream.close();
                if(l1 != l)
                    throw new IOException("Variant\uC758 byte \uBC30\uC5F4 \uAC12 \uC77D\uAE30 \uC624\uB958, length=" + l + ", count=" + l1);
                setType((short)8);
                setString(file.getAbsolutePath());
            }
            finally
            {
                if(fileoutputstream != null)
                    try
                    {
                        fileoutputstream.close();
                    }
                    catch(IOException ioexception) { }
            }
        } else
        {
            setType((short)0);
        }
    }

    public void readFrom2(InputStream inputstream)
        throws IOException
    {
        readFrom2(new DataInputStream(inputstream));
    }

    public void readFrom2(DataInputStream datainputstream)
        throws IOException
    {
        setType(datainputstream.readShort());
        switch(getType())
        {
        case 4: // '\004'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        default:
            break;

        case 3: // '\003'
            setInteger(datainputstream.readInt());
            break;

        case 5: // '\005'
            setDouble(datainputstream.readDouble());
            break;

        case 7: // '\007'
            setDate(DateConverter.toDate(datainputstream.readDouble()));
            break;

        case 6: // '\006'
            int i = datainputstream.readInt();
            int j = datainputstream.readInt();
            setCurrency(Currency.fromInt64(i, j));
            break;

        case 8: // '\b'
            int k = 0;
            byte byte0 = datainputstream.readByte();
            if((byte0 & 128) != 0)
            {
                k = byte0 << 24 & -16777216 | datainputstream.readByte() << 16 & 16711680 | datainputstream.readByte() << 8 & 65280 | datainputstream.readByte() & 255;
                k &= 2147483647;
            } else
            {
                k = byte0 << 8 & 65280 | datainputstream.readByte() & 255;
            }
            byte abyte0[] = new byte[k];
            int i1 = 0;
            do
            {
                if(datainputstream.available() <= 0 && i1 >= k)
                    break;
                int j1 = datainputstream.read(abyte0, i1, abyte0.length - i1);
                if(j1 <= 0)
                    break;
                i1 += j1;
            } while(true);
            if(i1 != k)
                throw new IOException("Variant\uC758 \uBB38\uC790\uC5F4 \uAC12 \uC77D\uAE30 \uC624\uB958, length=" + k + ", count=" + i1);
            setString(new String(abyte0, "utf-8"));
            break;

        case 13: // '\r'
            long l = 0L;
            byte byte1 = datainputstream.readByte();
            if((byte1 & 128) != 0)
            {
                l = byte1 << 24 & -16777216 | datainputstream.readByte() << 16 & 16711680 | datainputstream.readByte() << 8 & 65280 | datainputstream.readByte() & 255;
                l &= 2147483647L;
            } else
            {
                l = byte1 << 8 & 65280 | datainputstream.readByte() & 255;
            }
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(65536);
            byte abyte1[] = new byte[8192];
            long l1 = 0L;
            boolean flag = false;
            do
            {
                if(datainputstream.available() <= 0 && l1 >= l)
                    break;
                int k1;
                if(8192L > l - l1)
                    k1 = datainputstream.read(abyte1, 0, (int)(l - l1));
                else
                    k1 = datainputstream.read(abyte1, 0, 8192);
                if(k1 <= 0)
                    break;
                bytearrayoutputstream.write(abyte1, 0, k1);
                l1 += k1;
            } while(true);
            if(l1 != l)
                throw new IOException("Variant\uC758 byte \uBC30\uC5F4 \uAC12 \uC77D\uAE30 \uC624\uB958, length=" + l + ", count=" + l1);
            setBinary(bytearrayoutputstream.toByteArray());
            break;
        }
    }

    void readFrom2(DataInputStream datainputstream, File file)
        throws IOException
    {
        short word0 = datainputstream.readShort();
        switch(word0)
        {
        case 3: // '\003'
            setType((short)0);
            break;

        case 5: // '\005'
            setType((short)0);
            break;

        case 7: // '\007'
            setType((short)0);
            break;

        case 6: // '\006'
            setType((short)0);
            break;

        case 8: // '\b'
            setType((short)0);
            break;

        case 13: // '\r'
            long l = 0L;
            byte byte0 = datainputstream.readByte();
            if((byte0 & 128) != 0)
            {
                l = byte0 << 24 & -16777216 | datainputstream.readByte() << 16 & 16711680 | datainputstream.readByte() << 8 & 65280 | datainputstream.readByte() & 255;
                l &= 2147483647L;
            } else
            {
                l = byte0 << 8 & 65280 | datainputstream.readByte() & 255;
            }
            FileOutputStream fileoutputstream = null;
            try
            {
                fileoutputstream = new FileOutputStream(file);
                byte abyte0[] = new byte[8192];
                long l1 = 0L;
                boolean flag = false;
                do
                {
                    if(datainputstream.available() <= 0 && l1 >= l)
                        break;
                    int i;
                    if(8192L > l - l1)
                        i = datainputstream.read(abyte0, 0, (int)(l - l1));
                    else
                        i = datainputstream.read(abyte0, 0, 8192);
                    if(i <= 0)
                        break;
                    fileoutputstream.write(abyte0, 0, i);
                    l1 += i;
                } while(true);
                if(l1 != l)
                    throw new IOException("Variant\uC758 byte \uBC30\uC5F4 \uAC12 \uC77D\uAE30 \uC624\uB958, length=" + l + ", count=" + l1);
                setType((short)8);
                setString(file.getAbsolutePath());
                break;
            }
            finally
            {
                if(fileoutputstream != null)
                    try
                    {
                        fileoutputstream.close();
                    }
                    catch(IOException ioexception) { }
            }

        case 4: // '\004'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        default:
            setType((short)0);
            break;
        }
    }

    public void writeTo(OutputStream outputstream, String s)
        throws IOException
    {
        writeTo(new DataOutputStream(outputstream), s);
    }

    public void writeTo(DataOutputStream dataoutputstream, String s)
        throws IOException
    {
        writeTo(dataoutputstream, s, (short)3100);
    }

    void writeFileTo(DataOutputStream dataoutputstream, String s)
        throws IOException
    {
        writeFileTo(dataoutputstream, s, (short)3100);
    }

    void writeTo(DataOutputStream dataoutputstream, String s, short word0)
        throws IOException
    {
        dataoutputstream.writeShort(getType());
        if(getType() == 3)
            dataoutputstream.writeInt(getInteger().intValue());
        else
        if(getType() == 5)
            dataoutputstream.writeDouble(getDouble().doubleValue());
        else
        if(getType() == 8)
        {
            byte abyte0[] = null;
            if(word0 >= 4000)
            {
                abyte0 = getString().getBytes("utf-8");
                if(abyte0.length < 32768)
                    dataoutputstream.writeShort(abyte0.length);
                else
                    dataoutputstream.writeInt(abyte0.length | -2147483648);
            } else
            {
                if(s == null)
                    abyte0 = getString().getBytes();
                else
                    abyte0 = getString().getBytes(s);
                dataoutputstream.writeShort(abyte0.length);
            }
            dataoutputstream.write(abyte0);
        } else
        if(getType() == 13)
        {
            if(word0 >= 4000)
            {
                int i = getBinary().length;
                if(i < 32768)
                    dataoutputstream.writeShort(i);
                else
                    dataoutputstream.writeInt(i | -2147483648);
            } else
            {
                dataoutputstream.writeInt(getBinary().length);
            }
            dataoutputstream.write(getBinary());
        } else
        if(getType() == 7)
            dataoutputstream.writeDouble(DateConverter.toDouble(getDate()));
        else
        if(getType() == 6)
        {
            int ai[] = Currency.toInt64(getCurrency());
            dataoutputstream.writeInt(ai[0]);
            dataoutputstream.writeInt(ai[1]);
        }
    }

    int getWriteLength(String s, short word0)
        throws IOException
    {
        int i = 0;
        i += 2;
        if(getType() == 3)
            i += 4;
        else
        if(getType() == 5)
            i += 8;
        else
        if(getType() == 8)
        {
            byte abyte0[] = null;
            if(word0 >= 4000)
            {
                abyte0 = getString().getBytes("utf-8");
                if(abyte0.length < 32768)
                    i += 2;
                else
                    i += 4;
            } else
            {
                if(s == null)
                    abyte0 = getString().getBytes();
                else
                    abyte0 = getString().getBytes(s);
                i += 2;
            }
            i += abyte0.length;
        } else
        if(getType() == 13)
        {
            if(word0 >= 4000)
            {
                if(getBinary().length < 32768)
                    i += 2;
                else
                    i += 4;
            } else
            {
                i += 4;
            }
            i += getBinary().length;
        } else
        if(getType() == 7)
            i += 8;
        else
        if(getType() == 6)
            i += 8;
        return i;
    }

    void writeFileTo(DataOutputStream dataoutputstream, String s, short word0)
        throws IOException
    {
        if(getType() == 3)
            dataoutputstream.writeShort(0);
        else
        if(getType() == 5)
            dataoutputstream.writeShort(0);
        else
        if(getType() == 8)
        {
            File file = new File(getString());
            if(file.exists())
            {
                dataoutputstream.writeShort(13);
                int i = (int)file.length();
                if(word0 >= 4000)
                {
                    if(i < 32768)
                        dataoutputstream.writeShort(i);
                    else
                        dataoutputstream.writeInt(i | -2147483648);
                } else
                {
                    dataoutputstream.writeInt(i);
                }
                FileInputStream fileinputstream = null;
                try
                {
                    fileinputstream = new FileInputStream(file);
                    byte abyte0[] = new byte[8192];
                    int j = 0;
                    do
                    {
                        int k = i - j;
                        if(k <= 0)
                            break;
                        if(k > 8192)
                            k = 8192;
                        int l = fileinputstream.read(abyte0, 0, k);
                        if(l <= 0)
                            break;
                        dataoutputstream.write(abyte0, 0, l);
                        j += l;
                    } while(true);
                    if(j != i)
                        throw new IOException("\uD30C\uC77C \uC77D\uAE30 \uC624\uB958");
                }
                finally
                {
                    if(fileinputstream != null)
                        try
                        {
                            fileinputstream.close();
                        }
                        catch(IOException ioexception) { }
                }
            } else
            {
                dataoutputstream.writeShort(0);
            }
        } else
        if(getType() == 13)
            dataoutputstream.writeShort(0);
        else
        if(getType() == 7)
            dataoutputstream.writeShort(0);
        else
        if(getType() == 6)
            dataoutputstream.writeShort(0);
        else
            dataoutputstream.writeShort(0);
    }

    int getWriteFileLength(String s, short word0)
    {
        if(getType() == 3)
            return 2;
        if(getType() == 5)
            return 2;
        if(getType() == 8)
        {
            File file = new File(getString());
            if(file.exists())
            {
                int i = 0;
                i += 2;
                int j = (int)file.length();
                if(word0 >= 4000)
                {
                    if(j < 32768)
                        i += 2;
                    else
                        i += 4;
                } else
                {
                    i += 4;
                }
                i += j;
                return i;
            } else
            {
                return 2;
            }
        }
        if(getType() == 13)
            return 2;
        if(getType() == 7)
            return 2;
        return getType() != 6 ? 2 : 2;
    }

    public void writeTo2(OutputStream outputstream)
        throws IOException
    {
        writeTo2(new DataOutputStream(outputstream));
    }

    public void writeTo2(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeShort(getType());
        switch(getType())
        {
        case 4: // '\004'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        default:
            break;

        case 3: // '\003'
            dataoutputstream.writeInt(getInteger().intValue());
            break;

        case 5: // '\005'
            dataoutputstream.writeDouble(getDouble().doubleValue());
            break;

        case 8: // '\b'
            byte abyte0[] = getString().getBytes("utf-8");
            int i = abyte0.length;
            if(i < 32768)
                dataoutputstream.writeShort(i);
            else
                dataoutputstream.writeInt(i | -2147483648);
            if(i > 0)
                dataoutputstream.write(abyte0);
            break;

        case 13: // '\r'
            byte abyte1[] = getBinary();
            int j = 0;
            if(abyte1 != null)
                j = abyte1.length;
            if(j < 32768)
                dataoutputstream.writeShort(j);
            else
                dataoutputstream.writeInt(j | -2147483648);
            if(j > 0)
                dataoutputstream.write(abyte1);
            break;

        case 7: // '\007'
            dataoutputstream.writeDouble(DateConverter.toDouble(getDate()));
            break;

        case 6: // '\006'
            int ai[] = Currency.toInt64(getCurrency());
            dataoutputstream.writeInt(ai[0]);
            dataoutputstream.writeInt(ai[1]);
            break;
        }
    }

    int getWrite2Length()
        throws IOException
    {
        int i = 0;
        i += 2;
        switch(getType())
        {
        case 4: // '\004'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        default:
            break;

        case 3: // '\003'
            i += 4;
            break;

        case 5: // '\005'
            i += 8;
            break;

        case 8: // '\b'
            byte abyte0[] = getString().getBytes("utf-8");
            int j = abyte0.length;
            if(j < 32768)
                i += 2;
            else
                i += 4;
            i += j;
            break;

        case 13: // '\r'
            byte abyte1[] = getBinary();
            int k = 0;
            if(abyte1 != null)
                k = abyte1.length;
            if(k < 32768)
                i += 2;
            else
                i += 4;
            if(k > 0)
                i += k;
            break;

        case 7: // '\007'
            i += 8;
            break;

        case 6: // '\006'
            i += 8;
            break;
        }
        return i;
    }

    void writeFileTo2(DataOutputStream dataoutputstream)
        throws IOException
    {
        switch(getType())
        {
        case 3: // '\003'
            dataoutputstream.writeShort(0);
            break;

        case 5: // '\005'
            dataoutputstream.writeShort(0);
            break;

        case 8: // '\b'
            File file = new File(getString());
            if(file.exists())
            {
                dataoutputstream.writeShort(13);
                int i = (int)file.length();
                if(i < 32768)
                    dataoutputstream.writeShort(i);
                else
                    dataoutputstream.writeInt(i | -2147483648);
                FileInputStream fileinputstream = null;
                try
                {
                    fileinputstream = new FileInputStream(file);
                    byte abyte0[] = new byte[8192];
                    int j = 0;
                    do
                    {
                        int k = i - j;
                        if(k <= 0)
                            break;
                        if(k > 8192)
                            k = 8192;
                        int l = fileinputstream.read(abyte0, 0, k);
                        if(l <= 0)
                            break;
                        dataoutputstream.write(abyte0, 0, l);
                        j += l;
                    } while(true);
                    if(j != i)
                        throw new IOException("\uD30C\uC77C \uC77D\uAE30 \uC624\uB958");
                    break;
                }
                finally
                {
                    if(fileinputstream != null)
                        try
                        {
                            fileinputstream.close();
                        }
                        catch(IOException ioexception) { }
                }
            }
            dataoutputstream.writeShort(0);
            break;

        case 13: // '\r'
            dataoutputstream.writeShort(0);
            break;

        case 7: // '\007'
            dataoutputstream.writeShort(0);
            break;

        case 6: // '\006'
            dataoutputstream.writeShort(0);
            break;

        case 4: // '\004'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        default:
            dataoutputstream.writeShort(0);
            break;
        }
    }

    int getWriteFile2Length()
        throws IOException
    {
        int i = 0;
        switch(getType())
        {
        case 3: // '\003'
            i += 2;
            break;

        case 5: // '\005'
            i += 2;
            break;

        case 8: // '\b'
            File file = new File(getString());
            if(file.exists())
            {
                i += 2;
                int j = (int)file.length();
                if(j < 32768)
                    i += 2;
                else
                    i += 4;
                i += j;
            } else
            {
                i += 2;
            }
            break;

        case 13: // '\r'
            i += 2;
            break;

        case 7: // '\007'
            i += 2;
            break;

        case 6: // '\006'
            i += 2;
            break;

        case 4: // '\004'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        default:
            i += 2;
            break;
        }
        return i;
    }
}
