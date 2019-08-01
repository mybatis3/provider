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

/**
 * @author liuzh
 */
public class ProviderSql {
  /**
   * The real script information must be the XML form of the one of select, insert, update, delete
   */
  private String sqlXml;
  private ProviderContext context;

  public ProviderSql() {
  }

  public ProviderSql(String sqlXml, ProviderContext context) {
    this.sqlXml = sqlXml;
    this.context = context;
  }

  public String getSqlXml() {
    return sqlXml;
  }

  public void setSqlXml(String sqlXml) {
    this.sqlXml = sqlXml;
  }

  public ProviderContext getContext() {
    return context;
  }

  public void setContext(ProviderContext context) {
    this.context = context;
  }
}
