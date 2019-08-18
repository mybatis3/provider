package io.mybatis.xml.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzh
 */
public class IfXmlBuilder extends BaseXmlBuilder<IfXmlBuilder> {
    List<TestNode> tests;

    public IfXmlBuilder and(String test) {
        if (tests == null) {
            tests = new ArrayList<>();
        }
        tests.add(new TestNode(test, AndOr.AND));
        return this;
    }

    public IfXmlBuilder or(String test) {
        if (tests == null) {
            tests = new ArrayList<>();
        }
        tests.add(new TestNode(test, AndOr.OR));
        return this;
    }

    @Override
    public String prefix() {
        StringBuilder ifBuilder = new StringBuilder();
        ifBuilder.append("<if test=\"");
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
        return "</if>";
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

    class TestNode {
        String test;
        AndOr andOr;

        public TestNode(String test, AndOr andOr) {
            this.test = test;
            this.andOr = andOr;
        }
    }
}
