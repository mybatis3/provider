package io.mybatis.xml.builder;

import io.mybatis.xml.Node;
import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * @author liuzh
 */
public class SelectXmlBuilderTest {

    @Test
    public void testSelectAll() {
        System.out.println(new XmlBuilder() {
            {
                select("selectAll")
                    .resultType("Country")
                    .body("select id,countryname,countrycode from country");
            }
        }.toString());
    }

    @Test
    public void testSelectByPrimaryKey() {
        System.out.println(new XmlBuilder() {
            {
                select("selectByPrimaryKey")
                    .resultMap("BaseResultMap")//TODO 需提供
                    .parameterType("java.lang.Long")
                    .body((Supplier<String>) () -> new SQL() {
                        {
                            SELECT("id,countryname,countrycode");
                            FROM("country");
                            WHERE("id = #{id, jdbcType=INTEGER}");
                        }
                    }.toString());
            }
        });
    }

    @Test
    public void testSelectByUser() {
        System.out.println(new XmlBuilder() {
            {
                select("selectByUser")
                    .resultType("tk.mybatis.simple.model.SysUser")
                    .body(
                        bind("print", "@tk.mybatis.util.StringUtil@print(_parameter)"),
                        text("select id, user_name userName", column("user_password", "userPassword")),
                        text("from user"),
                        //tableName(User.class),
                        where(
                            If("@tk.mybatis.util.StringUtil@isNotEmpty(userName)")
                                .then("and user_name like concat('%', #{userName}, '%')"),//同 body
                            If("userEmail != ''").and("userEmail != null")
                                .then("and user_email = #{userEmail}")//同 body
                        )
                    );
            }
        });
    }

    @Test
    public void testSelectByExample() {
        System.out.println(new XmlBuilder() {
            {
                Node Example_Where_Clause = where(
                    foreach("oredCriteria")
                        .item("criteria")
                        .separator("or")
                        .then(
                            If("criteria.valid").then(
                                trim().prefix("(").suffix(")").prefixOverrides("and").then(
                                    foreach("criteria.criteria").item("criterion").then(
                                        choose().when("criterion.noValue").then("and ${criterion.condition}")
                                            .when("criterion.singleValue").then("and ${criterion.condition} #{criterion.value}")
                                            .when("criterion.betweenValue").then("and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}")
                                            .when("criterion.listValue").then(
                                            "and ${criterion.condition}",
                                            foreach("criterion.value").item("listItem").open("(").close(")").separator(",").then("#{listItem}")
                                        ).otherwise("otherwise!!!")
                                    )
                                )
                            )
                        )
                );
                select("selectByExample")
                    .parameterType("tk.mybatis.simple.model.CountryExample")
                    .resultType("tk.mybatis.simple.model.SysUser")
                    .body(
                        "select",
                        If("distinct").then("distinct"),
                        "id, countryname, countrycode",
                        "from country",
                        If("_parameter != null").then(
                            Example_Where_Clause
                        ),
                        If("orderByClause != null").then("order by ${orderByClause}")
                    );
            }
        });
    }

}