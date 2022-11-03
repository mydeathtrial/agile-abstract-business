package cloud.agileframework.abstractbusiness.pojo.template.simple;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElement;
import lombok.Data;

import java.util.Map;
@Data
public class TableDataTemplate {
    private Map<String, FormElement> column;
    private PageTemplate page;
}
