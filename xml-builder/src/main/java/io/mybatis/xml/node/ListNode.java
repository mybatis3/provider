package io.mybatis.xml.node;

import io.mybatis.xml.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzh
 */
public class ListNode extends NodeAdapter {
    private List<Node> nodes;

    public void add(Node node) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        nodes.add(node);
    }

    @Override
    public String body() {
        StringBuilder bodyBuilder = new StringBuilder();
        for (Node node : nodes) {
            bodyBuilder.append(" ")
                .append(node.prefix())
                .append(" ")
                .append(node.body())
                .append(" ")
                .append(node.suffix());
        }
        return bodyBuilder.toString();
    }
}
