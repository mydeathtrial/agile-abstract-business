package cloud.agileframework.abstractbusiness.pojo.vo;

import cloud.agileframework.common.util.collection.SortInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    private Integer pageNum;

    /**
     * 数量
     */
    private Integer pageSize;
}
