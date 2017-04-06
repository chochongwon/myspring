package myspring.common.data;

import java.io.*;

public class Variable implements Serializable {
    private String id;
    private Variant value;

    public Variable()
    {
    }

    public Variable(String s, Variant variant)
    {
        id = s;
        value = variant;
    }

    public String getId()
    {
        return id;
    }

    public String getID()
    {
        return getId();
    }

    public void setId(String s)
    {
        id = s;
    }

    public void setID(String s)
    {
        setId(s);
    }

    public Variant getValue()
    {
        return value;
    }

    public void setValue(Variant variant)
    {
        value = variant;
    }

    public String toString()
    {
        return toString(true);
    }

    public String toString(boolean flag)
    {
        String s = flag ? "Variable" : "";
        if(getValue() == null)
            return s + "[id=" + getId() + ", value=null]";
        else
            return s + "[id=" + getId() + ", type=" + getValue().getTypeName() + ", value=" + getValue().asString() + "]";
    }

    public void dump()
    {
        System.out.print("Variable ID=[" + id + "], ");
        System.out.print("Type=[" + getValue().getTypeName() + "], ");
        System.out.println("Value=[" + getValue().toString() + "]");
    }

    public void printVariable()
        throws IOException
    {
        printVariable(true);
    }

    public void printVariable(boolean flag)
        throws IOException
    {
        printVariable(this, flag);
    }

    public static void printVariable(Variable variable, boolean flag)
        throws IOException
    {
        printVariable(((Writer) (new BufferedWriter(new OutputStreamWriter(System.out)))), variable, flag, 0);
    }

    public static void printVariable(OutputStream outputstream, Variable variable, boolean flag, int i)
        throws IOException
    {
        outputstream.write((variable + System.getProperty("line.separator")).getBytes());
    }

    public static void printVariable(Writer writer, Variable variable, boolean flag, int i)
        throws IOException
    {
        String s = "";
        if(i > 0)
        {
            for(int j = 0; j < i; j++)
                s = s + "\t";

        }
        if(variable == null)
        {
            writer.write(s + "@@@ Variable : null\n");
            writer.flush();
            return;
        }
        String s1 = variable.getId();
        Variant variant = variable.getValue();
        if(s1 != null && variant != null)
        {
            writer.write(s + "@@@ Variable : ID = \"" + s1 + "\", Type = <" + variant.getTypeName() + ">");
            if(flag)
                writer.write(", Value = {" + variant.asString() + "}");
            writer.write("\n");
        } else
        {
            writer.write(s + "@@@ Variable : null");
        }
        writer.flush();
    }
}
