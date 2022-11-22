package cloud.agileframework.abstractbusiness.pojo.template.function;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ViewContainerRegion extends BaseViewElement implements Template {
    private TemplateType type;
    private Template value;
}
