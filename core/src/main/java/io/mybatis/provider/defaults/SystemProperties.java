package io.mybatis.provider.defaults;

import io.mybatis.provider.Utils;

import java.util.Properties;

/**
 * Property configuration, priority:
 * <p>
 * <ol>
 *   <li>Java System Properties</li>
 *   <li>OS Environment Variables</li>
 *   <li>Properties file</li>
 * </ol>
 */
public class SystemProperties extends Properties {

    public SystemProperties(Properties defaults) {
        defaults.stringPropertyNames().forEach(property -> {
            setProperty(property, getPropertyValue(property, defaults.getProperty(property)));
        });
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return super.containsKey(key) || getProperty(key instanceof String ? (String) key : key.toString()) != null;
    }

    @Override
    public String getProperty(String key) {
        String val = super.getProperty(key);
        if(val == null) {
            val = getPropertyValue(key);
            if(val != null) {
                setProperty(key, val);
            }
        }
        return val;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return (val == null) ? defaultValue : val;
    }

    private String properyToEnv(String property) {
        if (Utils.isEmpty(property)) {
            return null;
        }
        return property.toUpperCase().replaceAll("\\.", "_");
    }

    private String getPropertyValue(String property) {
        String value = System.getProperty(property);
        if (Utils.isNotEmpty(value)) {
            return value;
        }
        value = System.getenv(property);
        if (Utils.isNotEmpty(value)) {
            return value;
        }
        value = System.getenv(properyToEnv(property));
        if (Utils.isNotEmpty(value)) {
            return value;
        }
        return null;
    }

    private String getPropertyValue(String property, String defaultValue) {
        String val = getPropertyValue(property);
        return (val == null) ? defaultValue : val;
    }
}
