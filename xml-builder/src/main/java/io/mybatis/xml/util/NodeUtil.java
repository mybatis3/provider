package io.mybatis.xml.util;

import io.mybatis.xml.Node;
import io.mybatis.xml.node.ListNode;
import io.mybatis.xml.node.NodeException;
import io.mybatis.xml.node.TextNode;

import java.util.function.Supplier;

/**
 * @author liuzh
 */
public abstract class NodeUtil implements Node {

    /**
     * wrap text as TextNode
     *
     * @param texts
     * @return
     */
    public static Node nodes(Object... texts) {
        if (texts.length == 1) {
            return convertToNode(texts[0]);
        }
        ListNode listNode = new ListNode();
        for (int i = 0; i < texts.length; i++) {
            listNode.add(convertToNode(texts[i]));
        }
        return listNode;
    }

    public static Node convertToNode(Object obj) {
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
     * if value is not empty, return "name = \"value\""
     *
     * @param name
     * @param value
     * @return
     */
    public static String nameEqualValue(String name, Object value) {
        if (value != null) {
            String str = value.toString();
            if (EmptyUtil.isNotEmpty(str)) {
                return " " + name + "=\"" + value + "\"";
            }
        }
        return EMPTY;
    }
}
