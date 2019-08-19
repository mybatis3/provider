package io.mybatis.xml.builder;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public abstract class BodyThenXmlBuilder<T extends BodyThenXmlBuilder<T>> extends BodyXmlBuilder<T> {

    public Node then(Object... nodes) {
        return body(nodes);
    }

}
