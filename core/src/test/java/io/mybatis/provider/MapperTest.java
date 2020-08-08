/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mybatis.provider;

import io.mybatis.provider.country.Country;
import io.mybatis.provider.country.CountryMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.List;

public class MapperTest {
  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void init() {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      reader.close();
      //Create a database
      SqlSession session = null;
      try {
        session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        reader = Resources.getResourceAsReader("hsqldb.sql");
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setLogWriter(null);
        runner.runScript(reader);
        reader.close();
      } finally {
        if (session != null) {
          session.close();
        }
      }
    } catch (IOException ignore) {
      ignore.printStackTrace();
    }
  }

  public SqlSession getSqlSession() {
    return sqlSessionFactory.openSession();
  }

  @Test
  public void testSelect() {
    SqlSession session = getSqlSession();
    try {
      CountryMapper mapper = session.getMapper(CountryMapper.class);

      List<Country> countries = mapper.selectAll();
      Assert.assertEquals(5, countries.size());

      Assert.assertNotNull(mapper.selectByPrimaryKey(1L));
    } finally {
      session.rollback();
    }
  }

  @Test
  public void testDelete() {
    SqlSession session = getSqlSession();
    try {
      CountryMapper mapper = session.getMapper(CountryMapper.class);
      Assert.assertEquals(1, mapper.deleteByPrimaryKey(2));
    } finally {
      session.rollback();
    }
  }

  @Test
  public void testInsert() {
    SqlSession session = getSqlSession();
    try {
      CountryMapper mapper = session.getMapper(CountryMapper.class);
      Country country = new Country();
      country.setId(999L);
      country.setCountryname("Test");
      country.setCountrycode("TT");

      Assert.assertEquals(1, mapper.insert(country));

      country = mapper.selectByPrimaryKey(999L);
      Assert.assertNotNull(country);
    } finally {
      session.rollback();
    }
  }

}
