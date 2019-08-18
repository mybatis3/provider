package io.mybatis.xml.builder;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.StatementType;

/**
 * @author liuzh
 */
class InsertXmlBuilder extends BaseXmlBuilder<InsertXmlBuilder> {
    private String id;
    private String parameterMap;
    private String parameterType;
    private StatementType statementType;
    private String fetchSize;
    private String timeout;
    private Options.FlushCachePolicy flushCache;
    private String keyProperty;
    private String keyColumn;
    private Boolean useGeneratedKeys;
    private String databaseId;
    private String lang;

    InsertXmlBuilder(String id) {
        this.id = id;
    }

    public InsertXmlBuilder parameterMap(String parameterMap) {
        this.parameterMap = parameterMap;
        return this;
    }

    public InsertXmlBuilder parameterType(String parameterType) {
        this.parameterType = parameterType;
        return this;
    }

    public InsertXmlBuilder statementType(StatementType statementType) {
        this.statementType = statementType;
        return this;
    }

    public InsertXmlBuilder fetchSize(String fetchSize) {
        this.fetchSize = fetchSize;
        return this;
    }

    public InsertXmlBuilder timeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    public InsertXmlBuilder flushCache(Options.FlushCachePolicy flushCache) {
        this.flushCache = flushCache;
        return this;
    }

    public InsertXmlBuilder databaseId(String databaseId) {
        this.databaseId = databaseId;
        return this;
    }

    public InsertXmlBuilder lang(String lang) {
        this.lang = lang;
        return this;
    }

    public InsertXmlBuilder keyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
        return this;
    }

    public InsertXmlBuilder keyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
        return this;
    }

    public InsertXmlBuilder useGeneratedKeys(Boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
        return this;
    }

    @Override
    public String prefix() {
        StringBuffer insertBuffer = new StringBuffer();
        insertBuffer.append("<insert");
        insertBuffer.append(nameEqualValue("id", id));
        insertBuffer.append(nameEqualValue("parameterMap", parameterMap));
        insertBuffer.append(nameEqualValue("parameterType", parameterType));
        insertBuffer.append(nameEqualValue("statementType", statementType));
        insertBuffer.append(nameEqualValue("fetchSize", fetchSize));
        insertBuffer.append(nameEqualValue("timeout", timeout));
        insertBuffer.append(nameEqualValue("flushCache", flushCache));
        insertBuffer.append(nameEqualValue("databaseId", databaseId));
        insertBuffer.append(nameEqualValue("lang", lang));
        insertBuffer.append(nameEqualValue("keyColumn", keyColumn));
        insertBuffer.append(nameEqualValue("keyProperty", keyProperty));
        insertBuffer.append(nameEqualValue("useGeneratedKeys", useGeneratedKeys));
        insertBuffer.append(">");
        return insertBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</insert>";
    }

}
