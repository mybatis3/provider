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

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.ScheduledCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuzh
 */
public class ProviderLanguageDriver implements LanguageDriver {
  public static final Log log = LogFactory.getLog(ProviderLanguageDriver.class);

  private ProviderBuilderAssistant builderAssistant;

  private static final Cache CACHE_SQLSOURCE = new ScheduledCache(new PerpetualCache("FULL_XML_LANGUAGEDRIVER_SQLSOURCE")) {
    {
      setClearInterval(60 * 1000);
    }
  };

  private static final Map<String, ProviderSql> PROVIDER_SQL = new ConcurrentHashMap<>(16);

  /**
   * record the parameter information to be passed
   *
   * @param uuid
   * @param sql
   */
  public static void putSqlData(String uuid, ProviderSql sql) {
    PROVIDER_SQL.put(uuid, sql);
  }

  @Override
  public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
    return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
  }

  @Override
  public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
    XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
    return builder.parseScriptNode();
  }

  @Override
  public SqlSource createSqlSource(Configuration configuration, String sqlUUID, Class<?> parameterType) {
    //temporarily pass data through PROVIDER_SQL
    ProviderSql providerSql = PROVIDER_SQL.get(sqlUUID);
    //remove
    PROVIDER_SQL.remove(sqlUUID);
    //XML script
    String sqlXml = providerSql.getSqlXml();
    //interface name
    ProviderContext context = providerSql.getContext();
    String mapperClass = context.getMapperType().getName();
    //interface name as namespace and resources
    builderAssistant = new ProviderBuilderAssistant(configuration, mapperClass.replaceAll("\\.", "/"));
    //initialization operation
    ProviderFactory.init(builderAssistant);
    builderAssistant.setCurrentNamespace(mapperClass);
    //parse XML
    XPathParser parser = new XPathParser(sqlXml, false, configuration.getVariables(), new XMLMapperEntityResolver());
    XNode node = parser.evalNode("select|insert|update|delete");
    //Get the method id to avoid repeated execution
    String id = node.getStringAttribute("id");
    id = builderAssistant.applyCurrentNamespace(id, false);
    //Avoid repetitive execution when high concurrency in a short time (when ms is processed, future methods will not pass here)
    if (CACHE_SQLSOURCE.getObject(id) == null) {
      synchronized (CACHE_SQLSOURCE) {
        if (CACHE_SQLSOURCE.getObject(id) == null) {
          log.debug("init " + id);
          XMLStatementBuilder statementParser =
              new XMLStatementBuilder(configuration, builderAssistant, node, configuration.getDatabaseId());
          statementParser.parseStatementNode();
          SqlSource sqlSource = builderAssistant.getSqlSource();
          CACHE_SQLSOURCE.putObject(id, sqlSource);
        }
      }
    }
    return (SqlSource) CACHE_SQLSOURCE.getObject(id);
  }

}