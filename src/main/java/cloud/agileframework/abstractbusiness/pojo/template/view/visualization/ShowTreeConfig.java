package cloud.agileframework.abstractbusiness.pojo.template.view.visualization;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.Button;
import cloud.agileframework.abstractbusiness.pojo.template.view.link.Link;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 表格配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShowTreeConfig extends ShowAbstractConfig {
    private Link link;
    private List<Button> button;
}
