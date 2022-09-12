package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.data.common.dao.BaseDao;
import cloud.agileframework.dictionary.AbstractDictionaryDataManager;
import cloud.agileframework.dictionary.DictionaryDataBase;
import cloud.agileframework.dictionary.util.DictionaryUtil;
import cloud.agileframework.spring.util.BeanUtil;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:56
 * 描述 基础控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> {

    default BaseDao dao() {
        return genericService().dao();
    }

    default GenericService genericService() {
        return BeanUtil.getBean(GenericService.class);
    }

    /**
     * 取当前控制器服务的实体类
     *
     * @return 实体类
     */
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
    default List<O> toOutVo(List<E> list) {
        return list.stream().map(this::toSingleOutVo).collect(Collectors.toList());
    }

    /**
     * 单个对象转换成OutVo类型
     *
     * @param n 单个对象
     * @return 返回值
     */
    default O toSingleOutVo(E n) {
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

    default AbstractDictionaryDataManager<DictionaryDataBase> dataManager() {
        if (!DictionaryDataBase.class.isAssignableFrom(getEntityClass())) {
            return null;
        }
        TypeReference<AbstractDictionaryDataManager<DictionaryDataBase>> typeReference = new TypeReference<AbstractDictionaryDataManager<DictionaryDataBase>>() {
        };
        ParameterizedType parameterizedType = (ParameterizedType) typeReference.getType();
        parameterizedType = TypeUtils.parameterizeWithOwner(parameterizedType.getOwnerType(),
                (Class<?>) parameterizedType.getRawType(),
                getEntityClass());
        typeReference.replace(parameterizedType);
        return (AbstractDictionaryDataManager<DictionaryDataBase>) BeanUtil.getApplicationContext().getBeanProvider(ResolvableType.forType(typeReference.getType())).getIfUnique();
    }
}
