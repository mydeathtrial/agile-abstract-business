package cloud.agileframework.abstractbusiness.pojo.template.view.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class FormValidate {
    //最小值
    private Integer min;
    //最大值
    private Integer max;
    //最小长度
    private Integer minSize;
    //最大长度
    private Integer maxSize;
    //正则
    private String validateRegex;
    //国际化信息
    private String validateMsg;
    //是否可以为null
    private Boolean nullable;
    //是否可以为空白值
    private Boolean isBlank;
}
