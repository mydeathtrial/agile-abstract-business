package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.dictionary.DictionaryDataBase;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.group.Insert;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.groups.Default;

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
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.save:}"}, method = RequestMethod.POST)
    default RETURN save() {
        I inParam = AgileParam.getInParam(getInVoClass());
        E data = AgileParam.getInParam(getEntityClass());
        validate(inParam, Default.class, Insert.class);
        validateEntity(data, Default.class, Insert.class);

        if (dataManager() != null) {
            dataManager().sync().add((DictionaryDataBase) data);
            return RETURN.SUCCESS;
        }
        AgileReturn.add(Constant.ResponseAbout.RESULT, toSingleOutVo(saveData(data)));
        return RETURN.SUCCESS;
    }
}
