package cloud.agileframework.abstractbusiness.pojo.template.view.form;

import java.sql.Timestamp;
import java.util.Date;

public enum CodeType {
    STRING(String.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    DATE(Date.class),
    BOOLEAN(Boolean.class),
    TIMESTAMP(Timestamp.class);
        
    private final Class<?> reallyClass;
    CodeType(Class<?> reallyClass) {
        this.reallyClass = reallyClass;
    }

    public Class<?> getReallyClass() {
        return reallyClass;
    }
}
