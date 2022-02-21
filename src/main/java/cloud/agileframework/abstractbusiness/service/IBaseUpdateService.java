package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.Update;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.groups.Default;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 更新基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseUpdateService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseService<E, I, O> {
    /**
     * 更新
     *
     * @return 响应
     */
    @SneakyThrows
    @Validate(nullable = false)
    @Mapping(value = {"${agile.base-service.update:}"}, method = RequestMethod.PUT)
    default RETURN update() {
        I inParam = AgileParam.getInParam(getInVoClass());
        E data = AgileParam.getInParam(getEntityClass());
        validate(inParam, Default.class, Update.class);
        validateEntityExists(data);
        validateEntity(data, Default.class, Update.class);
        AgileReturn.add(Constant.ResponseAbout.RESULT, toSingleOutVo(service().updateData(data)));
        return RETURN.SUCCESS;
    }
}
