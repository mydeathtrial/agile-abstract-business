package cloud.agileframework.abstractbusiness.pojo.template.view;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseViewElement implements Serializable {
    //页面元素脚本配置
    private JSONObject option;
    //样式
    private String style;
    //是否展示
    private boolean show;
    //栅栏格
    private int span;
}
