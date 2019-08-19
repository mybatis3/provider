package io.mybatis.xml.builder;

import io.mybatis.xml.Node;
import io.mybatis.xml.util.EmptyUtil;
import io.mybatis.xml.util.NodeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzh
 */
public class ChooseXmlBuilder implements Node {
    private List<WhenXmlBuilder> whens;
    private OtherwiseNode otherwise;

    @Override
    public String prefix() {
        return "<choose>";
    }

    @Override
    public String body() {
        if (EmptyUtil.isEmpty(whens)) {
            throw new XmlBuilderException("choose must contains when element");
        }
        StringBuffer chooseBuffer = new StringBuffer();
        for (WhenXmlBuilder when : whens) {
            chooseBuffer.append(" ");
            chooseBuffer.append(when.prefix());
            chooseBuffer.append(" ");
            chooseBuffer.append(when.body());
            chooseBuffer.append(" ");
            chooseBuffer.append(when.suffix());
        }
        if (otherwise != null) {
            chooseBuffer.append(" ");
            chooseBuffer.append(otherwise.prefix());
            chooseBuffer.append(" ");
            chooseBuffer.append(otherwise.body());
            chooseBuffer.append(" ");
            chooseBuffer.append(otherwise.suffix());
        }
        return chooseBuffer.toString();
    }

    @Override
    public String suffix() {
        return "</choose>";
    }

    private void addWhen(WhenXmlBuilder when) {
        if (whens == null) {
            whens = new ArrayList<>();
        }
        whens.add(when);
    }

    public WhenXmlBuilder when(String test) {
        return new WhenXmlBuilder().and(test);
    }

    public Node otherwise(Object... nodes) {
        otherwise = new OtherwiseNode(NodeUtil.nodes(nodes));
        return this;
    }

    enum AndOr {
        AND(" and "), OR(" or ");
        private String value;

        AndOr(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    class OtherwiseNode implements Node {
        private Node node;

        public OtherwiseNode(Node node) {
            this.node = node;
        }

        @Override
        public String prefix() {
            return "<otherwise>";
        }

        @Override
        public String body() {
            return node.body();
        }

        @Override
        public String suffix() {
            return "</otherwise>";
        }
    }

    class WhenXmlBuilder extends BodyXmlBuilder<WhenXmlBuilder> {
        List<TestNode> tests;

        public WhenXmlBuilder and(String test) {
            if (tests == null) {
                tests = new ArrayList<>();
            }
            tests.add(new TestNode(test, AndOr.AND));
            return this;
        }

        public WhenXmlBuilder or(String test) {
            if (tests == null) {
                tests = new ArrayList<>();
            }
            tests.add(new TestNode(test, AndOr.OR));
            return this;
        }

        public ChooseXmlBuilder then(Object... nodes) {
            body(nodes);
            ChooseXmlBuilder.this.addWhen(this);
            return ChooseXmlBuilder.this;
        }

        @Override
        public String prefix() {
            StringBuilder ifBuilder = new StringBuilder();
            ifBuilder.append("<when test=\"");
            for (int i = 0; i < tests.size(); i++) {
                TestNode t = tests.get(i);
                if (i > 0) {
                    ifBuilder.append(t.andOr);
                }
                ifBuilder.append(t.test);
            }
            ifBuilder.append("\">");
            return ifBuilder.toString();
        }

        @Override
        public String suffix() {
            return "</when>";
        }

    }

    class TestNode {
        String test;
        AndOr andOr;

        public TestNode(String test, AndOr andOr) {
            this.test = test;
            this.andOr = andOr;
        }
    }
}
