package io.mybatis.xml.builder;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public class XmlBuilder extends BaseXmlBuilder<XmlBuilder> {
    private Node node;

    public SelectXmlBuilder select(String id) {
        this.node = new SelectXmlBuilder(id);
        return (SelectXmlBuilder) node;
    }

    @Override
    public String body() {
        return node.body();
    }

    @Override
    public String prefix() {
        return node.prefix();
    }

    @Override
    public String suffix() {
        return node.suffix();
    }
}
