package io.mybatis.xml.builder;

import io.mybatis.xml.Node;
import io.mybatis.xml.util.EmptyUtil;

import java.util.ArrayList;
import java.util.List;

import static io.mybatis.xml.util.NodeUtil.convertToNode;
import static io.mybatis.xml.util.NodeUtil.nodes;

/**
 * @author liuzh
 */
public abstract class BodyXmlBuilder<T extends BodyXmlBuilder<T>> implements Node {
    private List<Node> bodies;

    /**
     * wrap text as TextNode
     *
     * @param texts
     * @return
     */
    public Node text(Object... texts) {
        return nodes(texts);
    }

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

    @Override
    public String body() {
        if (EmptyUtil.isNotEmpty(bodies)) {
            StringBuilder bodyBuilder = new StringBuilder();
            for (Node body : bodies) {
                bodyBuilder.append(" ");
                bodyBuilder.append(body.prefix());
                bodyBuilder.append(" ");
                bodyBuilder.append(body.body());
                bodyBuilder.append(" ");
                bodyBuilder.append(body.suffix());
            }
            return bodyBuilder.toString();
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
