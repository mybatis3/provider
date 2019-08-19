package io.mybatis.xml.builder;

import org.junit.Test;

/**
 * @author liuzh
 */
public class DeleteXmlBuilderTest {

    @Test
    public void testDelete() {
        System.out.println(new XmlBuilder() {
            {
                delete("deleteByPrimaryKey").parameterType("java.lang.Integer").then(
                    "delete from country",
                    "where id = #{id,jdbcType=INTEGER}"
                );
            }
        });
    }

}