package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.conf.BusinessAutoConfiguration;
import cloud.agileframework.common.annotation.Remark;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.common.util.string.StringUtil;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 佟盟
 * 日期 2021-02-20 17:15
 * 描述 公共基础业务控制器
 * @version 1.0
 * @since 1.0
 */
@AgileService
@Mapping("${agile.base-service.rootPath:${agile.module-name:api}/{model}/default}")
public class BaseBusinessService {

    /**
     * 模型传参
     */
    public static final String MODEL = "model";

    @Autowired
    private BusinessAutoConfiguration.BaseServiceOfController baseService;

    public static List<ValidateMsg> toValidateMessages(Object data, Class<?>... groups) {
        if (data instanceof Class) {
            return Lists.newArrayList();
        }
        List<ValidateMsg> result = ValidateUtil.validate(data, groups);
        if (!result.isEmpty()) {
            return result;
        }

        //判断字段唯一
        Set<ClassUtil.Target<Column>> columns = ClassUtil.getAllEntityAnnotation(data.getClass(), Column.class);
        Set<Member> any = columns.stream().filter(c -> c.getAnnotation().unique()).map(ClassUtil.Target::getMember).collect(Collectors.toSet());
        Dao dao = BeanUtil.getBean(Dao.class);
        if (!any.isEmpty()) {
            return any.stream()
                    .map(member -> {
                        String name = member.getName();
                        if (member instanceof Method && name.startsWith("get")) {
                            name = StringUtil.toLowerName(name.substring(3));
                        }
                        return name;
                    })
                    .map(fieldName -> {
                        Object demo = ClassUtil.newInstance(data.getClass());
                        if (demo == null) {
                            return null;
                        }
                        Object fieldValue = setFieldValue(data, fieldName, demo);

                        List<Object> demoList = dao.findAll(demo)
                                .stream()
                                .filter(n->!Objects.equals(dao.getId(n),dao.getId(data)))
                                .collect(Collectors.toList());

                        if (!demoList.isEmpty()) {
                            return new ValidateMsg( fieldValue + "不允许重复", fieldName, fieldValue);
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

        }

        //判断多字段组合唯一
        Map<String, Member> map = columns.stream().collect(Collectors.toMap(c -> c.getAnnotation().name(), ClassUtil.Target::getMember));
        Table table = data.getClass().getAnnotation(Table.class);
        if (table.uniqueConstraints().length > 0) {
            for (UniqueConstraint uniqueConstraint : table.uniqueConstraints()) {
                Object testData = ClassUtil.newInstance(data.getClass());
                if (testData == null) {
                    continue;
                }
                List<String> remarks = Lists.newArrayList();
                Arrays.stream(uniqueConstraint.columnNames())
                        .map(map::get)
                        .filter(Objects::nonNull)
                        .map(member -> {
                            String name = member.getName();
                            if (member instanceof Method && name.startsWith("get")) {
                                name = StringUtil.toLowerName(name.substring(3));
                            }
                            return name;
                        })
                        .forEach(fieldName -> {
                            Remark remark = ClassUtil.getFieldAnnotation(data.getClass(), fieldName, Remark.class);
                            String fieldRemark = remark == null ? fieldName : remark.value();
                            remarks.add(fieldRemark);
                            setFieldValue(data, fieldName, testData);
                        });
                List<Object> demoList = dao.findAll(testData)
                        .stream()
                        .filter(n->!Objects.equals(dao.getId(n),dao.getId(data)))
                        .collect(Collectors.toList());
                if (!demoList.isEmpty()) {
                    return Lists.newArrayList(new ValidateMsg(String.join(",", remarks) + "组合不能重复", "", ""));
                }
            }
        }

        return Lists.newArrayList();
    }

    private static Object setFieldValue(Object data, String fieldName, Object testData) {
        Field field = ClassUtil.getField(data.getClass(), fieldName);
        Object fieldValue;
        fieldValue = ObjectUtil.getFieldValue(data, field);
        ObjectUtil.setValue(testData, field, fieldValue);
        return fieldValue;
    }
}
