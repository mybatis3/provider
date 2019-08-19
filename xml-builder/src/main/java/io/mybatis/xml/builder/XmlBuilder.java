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

    public InsertXmlBuilder insert(String id) {
        this.node = new InsertXmlBuilder(id);
        return (InsertXmlBuilder) node;
    }

    public UpdateXmlBuilder update(String id) {
        this.node = new UpdateXmlBuilder(id);
        return (UpdateXmlBuilder) node;
    }

    public DeleteXmlBuilder delete(String id) {
        this.node = new DeleteXmlBuilder(id);
        return (DeleteXmlBuilder) node;
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
