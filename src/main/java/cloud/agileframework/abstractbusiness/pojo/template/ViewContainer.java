package cloud.agileframework.abstractbusiness.pojo.template;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ViewContainer extends BaseViewElement implements Template {
    //页头
    private ViewContainerRegion header;
    //体
    private ViewContainerRegion container;
    //页脚
    private ViewContainerRegion footer;
    //侧边
    private ViewContainerRegion aside;
}
