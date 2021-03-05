package cloud.agileframework.abstractbusiness.pojo.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 佟盟
 * 日期 2021-01-26 13:49
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Data
@ToString
public class BaseOutParamVo implements IBaseOutParamVo {
    /**
     * 创建者
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改者
     */
    private Long updateUserId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否启用
     */
    private Integer isEnabled;
}
