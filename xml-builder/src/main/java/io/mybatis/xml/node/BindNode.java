package io.mybatis.xml.node;

/**
 * @author liuzh
 */
public class BindNode extends NodeAdapter {
    public static final String TEMPLATE = "<bind name=\"%s\" value=\"%s\"/>";
    private String name;
    private String value;

    public BindNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String body() {
        return String.format(TEMPLATE, name, value);
    }

}
