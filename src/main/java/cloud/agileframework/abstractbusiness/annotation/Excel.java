package cloud.agileframework.abstractbusiness.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Excel {

    /**
     * 属性名字
     */
    String name();

    /**
     * 字段顺序
     */
    int sort() default 0;

    /**
     * 字段类型
     */
    Class<?> type() default Object.class;
    
    String format() default "";

    /**
     * 序列化，导出用
     */
    Class<? extends ExcelSerialize> serialize() default ExcelSerialize.class;

    /**
     * 反序列化，导入用
     */
    Class<? extends ExcelDeserialize> deserialize() default ExcelDeserialize.class;
}
