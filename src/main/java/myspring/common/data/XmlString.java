package myspring.common.data;

import java.io.*;

public class XmlString {

    public XmlString()
    {
    }

    public static String encode(String s)
    {
        return encode(s.toCharArray());
    }

    public static String encode(char ac[])
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = ac.length;
        for(int j = 0; j < i; j++)
            if(needToChange(ac[j]))
                stringbuffer.append(encode(ac[j], false));
            else
                stringbuffer.append(ac[j]);

        return stringbuffer.toString();
    }

    public static String encode(char c)
    {
        return encode(c, true);
    }

    public static String convertToXmlString(char c)
    {
        return encode(c);
    }

    public static void encode(char ac[], Writer writer)
        throws IOException
    {
        PrintWriter printwriter = (writer instanceof PrintWriter) ? (PrintWriter)writer : new PrintWriter(writer);
        int i = ac.length;
        for(int j = 0; j < i; j++)
            if(needToChange(ac[j]))
                printwriter.write(encode(ac[j], false));
            else
                printwriter.write(ac[j]);

    }

    public static String decode(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c != '&')
            {
                stringbuffer.append(c);
                continue;
            }
            if(s.charAt(j + 1) == 'l' && s.charAt(j + 2) == 't' && s.charAt(j + 3) == ';')
            {
                stringbuffer.append('<');
                j += 3;
                continue;
            }
            if(s.charAt(j + 1) == 'g' && s.charAt(j + 2) == 't' && s.charAt(j + 3) == ';')
            {
                stringbuffer.append('>');
                j += 3;
                continue;
            }
            if(s.charAt(j + 1) == 'a' && s.charAt(j + 2) == 'm' && s.charAt(j + 3) == 'p' && s.charAt(j + 4) == ';')
            {
                stringbuffer.append('&');
                j += 4;
                continue;
            }
            if(s.charAt(j + 1) == 'q' && s.charAt(j + 2) == 'u' && s.charAt(j + 3) == 'o' && s.charAt(j + 4) == 't' && s.charAt(j + 5) == ';')
            {
                stringbuffer.append('"');
                j += 5;
                continue;
            }
            if(s.charAt(j + 1) == 'a' && s.charAt(j + 2) == 'p' && s.charAt(j + 3) == 'o' && s.charAt(j + 4) == 's' && s.charAt(j + 5) == ';')
            {
                stringbuffer.append('\'');
                j += 5;
                continue;
            }
            if(s.charAt(j + 1) == '#')
            {
                int k = s.indexOf(';', j + 1);
                if(k < 0)
                {
                    stringbuffer.append(s.charAt(j));
                    continue;
                }
                if(s.charAt(j + 2) == 'x' || s.charAt(j + 2) == 'X')
                {
                    j = k;
                    continue;
                }
                String s1 = s.substring(j + 2, k);
                if(s1.equals("9"))
                    stringbuffer.append('\t');
                else
                if(s1.equals("32"))
                    stringbuffer.append(' ');
                else
                if(s1.equals("13"))
                    stringbuffer.append('\r');
                else
                if(s1.equals("10"))
                    stringbuffer.append('\n');
                j = k;
            } else
            {
                stringbuffer.append(s.charAt(j));
            }
        }

        return stringbuffer.toString();
    }

    private static String encode(char c, boolean flag)
    {
        if(flag && !needToChange(c))
            return String.valueOf(c);
        switch(c)
        {
        case 60: // '<'
            return "&lt;";

        case 62: // '>'
            return "&gt;";

        case 38: // '&'
            return "&amp;";

        case 34: // '"'
            return "&quot;";

        case 39: // '\''
            return "&apos;";

        case 32: // ' '
            return "&#32;";

        case 9: // '\t'
            return "&#9;";

        case 13: // '\r'
            return "&#13;";

        case 10: // '\n'
            return "&#10;";
        }
        return String.valueOf(c);
    }

    private static boolean needToChange(char c)
    {
        if(c > '>')
            return false;
        else
            return c == '<' || c == '>' || c == '&' || c == '"' || c == '\'' || c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }
}
