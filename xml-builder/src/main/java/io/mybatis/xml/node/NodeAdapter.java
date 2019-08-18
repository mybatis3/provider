package io.mybatis.xml.node;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public class NodeAdapter implements Node {
    @Override
    public String prefix() {
        return EMPTY;
    }

    @Override
    public String body() {
        return EMPTY;
    }

    @Override
    public String suffix() {
        return EMPTY;
    }
}
