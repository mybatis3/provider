package io.mybatis.xml.builder;

import io.mybatis.xml.Node;
import io.mybatis.xml.node.BindNode;
import io.mybatis.xml.node.ColumnTextNode;
import io.mybatis.xml.util.NodeUtil;

/**
 * @author liuzh
 */
public abstract class BaseXmlBuilder<T extends BaseXmlBuilder<T>> extends BodyThenXmlBuilder<T> {

    /**
     * &lt;selectKey&gt;
     * &lt;/selectKey&gt;
     *
     * @return
     */
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

    /**
     * &lt;set&gt;
     * &lt;/set&gt;
     *
     * @return
     */
    public SetXmlBuilder set(Object... nodes) {
        return new SetXmlBuilder().body(nodes);
    }

    /**
     * &lt;trim&gt;
     * &lt;/trim&gt;
     *
     * @return
     */
    public TrimXmlBuilder trim() {
        return new TrimXmlBuilder();
    }

    /**
     * &lt;if&gt;
     * &lt;/if&gt;
     *
     * @return
     */
    public IfXmlBuilder If(String test) {
        return new IfXmlBuilder().and(test);
    }

    /**
     * &lt;foreach&gt;
     * &lt;/foreach&gt;
     *
     * @return
     */
    public ForeachXmlBuilder foreach(String collection) {
        return new ForeachXmlBuilder(collection);
    }

    /**
     * &lt;choose&gt;
     * &lt;/choose&gt;
     *
     * @return
     */
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

    /**
     * column AS aliasName
     *
     * @return
     */
    public Node column(String columnName, String aliasName) {
        return new ColumnTextNode(columnName, aliasName);
    }

    /**
     * if value is not empty, return "name = \"value\""
     *
     * @param name
     * @param value
     * @return
     */
    protected String nameEqualValue(String name, Object value) {
        return NodeUtil.nameEqualValue(name, value);
    }

}
