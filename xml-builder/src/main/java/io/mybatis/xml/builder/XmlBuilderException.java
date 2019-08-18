package io.mybatis.xml.builder;

/**
 * @author liuzh
 */
public class XmlBuilderException extends RuntimeException {
    public XmlBuilderException() {
    }

    public XmlBuilderException(String message) {
        super(message);
    }

    public XmlBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlBuilderException(Throwable cause) {
        super(cause);
    }

    public XmlBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
