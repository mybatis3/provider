package io.mybatis.xml.builder;

import io.mybatis.xml.Node;

/**
 * @author liuzh
 */
public class SetXmlBuilder extends BaseXmlBuilder<SetXmlBuilder> implements Node {

    @Override
    public String prefix() {
        return "<set>";
    }

    @Override
    public String suffix() {
        return "</set>";
    }
}
