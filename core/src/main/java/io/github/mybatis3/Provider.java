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

import io.github.mybatis3.defaults.DefaultProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * Provider processing method, a simple encapsulation of the default ProviderSqlSource call ,
 * <p>
 * Convenient MapperLanguageDriver to get the necessary data
 *
 * @author liuzh
 */
public interface Provider {

  /**
   * Whether to support the current Mapper method
   *
   * @param parameterObject
   * @param context
   * @return
   */
  boolean isSupport(Object parameterObject, ProviderContext context);

  /**
   * Generate dynamic SQL data, where the actual return value is a UUID, not a real SQL
   * <p>
   * Implementation method reference {@link DefaultProvider}
   *
   * @param parameterObject
   * @param context
   * @return
   */
  String dynamicSQL(Object parameterObject, ProviderContext context);

}
