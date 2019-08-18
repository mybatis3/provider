package io.mybatis.xml.builder;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public class WhereXmlBuilder extends BaseXmlBuilder<WhereXmlBuilder> implements Node {

    @Override
    public String prefix() {
        return "<where>";
    }

    @Override
    public String suffix() {
        return "</where>";
    }
}
