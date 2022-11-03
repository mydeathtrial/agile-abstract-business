package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.data.common.dao.BaseDao;
import cloud.agileframework.dictionary.AbstractDictionaryDataManager;
import cloud.agileframework.dictionary.DictionaryDataBase;
import cloud.agileframework.spring.util.BeanUtil;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
        return GenericService.INSTANCE;
    }
    
    @Autowired
    default void setGenericService(GenericService genericService){
        GenericService.INSTANCE =  genericService;
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
