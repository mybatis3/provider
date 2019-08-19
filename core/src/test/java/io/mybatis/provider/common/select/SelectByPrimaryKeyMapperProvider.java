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

package io.mybatis.provider.common.select;

import io.mybatis.provider.Providers;
import org.apache.ibatis.builder.annotation.ProviderContext;

import javax.persistence.Table;

public class SelectByPrimaryKeyMapperProvider {

  public String selectByPrimaryKey(Object params, ProviderContext context) {
    Class genericType = Providers.getReturnType(context);
    Table table = (Table) genericType.getAnnotation(Table.class);
    String script = "<select id=\"selectByPrimaryKey\" resultType=\"" + genericType.getName()
        + "\"> select * from " + table.name() + " where id = #{id}</select>";
    return script;
  }

}