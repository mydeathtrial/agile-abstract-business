package cloud.agileframework.abstractbusiness.pojo.template;

import cloud.agileframework.abstractbusiness.pojo.template.request.FunctionRequest;
import cloud.agileframework.abstractbusiness.pojo.template.response.FunctionResponse;
import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * api
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Function extends BaseViewElement implements Template {
    //api相关的sql语句
    private String sql;
    //请求方式配置
    private FunctionRequest request;
    //响应方式配置
    private FunctionResponse response;
}
