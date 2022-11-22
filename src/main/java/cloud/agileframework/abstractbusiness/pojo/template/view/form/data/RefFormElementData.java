package cloud.agileframework.abstractbusiness.pojo.template.view.form.data;

import cloud.agileframework.abstractbusiness.pojo.template.curd.ButtonType;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementDataType;
import cloud.agileframework.abstractbusiness.service.TemplateEngin;
import cloud.agileframework.common.util.file.poi.ExcelFormatException;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.spring.util.BeanUtil;
import lombok.Data;

/**
 * 引用值
 */
@Data
public class RefFormElementData implements FormElementData {
    //值
    private String id;
    private ButtonType buttonType;
    @Override
    public FormElementDataType type() {
        return FormElementDataType.CONSTANT;
    }

    @Override
    public Object data() throws Exception {
        return BeanUtil.getBean(TemplateEngin.class).parse(id,buttonType);
    }
}
