package io.mybatis.xml.builder;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.StatementType;

/**
 * @author liuzh
 */
public class DeleteXmlBuilder extends BaseXmlBuilder<DeleteXmlBuilder> {
    private String id;
    private String parameterMap;
    private String parameterType;
    private StatementType statementType;
    private String timeout;
    private Options.FlushCachePolicy flushCache;
    private String databaseId;
    private String lang;

    DeleteXmlBuilder(String id) {
        this.id = id;
    }

    public DeleteXmlBuilder parameterMap(String parameterMap) {
        this.parameterMap = parameterMap;
        return this;
    }

    public DeleteXmlBuilder parameterType(String parameterType) {
        this.parameterType = parameterType;
        return this;
    }

    public DeleteXmlBuilder statementType(StatementType statementType) {
        this.statementType = statementType;
        return this;
    }

    public DeleteXmlBuilder timeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    public DeleteXmlBuilder flushCache(Options.FlushCachePolicy flushCache) {
        this.flushCache = flushCache;
        return this;
    }

    public DeleteXmlBuilder databaseId(String databaseId) {
        this.databaseId = databaseId;
        return this;
    }

    public DeleteXmlBuilder lang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public String prefix() {
        StringBuffer deleteBuffer = new StringBuffer();
        deleteBuffer.append("<delete");
        deleteBuffer.append(nameEqualValue("id", id));
        deleteBuffer.append(nameEqualValue("parameterMap", parameterMap));
        deleteBuffer.append(nameEqualValue("parameterType", parameterType));
        deleteBuffer.append(nameEqualValue("statementType", statementType));
        deleteBuffer.append(nameEqualValue("timeout", timeout));
        deleteBuffer.append(nameEqualValue("flushCache", flushCache));
        deleteBuffer.append(nameEqualValue("databaseId", databaseId));
        deleteBuffer.append(nameEqualValue("lang", lang));
        deleteBuffer.append(">");
        return deleteBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</delete>";
    }
}
