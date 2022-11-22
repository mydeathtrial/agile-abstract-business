package cloud.agileframework.abstractbusiness.pojo.template.view.visualization;

import cloud.agileframework.abstractbusiness.pojo.template.view.BaseViewElement;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.Button;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementType;
import cloud.agileframework.abstractbusiness.pojo.template.view.link.Link;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 表格配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShowTableConfig extends ShowAbstractConfig {
    private Map<String, QueryColumn> column;
    private List<Button> button;
    @Override
    public ShowType type() {
        return ShowType.PAGE;
    }
    /**
     * 表格字段
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class QueryColumn extends BaseViewElement {
        //展现形式
        private FormElementType type;
        //排序
        private int sort;
        //默认展示
        private boolean defShow;
        //排序方式
        private Order order;
        //链接
        private Link link;

        /**
         * 字段排序
         */
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Order implements Serializable {
            //开关
            private boolean enable;
            //是否能编辑
            private boolean edit;
            //默认排序方式
            private Sort.Direction def;
            //排序优先级
            private int sort;
        }
    }
}
