package cloud.agileframework.abstractbusiness.pojo.template.view.form;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.data.FormElementData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormElement extends BaseViewElement {
    //表单项展示方式
    private FormElementType showType;
    //表单项关联数据
    private FormElementData data;
    private boolean show;
    private boolean filter;
    private boolean update;
    private boolean insert;
    //顺序
    private int sort;
}
