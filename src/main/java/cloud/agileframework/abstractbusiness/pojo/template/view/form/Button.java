package cloud.agileframework.abstractbusiness.pojo.template.view.form;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import cloud.agileframework.abstractbusiness.pojo.template.view.link.Link;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 按钮
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Button extends BaseViewElement {
    //链接
    private Link link;
    //排序
    private int sort;

    @Override
    public boolean isShow() {
        return true;
    }
}
