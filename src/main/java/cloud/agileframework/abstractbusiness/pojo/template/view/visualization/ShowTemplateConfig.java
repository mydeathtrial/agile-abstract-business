package cloud.agileframework.abstractbusiness.pojo.template.view.visualization;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表格配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShowTemplateConfig extends ShowAbstractConfig {
    //骨架结构
    private String frame;
}
