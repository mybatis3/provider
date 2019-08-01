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

package io.github.mybatis3.common.delete;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;
import io.github.mybatis3.ProviderLanguageDriver;
import io.github.mybatis3.defaults.DefaultProvider;

/**
 * @author liuzh
 */
public interface DeleteMapper<T> {

  @Lang(ProviderLanguageDriver.class)
  //specify DefaultProvider directly here
  @DeleteProvider(type = DefaultProvider.class, method = "dynamicSQL")
  int delete(T record);

}