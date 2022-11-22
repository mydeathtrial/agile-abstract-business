package cloud.agileframework.abstractbusiness.pojo.template.view.form.data;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementDataType;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.spring.util.BeanUtil;
import lombok.Data;

import java.util.Objects;

@Data
public class SqlFormElementData implements FormElementData {
    private String value;
    @Override
    public FormElementDataType type() {
        return FormElementDataType.SQL;
    }

    @Override
    public Object data() {
        return Objects.requireNonNull(BeanUtil.getBean(Dao.class))
                .findBySQL(value, AgileParam.getInParam());
    }
}
