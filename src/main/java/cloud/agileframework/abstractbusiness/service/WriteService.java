package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;

/**
 * @author 佟盟
 * 日期 2022-10-08 10:47
 * 描述 控制层基类
 * @version 1.0
 * @since 1.0
 */
public interface WriteService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends
        IBaseSaveService<E, I, O>,
        IBaseDeleteService<E, I, O>,
        IBaseUpdateService<E, I, O> {

    /**
     * 保存或更新
     *
     * @param data 数据
     * @throws Exception 异常
     */
    default E saveOrUpdate(E data) throws Exception {
        Object id = dao().getId(data);
        if (id == null) {
            return saveDo(data);
        } else {
            if (dao().existsById(getEntityClass(), id.toString())) {
                return updateDo(data);
            } else {
                return saveDo(data);
            }
        }
    }
}
