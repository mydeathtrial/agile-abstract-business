package cloud.agileframework.abstractbusiness.pojo.template.view.form;

import cloud.agileframework.abstractbusiness.pojo.template.curd.ButtonType;
import cloud.agileframework.validate.ValidateConfig;
import cloud.agileframework.validate.group.Delete;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Query;
import cloud.agileframework.validate.group.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormValidate implements Serializable {
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
    //场景
    private List<ButtonType> groups;

    public ValidateConfig to() {
        return ValidateConfig.builder()
                .min(min == null ? Integer.MIN_VALUE : min)
                .max(max == null ? Long.MAX_VALUE : max)
                .minSize(minSize == null ? 0 : minSize)
                .maxSize(maxSize == null ? Integer.MAX_VALUE : maxSize)
                .validateRegex(StringUtils.isBlank(validateRegex) ? null : validateRegex)
                .validateMsg(StringUtils.isBlank(validateMsg) ?  "不符合规定格式" : validateMsg)
                .nullable(nullable == null || nullable)
                .isBlank(isBlank == null || isBlank)
                .validateGroups(groups == null ? null : groups.stream().map(group -> {
                    switch (group) {
                        case ADD:
                            return Insert.class;
                        case DELETE:
                        case ROW_DELETE:
                            return Delete.class;
                        case ROW_UPDATE:
                            return Update.class;
                        case PAGE:
                        case TREE:
                        case ROW_DETAIL:
                            return Query.class;
                    }
                    return null;
                }).filter(Objects::nonNull).toArray(Class[]::new))
                .build();
    }
}
