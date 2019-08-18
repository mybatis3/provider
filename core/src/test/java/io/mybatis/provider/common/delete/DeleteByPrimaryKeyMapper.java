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

package io.mybatis.provider.common.delete;

import io.mybatis.provider.ProviderLanguageDriver;
import io.mybatis.provider.Providers;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;

/**
 * @author liuzh
 */
public interface DeleteByPrimaryKeyMapper<T> {

  @Lang(ProviderLanguageDriver.class)
  //Providers are used here to allow support for implementations from a variety of strategies.
  @DeleteProvider(type = Providers.class, method = "dynamicSQL")
  int deleteByPrimaryKey(Object key);

}