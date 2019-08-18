package io.mybatis.xml.builder;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;

/**
 * @author liuzh
 */
class SelectXmlBuilder extends BaseXmlBuilder<SelectXmlBuilder> {
    private String id;
    private String parameterMap;
    private String parameterType;
    private String resultMap;
    private String resultType;
    private ResultSetType resultSetType;
    private StatementType statementType;
    private String fetchSize;
    private String timeout;
    private Options.FlushCachePolicy flushCache;
    private Boolean useCache;
    private String databaseId;
    private String lang;
    private Boolean resultOrdered;
    private String resultSets;

    SelectXmlBuilder(String id) {
        this.id = id;
    }

    public SelectXmlBuilder parameterMap(String parameterMap) {
        this.parameterMap = parameterMap;
        return this;
    }

    public SelectXmlBuilder parameterType(String parameterType) {
        this.parameterType = parameterType;
        return this;
    }

    public SelectXmlBuilder resultMap(String resultMap) {
        this.resultMap = resultMap;
        return this;
    }

    public SelectXmlBuilder resultType(String resultType) {
        this.resultType = resultType;
        return this;
    }

    public SelectXmlBuilder resultSetType(ResultSetType resultSetType) {
        this.resultSetType = resultSetType;
        return this;
    }

    public SelectXmlBuilder statementType(StatementType statementType) {
        this.statementType = statementType;
        return this;
    }

    public SelectXmlBuilder fetchSize(String fetchSize) {
        this.fetchSize = fetchSize;
        return this;
    }

    public SelectXmlBuilder timeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    public SelectXmlBuilder flushCache(Options.FlushCachePolicy flushCache) {
        this.flushCache = flushCache;
        return this;
    }

    public SelectXmlBuilder useCache(Boolean useCache) {
        this.useCache = useCache;
        return this;
    }

    public SelectXmlBuilder databaseId(String databaseId) {
        this.databaseId = databaseId;
        return this;
    }

    public SelectXmlBuilder lang(String lang) {
        this.lang = lang;
        return this;
    }

    public SelectXmlBuilder resultOrdered(Boolean resultOrdered) {
        this.resultOrdered = resultOrdered;
        return this;
    }

    public SelectXmlBuilder resultSets(String resultSets) {
        this.resultSets = resultSets;
        return this;
    }

    @Override
    public String prefix() {
        StringBuffer selectBuffer = new StringBuffer();
        selectBuffer.append("<select");
        selectBuffer.append(nameEqualValue("id", id));
        selectBuffer.append(nameEqualValue("parameterMap", parameterMap));
        selectBuffer.append(nameEqualValue("parameterType", parameterType));
        selectBuffer.append(nameEqualValue("resultMap", resultMap));
        selectBuffer.append(nameEqualValue("resultType", resultType));
        selectBuffer.append(nameEqualValue("resultSetType", resultSetType));
        selectBuffer.append(nameEqualValue("statementType", statementType));
        selectBuffer.append(nameEqualValue("fetchSize", fetchSize));
        selectBuffer.append(nameEqualValue("timeout", timeout));
        selectBuffer.append(nameEqualValue("flushCache", flushCache));
        selectBuffer.append(nameEqualValue("useCache", useCache));
        selectBuffer.append(nameEqualValue("databaseId", databaseId));
        selectBuffer.append(nameEqualValue("lang", lang));
        selectBuffer.append(nameEqualValue("resultOrdered", resultOrdered));
        selectBuffer.append(nameEqualValue("resultSets", resultSets));
        selectBuffer.append(">");
        return selectBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</select>";
    }

}
