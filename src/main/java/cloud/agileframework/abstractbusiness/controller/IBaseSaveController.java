package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.BaseEntity;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseOutParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.abstractbusiness.service.BaseService;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.spring.util.ServletUtil;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.Insert;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 新增基础控制去
 * @version 1.0
 * @since 1.0
 */
public interface IBaseSaveController<E extends IBaseEntity,O extends IBaseOutParamVo> extends IBaseController<E,O> {
    /**
     * 新增接口
     * @param entity 要操作的实体名
     */
    @SneakyThrows
    @PostMapping(value = {"${agile.base-service.save:}"})
    default RETURN save(@AgileInParam E entity) {
        validate(entity, Default.class,Insert.class);
        service().saveData(entity);
        return RETURN.SUCCESS;
    }
}
