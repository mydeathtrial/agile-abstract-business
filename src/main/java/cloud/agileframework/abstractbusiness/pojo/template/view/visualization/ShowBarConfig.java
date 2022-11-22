package cloud.agileframework.abstractbusiness.pojo.template.view.visualization;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表格配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShowBarConfig extends ShowAbstractConfig {
    @Override
    public ShowType type() {
        return ShowType.BAR;
    }
}