package com.mxjlife.tool.generator.common;

/**
 * description: 字段类型
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 16:59
 */
public enum FieldDataType{

    BYTE("Byte", null),
    SHORT("Short", null),
    CHARACTER("Character", null),
    INTEGER("Integer", null),
    LONG("Long", null),
    FLOAT("Float", null),
    DOUBLE("Double", null),
    BOOLEAN("Boolean", null),
    STRING("String", null),


    BYTE_ARRAY("byte[]", null),
    OBJECT("Object", null),

    DATE("Date", "java.util.Date"),
    BIG_INTEGER("BigInteger", "java.math.BigInteger"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal");

    /**
     * <p>类型</p>
     */
    private final String type;

    /**
     * <p>包路径</p>
     */
    private final String pkg;

    FieldDataType(final String type, final String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    public String getType() {
        return type;
    }

    public String getPkg() {
        return pkg;
    }
}
