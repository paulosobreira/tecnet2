package util;

import java.sql.Timestamp;


/**
 * @author Sobreira
 * Criado em 08/09/2005
 */
public class DummyVOString {
    private String value1;
    private String value2;
    private String value3;
    
    public String getValue1() {
        return value1;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }
    public String getValue2() {
        return value2;
    }
    public void setValue2(String value2) {
        this.value2 = value2;
    }
    public String getValue3() {
        return value3;
    }
    public void setValue3(String value3) {
        this.value3 = value3;
    }
    /**
     * 
     * @return 
     * @author 
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DummyVOString[");
        buffer.append("value1 = ").append(value1);
        buffer.append(" value2 = ").append(value2);
        buffer.append(" value3 = ").append(value3);
        buffer.append("]");
        return buffer.toString();
    }}
