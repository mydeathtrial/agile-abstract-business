package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.dictionary.DictionaryDataBase;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.security.filter.login.CustomerUserDetails;
import cloud.agileframework.spring.util.SecurityUtil;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.groups.Default;
import java.util.Date;

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
    @Validate(nullable = false)
    @Mapping(value = {"${agile.base-service.update:}"}, method = RequestMethod.PUT)
    default RETURN update() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        if(inParam==null){
            throw new AgileArgumentException("入参中没提取到有效数据");
        }
        update(inParam);
        return RETURN.SUCCESS;
    }

    default void update(I inParam) throws Exception {
        inParam.validate(Default.class, Update.class);
        E data = inParam.to(getEntityClass());
        data.validate(Default.class, Update.class);
        genericService().validateEntityExists(data);
        
        try {
            data.setUpdateTime(new Date());
            UserDetails currentUser = SecurityUtil.currentUser();
            if (currentUser instanceof CustomerUserDetails) {
                data.setUpdateUser(((CustomerUserDetails) currentUser).id());
            }
        } catch (Exception ignored) {
        }

        if (dataManager() != null) {
            dataManager().sync().updateOfNotNull((DictionaryDataBase) data);
        } else {
            genericService().updateData(data);
        }
    }
}
