package io.mybatis.xml.node;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public class TextNode extends NodeAdapter {
    public static final TextNode EMPTY = new TextNode(Node.EMPTY);

    private String text;

    public TextNode(String text) {
        this.text = text;
    }

    @Override
    public String body() {
        return text;
    }

}
