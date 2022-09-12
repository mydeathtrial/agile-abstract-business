package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.security.filter.login.CustomerUserDetails;
import cloud.agileframework.spring.util.SecurityUtil;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.groups.Default;
import java.util.Date;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 更新基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseUpdateController<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseController<E, O> {
    /**
     * 更新
     *
     * @param inParam 入参
     * @return 响应
     */
    @Validate(value = "id", nullable = false)
    @PutMapping(value = {"${agile.base-service.update:}"})
    default RETURN update(@AgileInParam I inParam) throws Exception {
        genericService().validate(inParam, Default.class, Update.class);
        E data = ObjectUtil.to(inParam, new TypeReference<>(getEntityClass()));
        genericService().validateEntityExists(data);
        genericService().validateEntity(data, Default.class, Update.class);

        try {
            data.setUpdateTime(new Date());
            UserDetails currentUser = SecurityUtil.currentUser();
            if (currentUser instanceof CustomerUserDetails) {
                data.setUpdateUser(((CustomerUserDetails) currentUser).id());
            }
        } catch (Exception ignored) {
        }

        AgileReturn.add(Constant.ResponseAbout.RESULT, toSingleOutVo(genericService().updateData(data)));
        return RETURN.SUCCESS;
    }
}
