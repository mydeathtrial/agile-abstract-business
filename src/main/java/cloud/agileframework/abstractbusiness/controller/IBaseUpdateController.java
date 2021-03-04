package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.Update;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.groups.Default;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 更新基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseUpdateController<E extends IBaseEntity, O extends IBaseOutParamVo> extends IBaseController<E, O> {
    /**
     * 更新
     *
     * @param entity 实体
     */
    @SneakyThrows
    @Validate(value = "id", nullable = false)
    @PutMapping(value = {"${agile.base-service.update:}"})
    default RETURN update(@AgileInParam E entity) {
        validate(entity, Default.class, Update.class);
        service().updateData(entity);
        return RETURN.SUCCESS;
    }
}
