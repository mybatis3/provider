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

import java.lang.reflect.Method;

/**
 * @author liuzh
 */
public class UnsupportedProviderMethodException extends RuntimeException {
  public UnsupportedProviderMethodException() {
    super();
  }

  public UnsupportedProviderMethodException(String message) {
    super(message);
  }

  public static UnsupportedProviderMethodException throwByProviderContext(ProviderContext context){
    Class<?> mapperType = context.getMapperType();
    Method mapperMethod = context.getMapperMethod();
    StringBuilder builder = new StringBuilder();
    builder.append("NotSupport ").append(mapperType.getName()).append(" class ").append(mapperMethod.getName()).append(" method");
    return new UnsupportedProviderMethodException(builder.toString());
  }


  public UnsupportedProviderMethodException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnsupportedProviderMethodException(Throwable cause) {
    super(cause);
  }

  protected UnsupportedProviderMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
