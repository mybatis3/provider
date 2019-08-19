package io.mybatis.xml.builder;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.StatementType;

/**
 * @author liuzh
 */
class UpdateXmlBuilder extends BaseXmlBuilder<UpdateXmlBuilder> {
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

    UpdateXmlBuilder(String id) {
        this.id = id;
    }

    public UpdateXmlBuilder parameterMap(String parameterMap) {
        this.parameterMap = parameterMap;
        return this;
    }

    public UpdateXmlBuilder parameterType(String parameterType) {
        this.parameterType = parameterType;
        return this;
    }

    public UpdateXmlBuilder parameterType(Class<?> parameterType) {
        this.parameterType = parameterType != null ? parameterType.getName() : null;
        return this;
    }

    public UpdateXmlBuilder statementType(StatementType statementType) {
        this.statementType = statementType;
        return this;
    }

    public UpdateXmlBuilder fetchSize(String fetchSize) {
        this.fetchSize = fetchSize;
        return this;
    }

    public UpdateXmlBuilder timeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    public UpdateXmlBuilder flushCache(Options.FlushCachePolicy flushCache) {
        this.flushCache = flushCache;
        return this;
    }

    public UpdateXmlBuilder databaseId(String databaseId) {
        this.databaseId = databaseId;
        return this;
    }

    public UpdateXmlBuilder lang(String lang) {
        this.lang = lang;
        return this;
    }

    public UpdateXmlBuilder keyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
        return this;
    }

    public UpdateXmlBuilder keyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
        return this;
    }

    public UpdateXmlBuilder useGeneratedKeys(Boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
        return this;
    }

    @Override
    public String prefix() {
        StringBuffer updateBuffer = new StringBuffer();
        updateBuffer.append("<update");
        updateBuffer.append(nameEqualValue("id", id));
        updateBuffer.append(nameEqualValue("parameterMap", parameterMap));
        updateBuffer.append(nameEqualValue("parameterType", parameterType));
        updateBuffer.append(nameEqualValue("statementType", statementType));
        updateBuffer.append(nameEqualValue("fetchSize", fetchSize));
        updateBuffer.append(nameEqualValue("timeout", timeout));
        updateBuffer.append(nameEqualValue("flushCache", flushCache));
        updateBuffer.append(nameEqualValue("databaseId", databaseId));
        updateBuffer.append(nameEqualValue("lang", lang));
        updateBuffer.append(nameEqualValue("keyColumn", keyColumn));
        updateBuffer.append(nameEqualValue("keyProperty", keyProperty));
        updateBuffer.append(nameEqualValue("useGeneratedKeys", useGeneratedKeys));
        updateBuffer.append(">");
        return updateBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</update>";
    }

}
