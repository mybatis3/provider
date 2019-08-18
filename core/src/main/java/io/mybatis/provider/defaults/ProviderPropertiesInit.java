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

package io.mybatis.provider.defaults;

import io.mybatis.provider.ProviderInit;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties initialization
 *
 * @author liuzh
 */
public class ProviderPropertiesInit implements ProviderInit {
  /**
   * default configuration
   */
  private static final String DEFAULT_PROVIDER_PROPERTIES = "META-INF/default_provider.properties";
  /**
   * user defined configuration
   */
  private static final String USER_PROVIDER_PROPERTIES = "META-INF/provider.properties";

  private volatile Properties properties;

  /**
   * Initialize configuration information
   *
   * @return
   */
  public Properties initProperties() {
    Properties defaultProperties = null;
    try {
      InputStream defaultInputStream = Resources.getResourceAsStream(DEFAULT_PROVIDER_PROPERTIES);
      defaultProperties = new Properties();
      defaultProperties.load(defaultInputStream);
    } catch (IOException e) {
      //ignore
    }
    try {
      InputStream inputStream = Resources.getResourceAsStream(USER_PROVIDER_PROPERTIES);
      Properties properties = new Properties(defaultProperties);
      properties.load(inputStream);
      return properties;
    } catch (IOException e) {
      //ignore
    }
    return defaultProperties;
  }

  @Override
  public void init(Object bean) {
    if(bean instanceof ProviderProperties){
      if(properties == null){
        synchronized (this){
          if(properties == null){
            properties = initProperties();
          }
        }
      }
      ((ProviderProperties) bean).init(properties);
    }
  }

}
