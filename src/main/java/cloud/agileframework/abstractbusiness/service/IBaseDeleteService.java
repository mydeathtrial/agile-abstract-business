package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.validate.annotation.Validate;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 删除基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseDeleteService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseService<E, I, O> {

    String ID = "id";

    /**
     * 根据主键删除
     *
     * @return 响应
     */
    @Validate(value = ID, nullable = false)
    @Mapping(value = {"${agile.base-service.deleteById:/{id}}"}, method = RequestMethod.DELETE)
    default RETURN deleteById() throws Exception {
        IBaseDeleteService<E, I, O> service = (IBaseDeleteService<E, I, O>)BeanUtil.getApplicationContext().getBean(getClass());
        service.delete(AgileParam.getInParam(ID, String.class));
        return RETURN.SUCCESS;
    }

    default void delete(String id) throws Exception {
        if (dataManager() != null) {
            dataManager().sync().deleteById(id);
            return;
        }
        genericService().deleteById(id, getEntityClass());
    }

    /**
     * 根据主键集合批量删除数据
     *
     * @return 响应
     */
    @Validate(nullable = false)
    @Mapping(value = {"${agile.base-service.deleteByIds:}"}, method = RequestMethod.DELETE)
    default RETURN deleteByIds() throws Exception {
        IBaseDeleteService<E, I, O> service = (IBaseDeleteService<E, I, O>)BeanUtil.getApplicationContext().getBean(getClass());
        service.delete(AgileParam.getInParam(ID, new TypeReference<List<String>>() {
        }));
        return RETURN.SUCCESS;
    }

    default void delete(List<String> ids) throws Exception {
        IBaseDeleteService<E, I, O> service = (IBaseDeleteService<E, I, O>)BeanUtil.getApplicationContext().getBean(getClass());
        
        if (dataManager() != null) {
            for (String id : ids) {
                service.delete(id);
            }
            return;
        }
        genericService().deleteByIds(ids, getEntityClass());
    }

    /**
     * 清空表
     *
     * @return 响应
     */
    @Mapping(value = {"${agile.base-service.clean:/all}"}, method = RequestMethod.DELETE)
    default RETURN clean() throws Exception {
        if (dataManager() != null) {
            return RETURN.FAIL;
        }
        genericService().clean(getEntityClass());
        return RETURN.SUCCESS;
    }
}
