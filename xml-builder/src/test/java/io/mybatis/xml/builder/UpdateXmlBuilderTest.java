package io.mybatis.xml.builder;

import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * @author liuzh
 */
public class UpdateXmlBuilderTest {

    @Test
    public void testUpdateByPrimaryKey() {
        System.out.println(new XmlBuilder() {
            {
                update("updateByPrimaryKey").parameterType("tk.mybatis.simple.model.Country")
                    .then((Supplier<String>) () -> new SQL() {
                        {
                            UPDATE("country");
                            SET("countryname = #{countryname,jdbcType=VARCHAR}");
                            SET("countrycode = #{countrycode,jdbcType=VARCHAR}");
                            WHERE("id = #{id,jdbcType=INTEGER}");
                        }
                    }.toString());
            }
        });
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        System.out.println(new XmlBuilder() {
            {
                update("updateByPrimaryKeySelective").parameterType("tk.mybatis.simple.model.Country")
                    .then(
                        "update contry",
                        set(
                            If("countryname != null").then("countryname = #{countryname,jdbcType=VARCHAR},"),
                            If("countrycode != null").then("countrycode = #{countrycode,jdbcType=VARCHAR},")
                        ),
                        "where id = #{id,jdbcType=INTEGER}"
                    );
            }
        });
    }

}