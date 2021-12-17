package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:47
 * 描述 控制层基类
 * @version 1.0
 * @since 1.0
 */
public interface AllBusinessService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends
        IBaseSaveService<E, I, O>,
        IBaseDeleteService<E, I, O>,
        IBaseUpdateService<E, I, O>,
        IBaseQueryService<E, I, O> {
}
