package cloud.agileframework.abstractbusiness.pojo.template.view.form.data;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementDataType;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 静态变量表单项值范围配置
 */
@Data
public class ConstantFormElementData implements FormElementData {
    //值
    private String value;
    @Override
    public FormElementDataType type() {
        return FormElementDataType.CONSTANT;
    }

    @Override
    public Object data() {
        return value==null?null:JSON.parse(value);
    }
}
