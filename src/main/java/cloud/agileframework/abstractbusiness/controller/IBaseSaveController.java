package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.validate.group.Insert;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.groups.Default;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 新增基础控制去
 * @version 1.0
 * @since 1.0
 */
public interface IBaseSaveController<E extends IBaseEntity, O extends IBaseOutParamVo> extends IBaseController<E, O> {
    /**
     * 新增接口
     *
     * @param entity 要操作的实体名
     * @return 响应
     */
    @SneakyThrows
    @PostMapping(value = {"${agile.base-service.save:}"})
    default RETURN save(@AgileInParam E entity) {
        validate(entity, Default.class, Insert.class);
        service().saveData(entity);
        return RETURN.SUCCESS;
    }
}
