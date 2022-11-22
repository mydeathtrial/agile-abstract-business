package cloud.agileframework.abstractbusiness.pojo.template.view.link;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElement;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
 * 链接
 */
@Data
public class Link implements Serializable {
    //链接地址
    private LinkType type;
    //链接内容，如具体地址、具体的api、路由等等
    private String value;
    //传递的参数
    private Map<String, FormElement> param;
    //打开形式，新页面，当前页面、弹窗
    private Target target;
}
