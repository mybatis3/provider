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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Unified processing of all bean instances
 *
 * @author liuzh
 */
public class ProviderFactory {

  private static volatile List<ProviderInit> providerInits;

  /**
   * get all beaninit instances
   *
   * @return
   */
  private static List<ProviderInit> getProviderInits() {
    if (providerInits == null) {
      synchronized (ProviderFactory.class) {
        if (providerInits == null) {
          providerInits = new ArrayList<>();
          ServiceLoader<ProviderInit> beanInitServiceLoader = ServiceLoader.load(ProviderInit.class);
          beanInitServiceLoader.forEach(beanInit -> {
            providerInits.add(beanInit);
          });
          for (ProviderInit beanInit : providerInits) {
            for (ProviderInit init : providerInits) {
              if (init != beanInit) {
                init.init(beanInit);
              }
            }
          }
        }
      }
    }
    return providerInits;
  }

  /**
   * create a beanClass instance
   *
   * @param beanClass
   * @param <T>
   * @return
   */
  public static <T> T newInstance(Class<T> beanClass) {
    try {
      T instance = beanClass.getDeclaredConstructor().newInstance();
      init(instance);
      return instance;
    } catch (IllegalAccessException e) {
      throw new RuntimeException("reflection creation [" + beanClass + "] class failure: " + e, e);
    } catch (InstantiationException e) {
      throw new RuntimeException("initialize [" + beanClass + "] class failure: " + e, e);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("obtain [" + beanClass + "] class constructor failed: " + e, e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException("invoke [" + beanClass + "] constructor failed: " + e, e);
    }
  }

  /**
   * initialize bean
   *
   * @param bean
   */
  public static void init(Object bean) {
    getProviderInits().forEach(beanInit -> {
      beanInit.init(bean);
    });
  }

}
