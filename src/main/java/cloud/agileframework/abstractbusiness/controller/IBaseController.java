package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.abstractbusiness.service.BaseService;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.spring.util.ServletUtil;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;

import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:56
 * 描述 基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseController<E extends IBaseEntity, O extends IBaseOutParamVo> {
    /**
     * 取基础服务
     *
     * @return 通用基础服务
     */
    BaseService service();

    /**
     * 取当前控制器服务的实体类
     *
     * @return 实体类
     */
    Class<E> getEntityClass();

    /**
     * 获取出参的vo类
     *
     * @return 出参vo类型
     */
    Class<O> getOutVoClass();

    /**
     * 参数验证
     *
     * @param pojo   实体
     * @param groups 场景
     * @throws AgileArgumentException 验证失败
     */
    default void validate(Object pojo, Class<?>... groups) throws AgileArgumentException {
        List<ValidateMsg> list = ValidateUtil.validate(pojo, groups);
        if (!list.isEmpty()) {
            ServletUtil.getCurrentRequest().setAttribute(Constant.RequestAttributeAbout.ATTRIBUTE_ERROR, list);
            throw new AgileArgumentException();
        }
    }
}
