package cloud.agileframework.abstractbusiness.controller;

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
public interface AllBusinessController<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends
        IBaseSaveController<E, I, O>,
        IBaseDeleteController<E, I, O>,
        IBaseUpdateController<E, I, O>,
        IBaseQueryController<E, I, O> {
}
