package io.mybatis.xml.builder;

import io.mybatis.xml.Node;
import io.mybatis.xml.node.BindNode;
import io.mybatis.xml.node.ColumnTextNode;
import io.mybatis.xml.node.TextNode;
import io.mybatis.xml.util.NodeUtil;

/**
 * @author liuzh
 */
public abstract class BaseXmlBuilder<T extends BaseXmlBuilder<T>> extends BodyThenXmlBuilder<T> {

    public SelectKeyXmlBuilder selectKey() {
        return new SelectKeyXmlBuilder();
    }

    /**
     * &lt;where&gt;
     * &lt;/where&gt;
     *
     * @return
     */
    public WhereXmlBuilder where(Object... nodes) {
        return new WhereXmlBuilder().body(nodes);
    }

    public SetXmlBuilder set(Object... nodes) {
        return new SetXmlBuilder().body(nodes);
    }

    public TrimXmlBuilder trim() {
        return new TrimXmlBuilder();
    }

    public IfXmlBuilder If(String test) {
        return new IfXmlBuilder().and(test);
    }

    public ForeachXmlBuilder foreach(String collection) {
        return new ForeachXmlBuilder(collection);
    }

    public ChooseXmlBuilder choose() {
        return new ChooseXmlBuilder();
    }

    /**
     * &lt;bind/&gt;
     *
     * @param name
     * @param value
     * @return
     */
    public Node bind(String name, String value) {
        return new BindNode(name, value);
    }

    public Node column(String columnName) {
        return new TextNode(columnName);
    }

    public Node column(String columnName, String aliasName) {
        return new ColumnTextNode(columnName, aliasName);
    }

    protected String nameEqualValue(String name, Object value) {
        return NodeUtil.nameEqualValue(name, value);
    }

}
