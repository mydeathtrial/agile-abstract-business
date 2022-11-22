package cloud.agileframework.abstractbusiness.pojo.template.view.visualization;

import java.io.Serializable;

public interface ShowConfig extends Serializable {
    //响应结果的展示形式
    ShowType type();
}
