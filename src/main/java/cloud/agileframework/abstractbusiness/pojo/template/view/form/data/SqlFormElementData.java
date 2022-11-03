package cloud.agileframework.abstractbusiness.pojo.template.view.form.data;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementDataType;
import lombok.Data;

@Data
public class SqlFormElementData implements FormElementData {
    private String value;
    @Override
    public FormElementDataType type() {
        return FormElementDataType.SQL;
    }
}
