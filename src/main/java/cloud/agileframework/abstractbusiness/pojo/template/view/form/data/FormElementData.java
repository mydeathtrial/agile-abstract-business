package cloud.agileframework.abstractbusiness.pojo.template.view.form.data;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementDataType;

import java.io.Serializable;

public interface FormElementData extends Serializable {
    FormElementDataType type();

    Object data() throws Exception;
}
