package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.validate.annotation.Validate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 删除基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseDeleteController<E extends IBaseEntity, O extends IBaseOutParamVo> extends IBaseController<E, O> {

    String ID = "id";

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return 响应
     */
    @Validate(value = ID, nullable = false)
    @DeleteMapping(value = {"${agile.base-service.deleteById:/{id}}"})
    default RETURN delete(@PathVariable(ID) String id) {
        service().deleteById(id, getEntityClass());
        return RETURN.SUCCESS;
    }

    /**
     * 根据主键集合批量删除数据
     *
     * @param ids 主键集合
     * @return 响应
     */
    @Validate(nullable = false)
    @DeleteMapping(value = {"${agile.base-service.deleteByIds:}"})
    default RETURN delete(@AgileInParam List<String> ids) {
        service().deleteByIds(ids, getEntityClass());
        return RETURN.SUCCESS;
    }

    /**
     * 清空表
     *
     * @return 响应
     */
    @DeleteMapping(value = {"${agile.base-service.clean:/all}"})
    default RETURN clean() {
        service().clean(getEntityClass());
        return RETURN.SUCCESS;
    }
}
