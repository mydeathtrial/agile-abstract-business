package cloud.agileframework.abstractbusiness.pojo.vo;

import cloud.agileframework.common.annotation.Alias;
import cloud.agileframework.common.util.collection.SortInfo;
import cloud.agileframework.validate.group.PageQuery;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-01-26 11:43
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Data
@ToString
public class BaseInParamVo implements Serializable {
    /**
     * 排序字段
     * 表名称
     */
    private List<SortInfo> sortColumn;

    /**
     * 页数
     */
    @Alias("page")
    @NotNull(groups = PageQuery.class)
    private Integer pageNum;

    /**
     * 数量
     */
    @NotNull(groups = PageQuery.class)
    private Integer pageSize;
}
