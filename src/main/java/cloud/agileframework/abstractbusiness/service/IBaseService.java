package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.controller.BaseBusinessService;
import cloud.agileframework.abstractbusiness.pojo.EntityExistsException;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.data.common.dao.BaseDao;
import cloud.agileframework.dictionary.util.DictionaryUtil;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.mvc.annotation.NotAPI;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.spring.util.ServletUtil;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:56
 * 描述 基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends BaseService {

    @NotAPI
    default BaseDao dao() {
        return BeanUtil.getBean(Dao.class);
    }

    /**
     * 参数验证
     *
     * @param pojo   实体
     * @param groups 场景
     * @throws AgileArgumentException 验证失败
     */
    @NotAPI
    default void validate(Object pojo, Class<?>... groups) throws AgileArgumentException {
        List<ValidateMsg> list = ValidateUtil.validate(pojo, groups);
        if (!list.isEmpty()) {
            ServletUtil.getCurrentRequest().setAttribute(Constant.RequestAttributeAbout.ATTRIBUTE_ERROR, list);
            throw new AgileArgumentException();
        }
    }

    /**
     * 验证实体
     *
     * @param pojo   数据库数据
     * @param groups 场景
     * @throws AgileArgumentException 验证失败
     */
    @NotAPI
    default void validateEntity(Object pojo, Class<?>... groups) throws AgileArgumentException {
        List<ValidateMsg> list = BaseBusinessService.toValidateMessages(pojo, groups);
        if (!list.isEmpty()) {
            ServletUtil.getCurrentRequest().setAttribute(Constant.RequestAttributeAbout.ATTRIBUTE_ERROR, list);
            throw new AgileArgumentException();
        }
    }

    /**
     * 验证实体
     *
     * @param pojo 数据库数据
     * @throws EntityExistsException 数据不存在异常
     */
    @NotAPI
    default <A> void validateEntityExists(A pojo) throws EntityExistsException, AgileArgumentException {

        Dao dao = BeanUtil.getBean(Dao.class);
        Object id = null;
        if (pojo != null) {
            id = dao.getId(pojo);
        }
        if (pojo == null || id == null) {
            ServletUtil.getCurrentRequest()
                    .setAttribute(Constant.RequestAttributeAbout.ATTRIBUTE_ERROR,
                            Collections.singleton(
                                    new ValidateMsg("主键不允许为空", dao.getIdField(getEntityClass()).getName(), null)
                            )
                    );
            throw new AgileArgumentException();
        }
        List<A> old = dao.findAllByArrayId((Class<A>) pojo.getClass(), id);
        if (old == null || old.isEmpty()) {
            throw new EntityExistsException(id + "");
        }
    }

    /**
     * 取基础服务
     *
     * @return 通用基础服务
     */
    @NotAPI
    default BaseService service() {
        return BeanUtil.getBean(BaseService.class);
    }

    /**
     * 取当前控制器服务的实体类
     *
     * @return 实体类
     */
    @NotAPI
    default Class<E> getEntityClass() {
        Type entityClass = ClassUtil.getGeneric(this.getClass(), IBaseService.class, 0);
        if (entityClass instanceof Class) {
            return (Class<E>) entityClass;
        }
        throw new ClassCastException("当前类作为参数化类型“E”，没找到对应的实体类型");
    }

    /**
     * 获取出参的vo类
     *
     * @return 出参vo类型
     */
    @NotAPI
    default Class<O> getOutVoClass() {
        Type outVo = ClassUtil.getGeneric(this.getClass(), IBaseService.class, 2);
        if (outVo instanceof Class) {
            return (Class<O>) outVo;
        }
        throw new ClassCastException("当前类作为参数化类型“O”，没找到对应的实体类型");
    }

    /**
     * 获取出参的vo类
     *
     * @return 出参vo类型
     */
    @NotAPI
    default Class<I> getInVoClass() {
        Type inVo = ClassUtil.getGeneric(this.getClass(), IBaseService.class, 1);
        if (inVo instanceof Class) {
            return (Class<I>) inVo;
        }
        throw new ClassCastException("当前类作为参数化类型“I”，没找到对应的实体类型");
    }


    /**
     * 集合转换成OutVo
     *
     * @param list 响应数据集合
     * @return OutVo类型响应数据
     */
    @NotAPI
    default List<O> toOutVo(List<?> list) {
        return list.stream().map(this::toSingleOutVo).collect(Collectors.toList());
    }

    /**
     * 单个对象转换成OutVo类型
     *
     * @param n 单个对象
     * @return 返回值
     */
    @NotAPI
    default O toSingleOutVo(Object n) {
        if (n == null) {
            return null;
        }
        final TypeReference<O> typeReference = new TypeReference<>(getOutVoClass());
        O o = ObjectUtil.to(n, typeReference);
        if (o == null) {
            return ClassUtil.newInstance(getOutVoClass());
        }
        DictionaryUtil.cover(o);
        return o;
    }
}
