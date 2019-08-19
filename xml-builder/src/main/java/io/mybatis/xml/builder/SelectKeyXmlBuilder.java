package io.mybatis.xml.builder;

import io.mybatis.xml.Node;
import org.apache.ibatis.mapping.StatementType;

/**
 * @author liuzh
 */
public class SelectKeyXmlBuilder extends BaseXmlBuilder<SelectKeyXmlBuilder> implements Node {
    private String resultType;
    private StatementType statementType;
    private String keyProperty;
    private String keyColumn;
    private Order order;
    private String databaseId;

    public SelectKeyXmlBuilder resultType(String resultType) {
        this.resultType = resultType;
        return this;
    }

    public SelectKeyXmlBuilder resultType(Class resultType) {
        this.resultType = resultType != null ? resultType.getName() : null;
        return this;
    }

    public SelectKeyXmlBuilder statementType(StatementType statementType) {
        this.statementType = statementType;
        return this;
    }

    public SelectKeyXmlBuilder keyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
        return this;
    }

    public SelectKeyXmlBuilder keyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
        return this;
    }

    public SelectKeyXmlBuilder order(Order order) {
        this.order = order;
        return this;
    }

    public SelectKeyXmlBuilder databaseId(String databaseId) {
        this.databaseId = databaseId;
        return this;
    }

    @Override
    public String prefix() {
        StringBuffer foreachBuffer = new StringBuffer();
        foreachBuffer.append("<selectKey");
        foreachBuffer.append(nameEqualValue("resultType", resultType));
        foreachBuffer.append(nameEqualValue("statementType", statementType));
        foreachBuffer.append(nameEqualValue("keyProperty", keyProperty));
        foreachBuffer.append(nameEqualValue("keyColumn", keyColumn));
        foreachBuffer.append(nameEqualValue("order", order));
        foreachBuffer.append(nameEqualValue("databaseId", databaseId));
        foreachBuffer.append(">");
        return foreachBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</selectKey>";
    }

    enum Order {
        BEFORE, AFTER
    }
}
