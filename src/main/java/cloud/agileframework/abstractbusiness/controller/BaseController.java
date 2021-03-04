package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.BaseEntity;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseOutParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.abstractbusiness.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:47
 * 描述 控制层基类
 * @version 1.0
 * @since 1.0
 */
public class BaseController<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> implements
        IBaseSaveController<E,O>,
        IBaseDeleteController<E,O>,
        IBaseUpdateController<E,O>,
        IBaseQueryController<E, I, O> {

    @Autowired
    private BaseService baseService;
    protected Class<E> entityClass = null;
    protected Class<O> outVoClass = null;

    @Override
    public BaseService service() {
        return baseService;
    }

    @Override
    public Class<E> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return this.entityClass;
    }

    @Override
    public Class<O> getOutVoClass() {
        if (outVoClass == null) {
            this.outVoClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        }
        return this.outVoClass;
    }
}
