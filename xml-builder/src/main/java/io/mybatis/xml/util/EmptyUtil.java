package io.mybatis.xml.util;

import java.util.Collection;

/**
 * @author liuzh
 */
public class EmptyUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection str) {
        return !isEmpty(str);
    }

}
