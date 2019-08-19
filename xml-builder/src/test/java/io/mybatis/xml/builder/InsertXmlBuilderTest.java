package io.mybatis.xml.builder;

import org.junit.Test;

/**
 * @author liuzh
 */
public class InsertXmlBuilderTest {

    @Test
    public void testInsert() {
        System.out.println(new XmlBuilder() {
            {
                insert("insert").parameterType("tk.mybatis.simple.model.Country").then(
                    selectKey().keyProperty("id").order(SelectKeyXmlBuilder.Order.BEFORE)
                        .resultType("java.lang.Integer").then("SELECT LAST_INSERT_ID()"),
                    "insert into country (id, countryname, countrycode)",
                    "values (#{id,jdbcType=INTEGER}, #{countryname,jdbcType=VARCHAR},#{countrycode,jdbcType=VARCHAR} )"
                );
            }
        });
    }

    @Test
    public void testInsertSelective() {
        System.out.println(new XmlBuilder() {
            {
                insert("insert").parameterType("tk.mybatis.simple.model.Country").then(
                    selectKey().keyProperty("id").order(SelectKeyXmlBuilder.Order.BEFORE)
                        .resultType("java.lang.Integer").then("SELECT LAST_INSERT_ID()"),
                    "insert into country",
                    trim().prefix("(").suffix(")").suffixOverrides(",").then(
                        "id,",
                        If("countryname != null").then("countryname,"),
                        If("countrycode != null").then("countrycode,")
                    ),
                    trim().prefix("values (").suffix(")").suffixOverrides(",").then(
                        "id,",
                        If("countryname != null").then("#{countryname,jdbcType=VARCHAR},"),
                        If("countrycode != null").then("#{countrycode,jdbcType=VARCHAR},")
                    )
                );
            }
        });
    }

}
