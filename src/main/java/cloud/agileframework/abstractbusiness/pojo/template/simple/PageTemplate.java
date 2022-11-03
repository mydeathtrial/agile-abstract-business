package cloud.agileframework.abstractbusiness.pojo.template.simple;

import cloud.agileframework.abstractbusiness.pojo.template.view.visualization.ShowTableConfig;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class PageTemplate {
    private Map<String, ShowTableConfig.Column> column;
    private List<ButtonType> button;
}
