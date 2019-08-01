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

package io.github.mybatis3.defaults;

import io.github.mybatis3.Provider;
import io.github.mybatis3.ProviderFactory;
import io.github.mybatis3.Providers;
import io.github.mybatis3.UnsupportedProviderMethodException;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Properties;

/**
 * The default implementation, based on the interface name,
 * <p>
 * find the interface name defined by the interface name + mapperMethod in the Provider implementation
 *
 * @author liuzh
 */
public class DefaultProvider implements Provider, ProviderProperties {
  private String suffix = "Provider";
  private Properties properties;
  private Configuration configuration;

  @Override
  public void init(Properties properties) {
    this.properties = properties;
    this.configuration = new Configuration();
    if (properties.containsKey("DefaultProvider.configuration.useActualParamName")) {
      this.configuration.setUseActualParamName(
          Boolean.parseBoolean(this.properties.getProperty("DefaultProvider.configuration.useActualParamName", "true")));
    }
    if (properties.containsKey("DefaultProvider.suffix")) {
      this.suffix = properties.getProperty("DefaultProvider.suffix");
    }
  }

  /**
   * Whether to support the corresponding method, support exists when there is a class that defines interface + {@link #suffix}
   *
   * @param parameterObject
   * @param context
   * @return
   */
  @Override
  public boolean isSupport(Object parameterObject, ProviderContext context) {
    Method mapperMethod = context.getMapperMethod();
    String providerClassStr = mapperMethod.getDeclaringClass().getName() + suffix;
    try {
      Resources.classForName(providerClassStr);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  /**
   * Create SQL, the actual return is UUID
   *
   * @param parameterObject
   * @param context
   * @return
   */
  @Override
  public String dynamicSQL(Object parameterObject, ProviderContext context) {
    // The following judgment is made because the user is allowed to directly specify the current implementation,
    // such as the following configuration:
    // @DeleteProvider(type = DefaultProvider.class, method = "dynamicSQL")
    // At this point, because there is no processing by the BeanFactory, you need to call the init method separately.
    if (properties == null) {
      ProviderFactory.init(this);
    }
    //The sqlXml obtained here is really dynamic SQL
    String sqlXml = new ProviderSqlSource(parameterObject, context).createSql();
    //This wrap SQL and returns a UUID
    return Providers.wrapResult(sqlXml, context);
  }

  /**
   * Reference MyBatis ProviderSqlSource implementation
   */
  public class ProviderSqlSource {
    private final Class<?> providerType;
    private Object providerInstance;
    private Object parameterObject;
    private Method providerMethod;
    private String[] providerMethodArgumentNames;
    private Class<?>[] providerMethodParameterTypes;
    private ProviderContext providerContext;
    private Integer providerContextIndex;

    public ProviderSqlSource(Object parameterObject, ProviderContext context) {
      this.providerContext = context;
      this.parameterObject = parameterObject;
      Method mapperMethod = context.getMapperMethod();
      String providerClassStr = mapperMethod.getDeclaringClass().getName() + suffix;
      try {
        providerType = Resources.classForName(providerClassStr);
        providerInstance = ProviderFactory.newInstance(providerType);
      } catch (ClassNotFoundException e) {
        throw new UnsupportedProviderMethodException("Obtain [" + providerClassStr + "] class failure: " + e, e);
      }
      String providerMethodName = mapperMethod.getName();
      for (Method m : providerType.getMethods()) {
        if (providerMethodName.equals(m.getName()) && CharSequence.class.isAssignableFrom(m.getReturnType())) {
          if (providerMethod != null) {
            throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
                + providerMethodName + "' is found multiple in SqlProvider '" + providerType.getName()
                + "'. Sql provider method can not overload.");
          }
          providerMethod = m;
        }
      }
      if (this.providerMethod == null) {
        throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
            + providerMethodName + "' not found in SqlProvider '" + this.providerType.getName() + "'.");
      }
      this.providerMethodArgumentNames = new ParamNameResolver(configuration, this.providerMethod).getNames();
      this.providerMethodParameterTypes = this.providerMethod.getParameterTypes();
      for (int i = 0; i < this.providerMethodParameterTypes.length; i++) {
        Class<?> parameterType = this.providerMethodParameterTypes[i];
        if (parameterType == ProviderContext.class) {
          this.providerContextIndex = i;
        }
      }
    }

    public String createSql() {
      try {
        int bindParameterCount = providerMethodParameterTypes.length - (providerContext == null ? 0 : 1);
        String sql;
        if (providerMethodParameterTypes.length == 0) {
          sql = invokeProviderMethod();
        } else if (bindParameterCount == 0) {
          sql = invokeProviderMethod(providerContext);
        } else if (bindParameterCount == 1
            && (parameterObject == null || providerMethodParameterTypes[providerContextIndex == null || providerContextIndex == 1 ? 0 : 1].isAssignableFrom(parameterObject.getClass()))) {
          sql = invokeProviderMethod(extractProviderMethodArguments(parameterObject));
        } else if (parameterObject instanceof Map) {
          Map<String, Object> params = (Map<String, Object>) parameterObject;
          sql = invokeProviderMethod(extractProviderMethodArguments(params, providerMethodArgumentNames));
        } else {
          throw new BuilderException("Error invoking SqlProvider method ("
              + providerType.getName() + "." + providerMethod.getName()
              + "). Cannot invoke a method that holds "
              + (bindParameterCount == 1 ? "named argument(@Param)" : "multiple arguments")
              + " using a specifying parameterObject. In this case, please specify a 'java.util.Map' object.");
        }
        return sql;
      } catch (BuilderException e) {
        throw e;
      } catch (Exception e) {
        throw new BuilderException("Error invoking SqlProvider method ("
            + providerType.getName() + "." + providerMethod.getName()
            + ").  Cause: " + e, e);
      }
    }

    private Object[] extractProviderMethodArguments(Object parameterObject) {
      if (providerContext != null) {
        Object[] args = new Object[2];
        args[providerContextIndex == 0 ? 1 : 0] = parameterObject;
        args[providerContextIndex] = providerContext;
        return args;
      } else {
        return new Object[]{parameterObject};
      }
    }

    private Object[] extractProviderMethodArguments(Map<String, Object> params, String[] argumentNames) {
      Object[] args = new Object[argumentNames.length];
      for (int i = 0; i < args.length; i++) {
        if (providerContextIndex != null && providerContextIndex == i) {
          args[i] = providerContext;
        } else {
          args[i] = params.get(argumentNames[i]);
        }
      }
      return args;
    }

    private String invokeProviderMethod(Object... args) throws Exception {
      Object targetObject = null;
      if (!Modifier.isStatic(providerMethod.getModifiers())) {
        targetObject = providerInstance;
      }
      CharSequence sql = (CharSequence) providerMethod.invoke(targetObject, args);
      return sql != null ? sql.toString() : null;
    }

  }

}
