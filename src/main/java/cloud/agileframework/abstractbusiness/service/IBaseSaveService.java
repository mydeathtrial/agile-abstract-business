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
import cloud.agileframework.spring.util.BeanUtil;
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
        if (inParam == null) {
            throw new AgileArgumentException("入参中没提取到有效数据");
        }
        IBaseSaveService<E, I, O> service = (IBaseSaveService<E, I, O>) BeanUtil.getApplicationContext().getBean(getClass());
        service.save(inParam);
        inParam.validate(Default.class, Insert.class);
        return RETURN.SUCCESS;
    }

    /**
     * 保存
     *
     * @param inParam 入参
     * @throws Exception 异常
     */
    default E save(I inParam) throws Exception {
        E data = inParam.to(getEntityClass());
        data.validate(Default.class, Insert.class);
        IBaseSaveService<E, I, O> service = (IBaseSaveService<E, I, O>) BeanUtil.getApplicationContext().getBean(getClass());
        return service.saveDo(data);
    }

    /**
     * 保存
     *
     * @param data 数据
     * @throws Exception 异常
     */
    default E saveDo(E data) throws Exception {
        try {
            data.setCreateTime(new Date());
            UserDetails currentUser = SecurityUtil.currentUser();
            if (currentUser instanceof CustomerUserDetails) {
                data.setCreateUser(((CustomerUserDetails) currentUser).id());
            }
        } catch (Exception ignored) {
        }

        if (dataManager() != null) {
            return (E) dataManager().sync().add((DictionaryDataBase) data);
        } else {
            return genericService().saveData(data);
        }
    }
}
