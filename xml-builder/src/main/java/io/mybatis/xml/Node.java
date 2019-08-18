package io.mybatis.xml;

/**
 * @author liuzh
 */
public interface Node {
    String EMPTY = "";

    /**
     * node prefix text
     *
     * @return
     */
    String prefix();

    /**
     * node body text
     *
     * @return
     */
    String body();

    /**
     * node suffix text
     *
     * @return
     */
    String suffix();
}
