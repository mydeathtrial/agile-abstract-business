package cloud.agileframework.abstractbusiness.pojo.template.request;

import cloud.agileframework.abstractbusiness.pojo.template.curd.Column;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.Button;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 请求方式配置
 */
@Data
public class FunctionRequest {
    //请求参数配置，用于生成表单
    private Map<String, Column> param;
    //表单按钮
    private List<Button> button;
    //请求是否需要认证
    private Security security;
}
