package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.dictionary.DictionaryDataBase;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.security.filter.login.CustomerUserDetails;
import cloud.agileframework.spring.util.SecurityUtil;
import cloud.agileframework.validate.group.Insert;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.groups.Default;
import java.util.Date;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 新增基础控制去
 * @version 1.0
 * @since 1.0
 */
public interface IBaseSaveService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseService<E, I, O> {
    /**
     * 新增接口
     *
     * @return 响应
     */
    @Mapping(value = {"${agile.base-service.save:}"}, method = RequestMethod.POST)
    default RETURN save() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        save(inParam);
        return RETURN.SUCCESS;
    }

    default void save(I inParam) throws Exception {
        E data = ObjectUtil.to(inParam, new TypeReference<>(getEntityClass()));
        genericService().validate(inParam, Default.class, Insert.class);
        genericService().validateEntity(data, Default.class, Insert.class);

        try {
            data.setCreateTime(new Date());
            UserDetails currentUser = SecurityUtil.currentUser();
            if (currentUser instanceof CustomerUserDetails) {
                data.setCreateUser(((CustomerUserDetails) currentUser).id());
            }
        } catch (Exception ignored) {
        }

        if (dataManager() != null) {
            dataManager().sync().add((DictionaryDataBase) data);
        } else {
            genericService().saveData(data);
        }
    }
}
