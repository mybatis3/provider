package io.mybatis.xml.builder;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public class ForeachXmlBuilder extends BaseXmlBuilder<ForeachXmlBuilder> implements Node {
    private String collection;
    private String item;
    private String index;
    private String open;
    private String close;
    private String separator;

    public ForeachXmlBuilder(String collection) {
        this.collection = collection;
    }

    public ForeachXmlBuilder item(String item) {
        this.item = item;
        return this;
    }

    public ForeachXmlBuilder index(String index) {
        this.index = index;
        return this;
    }

    public ForeachXmlBuilder open(String open) {
        this.open = open;
        return this;
    }

    public ForeachXmlBuilder close(String close) {
        this.close = close;
        return this;
    }

    public ForeachXmlBuilder separator(String separator) {
        this.separator = separator;
        return this;
    }


    @Override
    public String prefix() {
        StringBuffer foreachBuffer = new StringBuffer();
        foreachBuffer.append("<foreach");
        foreachBuffer.append(nameEqualValue("collection", collection));
        foreachBuffer.append(nameEqualValue("item", item));
        foreachBuffer.append(nameEqualValue("index", index));
        foreachBuffer.append(nameEqualValue("open", open));
        foreachBuffer.append(nameEqualValue("close", close));
        foreachBuffer.append(nameEqualValue("separator", separator));
        foreachBuffer.append(">");
        return foreachBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</foreach>";
    }
}
