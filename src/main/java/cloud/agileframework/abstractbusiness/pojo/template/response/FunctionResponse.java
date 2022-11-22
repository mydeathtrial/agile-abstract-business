package cloud.agileframework.abstractbusiness.pojo.template.response;

import cloud.agileframework.abstractbusiness.pojo.template.view.visualization.ShowConfig;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应方式配置
 */
@Data
public class FunctionResponse implements Serializable {
    //动作
    private Action action;
    //展示相关的配置
    private ShowConfig show;
}
