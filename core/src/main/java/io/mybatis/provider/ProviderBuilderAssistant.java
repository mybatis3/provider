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

import io.mybatis.provider.defaults.ProviderProperties;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This is done by replacing the original MappedStatement with a replacement.，
 * <p>
 * After updating, it is no longer a ProviderSqlSource, so the method here will not be called repeatedly.
 *
 * @author liuzh
 */
public class ProviderBuilderAssistant extends MapperBuilderAssistant implements ProviderProperties {

  private SqlSource sqlSource;
  private KeyGenerator keyGenerator;
  private Properties properties;

  public ProviderBuilderAssistant(Configuration configuration, String resource) {
    super(configuration, resource);
  }

  @Override
  public void init(Properties properties) {
    this.properties = properties;
  }

  private static String[] delimitedStringToArray(String in) {
    if (in == null || in.trim().length() == 0) {
      return null;
    } else {
      return in.split(",");
    }
  }

  public MappedStatement addMappedStatement(
      String id,
      SqlSource sqlSource,
      StatementType statementType,
      SqlCommandType sqlCommandType,
      Integer fetchSize,
      Integer timeout,
      String parameterMap,
      Class<?> parameterType,
      String resultMap,
      Class<?> resultType,
      ResultSetType resultSetType,
      boolean flushCache,
      boolean useCache,
      boolean resultOrdered,
      KeyGenerator keyGenerator,
      String keyProperty,
      String keyColumn,
      String databaseId,
      LanguageDriver lang,
      String resultSets) {
    try {
      // get current ms id
      id = applyCurrentNamespace(id, false);
      // get existing ms
      MappedStatement ms = configuration.getMappedStatement(id);
      // record sqlSource，need to be used for the first call
      this.sqlSource = sqlSource;
      this.keyGenerator = keyGenerator;
      MetaObject metaObject = SystemMetaObject.forObject(ms);
      // replace ms
      // replace sqlSource
      metaObject.setValue("sqlSource", sqlSource);
      // replace parameterMap
      ParameterMap statementParameterMap = getStatementParameterMap(parameterMap, parameterType, id);
      if (statementParameterMap != null) {
        metaObject.setValue("parameterMap", statementParameterMap);
      }
      // replace keyGenerator
      metaObject.setValue("keyGenerator", keyGenerator);
      // replace keyProperty
      metaObject.setValue("keyProperties", delimitedStringToArray(keyProperty));
      // replace keyColumn
      metaObject.setValue("keyColumns", delimitedStringToArray(keyColumn));
      // replace others
      return ms;
    } catch (IllegalArgumentException e) {
      // When xxx!selectKey appears, an exception is entered.
      // In this case, only the parent class method needs to be called.
      return super.addMappedStatement(id, sqlSource, statementType, sqlCommandType, fetchSize, timeout, parameterMap, parameterType, resultMap, resultType, resultSetType, flushCache, useCache, resultOrdered, keyGenerator, keyProperty, keyColumn, databaseId, lang, resultSets);
    }
  }

  private ParameterMap getStatementParameterMap(
      String parameterMapName,
      Class<?> parameterTypeClass,
      String statementId) {
    parameterMapName = applyCurrentNamespace(parameterMapName, true);
    ParameterMap parameterMap = null;
    if (parameterMapName != null) {
      try {
        parameterMap = configuration.getParameterMap(parameterMapName);
      } catch (IllegalArgumentException e) {
        throw new IncompleteElementException("Could not find parameter map " + parameterMapName, e);
      }
    } else if (parameterTypeClass != null) {
      List<ParameterMapping> parameterMappings = new ArrayList<>();
      parameterMap = new ParameterMap.Builder(
          configuration,
          statementId + "-Inline",
          parameterTypeClass,
          parameterMappings).build();
    }
    return parameterMap;
  }

  public SqlSource getSqlSource() {
    return sqlSource;
  }

  public KeyGenerator getKeyGenerator() {
    return keyGenerator;
  }

}
