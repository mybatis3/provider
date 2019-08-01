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

package io.github.mybatis3;

import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.UUID;

/**
 * @author liuzh
 */
public abstract class Providers {
  private static volatile List<Provider> providers;

  /**
   * Obtain ProviderStrategy classes
   *
   * @return
   */
  private static List<Provider> getProviders() {
    if (providers == null) {
      synchronized (Providers.class) {
        if (providers == null) {
          providers = new ArrayList<>();
          ServiceLoader<Provider> providerStrategies = ServiceLoader.load(Provider.class);
          providerStrategies.forEach(strategy -> {
            //Initialize all instances
            ProviderFactory.init(strategy);
            providers.add(strategy);
          });
        }
      }
    }
    return providers;
  }

  /**
   * generate dynamic SQL
   *
   * @param parameterObject
   * @param context
   * @return
   */
  public static String dynamicSQL(Object parameterObject, ProviderContext context) {
    for (Provider provider : getProviders()) {
      if (provider.isSupport(parameterObject, context)) {
        return provider.dynamicSQL(parameterObject, context);
      }
    }
    throw UnsupportedProviderMethodException.throwByProviderContext(context);
  }

  /**
   * get method return value type
   *
   * @param context
   * @return
   */
  public static Class<?> getReturnType(ProviderContext context) {
    Method mapperMethod = context.getMapperMethod();
    Class<?> mapperType = context.getMapperType();
    return ProviderTypeParameterResolver.getReturnType(mapperType, mapperMethod);
  }

  /**
   * get method parameter type
   *
   * @param context
   * @return
   */
  public static Type[] getParamTypes(ProviderContext context) {
    Method mapperMethod = context.getMapperMethod();
    Class<?> mapperType = context.getMapperType();
    return ProviderTypeParameterResolver.resolveParamTypes(mapperMethod, mapperType);
  }

  /**
   * get method corresponding to interface generic information
   *
   * @param context
   * @return
   */
  public static Type[] getMapperTypes(ProviderContext context) {
    Method mapperMethod = context.getMapperMethod();
    Class<?> mapperType = context.getMapperType();
    return ProviderTypeParameterResolver.resolveMapperTypes(mapperMethod, mapperType);
  }

  /**
   * get method corresponds to the first type of interface generic information
   *
   * @param context
   * @return
   */
  public static Class getSingleMapperType(ProviderContext context) {
    Method mapperMethod = context.getMapperMethod();
    Class<?> mapperType = context.getMapperType();
    return (Class) ProviderTypeParameterResolver.resolveMapperTypes(mapperMethod, mapperType)[0];
  }

  /**
   * wrap and return the final result
   *
   * @param data SQL data
   * @return
   */
  public static String wrapResult(ProviderSql data) {
    String uuid = UUID.randomUUID().toString();
    ProviderLanguageDriver.putSqlData(uuid, data);
    return uuid;
  }

  /**
   * wrap and return the final result
   *
   * @param sqlXml  SQL XML script
   * @param context
   * @return
   */
  public static String wrapResult(String sqlXml, ProviderContext context) {
    ProviderSql data = new ProviderSql(sqlXml, context);
    return wrapResult(data);
  }
}
