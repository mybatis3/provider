package io.mybatis.xml.builder;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public class TrimXmlBuilder extends BaseXmlBuilder<TrimXmlBuilder> implements Node {
    private String prefix;
    private String prefixOverrides;
    private String suffix;
    private String suffixOverrides;

    public TrimXmlBuilder prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public TrimXmlBuilder prefixOverrides(String prefixOverrides) {
        this.prefixOverrides = prefixOverrides;
        return this;
    }

    public TrimXmlBuilder suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public TrimXmlBuilder suffixOverrides(String suffixOverrides) {
        this.suffixOverrides = suffixOverrides;
        return this;
    }

    @Override
    public String prefix() {
        StringBuffer foreachBuffer = new StringBuffer();
        foreachBuffer.append("<trim");
        foreachBuffer.append(nameEqualValue("prefix", prefix));
        foreachBuffer.append(nameEqualValue("prefixOverrides", prefixOverrides));
        foreachBuffer.append(nameEqualValue("suffix", suffix));
        foreachBuffer.append(nameEqualValue("suffixOverrides", suffixOverrides));
        foreachBuffer.append(">");
        return foreachBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</trim>";
    }
}
