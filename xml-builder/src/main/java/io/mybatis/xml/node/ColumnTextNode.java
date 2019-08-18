package io.mybatis.xml.node;

import io.mybatis.xml.util.EmptyUtil;

/**
 * @author liuzh
 */
public class ColumnTextNode extends NodeAdapter {
    public static final String TEMPLATE = "%s AS %s";
    private String name;
    private String aliasName;

    public ColumnTextNode(String name) {
        this.name = name;
    }

    public ColumnTextNode(String columnName, String aliasName) {
        this.name = name;
        this.aliasName = aliasName;
    }

    @Override
    public String body() {
        if (EmptyUtil.isEmpty(aliasName)) {
            return name;
        } else {
            return String.format(TEMPLATE, name, aliasName);
        }
    }

}
