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
import cloud.agileframework.validate.group.Insert;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.groups.Default;
import java.util.Date;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 新增基础控制去
 * @version 1.0
 * @since 1.0
 */
public interface IBaseSaveController<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseController<E, O> {
    /**
     * 新增接口
     *
     * @param inParam 入参
     * @return 响应
     */
    @PostMapping(value = {"${agile.base-service.save:}"})
    default RETURN save(@AgileInParam I inParam) throws Exception {
        genericService().validate(inParam, Default.class, Insert.class);
        E data = ObjectUtil.to(inParam, new TypeReference<>(getEntityClass()));
        genericService().validateEntity(data, Default.class, Insert.class);

        try {
            data.setCreateTime(new Date());
            UserDetails currentUser = SecurityUtil.currentUser();
            if (currentUser instanceof CustomerUserDetails) {
                data.setCreateUser(((CustomerUserDetails) currentUser).id());
            }
        } catch (Exception ignored) {
        }
        AgileReturn.add(Constant.ResponseAbout.RESULT, toSingleOutVo(genericService().saveData(data)));
        return RETURN.SUCCESS;
    }
}
