package cloud.agileframework.abstractbusiness.pojo.template.response;

import cloud.agileframework.abstractbusiness.pojo.template.view.visualization.ShowConfig;
import cloud.agileframework.abstractbusiness.pojo.template.view.visualization.ShowType;

/**
 * 响应方式配置
 */
public class FunctionResponse {
    //响应结果的展示形式
    private ShowType type;
    //动作
    private Action action;
    //展示相关的配置
    private ShowConfig config;
}
