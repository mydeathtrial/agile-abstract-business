package cloud.agileframework.abstractbusiness.pojo.template.view.form;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.data.FormElementData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FormElement extends BaseViewElement {
    //显示名
    private String name;
    //备注
    private String title;
    //表单项展示方式
    private FormElementType type;
    //程序类型
    private CodeType codeType;
    //顺序
    private int sort;
    //默认值
    private Object def;
    //表单项关联数据
    private FormElementData data;
    //表单验证方式
    private FormValidate validate;
}
