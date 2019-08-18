package io.mybatis.xml.builder;

import io.mybatis.xml.Node;
import io.mybatis.xml.node.*;
import io.mybatis.xml.util.EmptyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author liuzh
 */
public abstract class BaseXmlBuilder<T extends BaseXmlBuilder<T>> implements Node {
    private List<Node> bodies;
    private WhereXmlBuilder whereXmlBuilder;

    /**
     * element body
     *
     * @param nodes
     */
    public T body(Object... nodes) {
        if (bodies == null) {
            bodies = new ArrayList<>();
        }
        for (Object node : nodes) {
            bodies.add(convertToNode(node));
        }
        return (T) this;
    }

    public T then(Object... nodes) {
        return body(nodes);
    }

    public String body() {
        if (EmptyUtil.isNotEmpty(bodies)) {
            StringBuilder bodyBuilder = new StringBuilder();
            for (Node body : bodies) {
                bodyBuilder.append(" ");
                bodyBuilder.append(body.prefix());
                bodyBuilder.append(body.body());
                bodyBuilder.append(body.suffix());
            }
            return bodyBuilder.toString();
        }
        return EMPTY;
    }

    /**
     * &lt;where&gt;
     * &lt;/where&gt;
     *
     * @return
     */
    public WhereXmlBuilder where() {
        if (whereXmlBuilder != null) {
            throw new XmlBuilderException("where xml builder has been exists");
        }
        whereXmlBuilder = new WhereXmlBuilder();
        return whereXmlBuilder;
    }

    public IfXmlBuilder If(String test) {
        return new IfXmlBuilder().and(test);
    }

    /**
     * wrap text as TextNode
     *
     * @param texts
     * @return
     */
    public Node text(Object... texts) {
        if (texts.length == 1) {
            return convertToNode(texts[0]);
        }
        ListNode listNode = new ListNode();
        for (int i = 0; i < texts.length; i++) {
            listNode.add(convertToNode(texts[i]));
        }
        return listNode;
    }

    private Node convertToNode(Object obj) {
        if (obj instanceof String) {
            return new TextNode((String) obj);
        } else if (obj instanceof Node) {
            return (Node) obj;
        } else if (obj instanceof Supplier) {
            return convertToNode(((Supplier) obj).get());
        } else {
            throw new NodeException("Unsupported text");
        }
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
        if (value != null) {
            String str = value.toString();
            if (EmptyUtil.isNotEmpty(str)) {
                return " " + name + "=\"" + value + "\"";
            }
        }
        return EMPTY;
    }

    @Override
    public String toString() {
        StringBuffer xmlBuffer = new StringBuffer();
        xmlBuffer.append(prefix());
        xmlBuffer.append(body());
        xmlBuffer.append(suffix());
        return xmlBuffer.toString();
    }

}
