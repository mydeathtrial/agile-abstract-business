package cloud.agileframework.abstractbusiness.pojo.template.view.visualization;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.Button;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementType;
import cloud.agileframework.abstractbusiness.pojo.template.view.link.Link;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 表格配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShowTableConfig extends ShowAbstractConfig {
    private Map<String, Column> column;
    private List<Button> button;

    /**
     * 表格字段
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Column extends BaseViewElement {
        //展现形式
        private FormElementType type;
        //排序
        private int sort;
        //链接
        private Link link;
        //字段绑定的按钮组
        private List<Button> button;

        /**
         * 字段排序
         */
        @Data
        public static class Order {
            //开关
            private boolean enable;
            //是否能编辑
            private boolean edit;
            //默认排序方式 true正序，false倒叙
            private boolean def;
            //排序优先级
            private int sort;
        }
    }
}
