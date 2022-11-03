package cloud.agileframework.abstractbusiness.pojo.template;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Router extends BaseViewElement implements Template {
    private String value;
}
