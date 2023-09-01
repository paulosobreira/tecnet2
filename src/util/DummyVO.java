package util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Sobreira
 * Criado em 08/09/2005
 */
public class DummyVO {
    private int value1;
    private String value2;
    private Timestamp value3;
    private List dummyListString = new ArrayList();
    private List dummyListInteger = new ArrayList();
    

    public List getDummyListInteger() {
        return dummyListInteger;
    }

    public List getDummyListString() {
        return dummyListString;
    }

    public void setDummyListInteger(List dummyListInteger) {
        this.dummyListInteger = dummyListInteger;
    }

    public void setDummyListString(List dummyListString) {
        this.dummyListString = dummyListString;
    }

    /**
     * @param value1
     * @param value2
     * @param value3
     */
    public DummyVO(int value1, String value2, Timestamp value3) {
        super();
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    /**
     *
     */
    public DummyVO() {
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public Timestamp getValue3() {
        return value3;
    }

    public void setValue3(Timestamp value3) {
        this.value3 = value3;
    }

    /**
     *
     * @return
     * @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DummyVO[");
        buffer.append("value1 = ").append(value1);
        buffer.append(" value2 = ").append(value2);
        buffer.append(" value3 = ").append(value3);
        buffer.append("]");

        return buffer.toString();
    }
}
