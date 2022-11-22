package cloud.agileframework.abstractbusiness.pojo.template.view.visualization;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表格配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShowNoneConfig extends ShowAbstractConfig {
    @Override
    public ShowType type() {
        return ShowType.NONE;
    }
}
