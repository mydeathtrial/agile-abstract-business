package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.controller.BaseBusinessService;
import cloud.agileframework.abstractbusiness.pojo.EntityExistsException;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.common.DataException;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.common.util.collection.TreeUtil;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.data.common.dao.BaseDao;
import cloud.agileframework.dictionary.util.ConvertDicAnnotation;
import cloud.agileframework.dictionary.util.TranslateException;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

/**
 * @author 佟盟
 * 日期 2020/8/00021 9:49
 * 描述 基础服务，提供增删改查
 * @version 1.0
 * @since 1.0
 */
public class GenericService {
    private final BaseDao dao;
    private final ISecurityService security;

    public GenericService(BaseDao dao, ISecurityService security) {
        this.dao = dao;
        this.security = security;
    }

    public static <A, B> List<B> toList(List<A> data, Class<B> type) {
        final TypeReference<List<B>> toClass = new TypeReference<List<B>>() {
        };
        ParameterizedType parameterizedType = (ParameterizedType) toClass.getType();
        parameterizedType = TypeUtils.parameterizeWithOwner(parameterizedType.getOwnerType(),
                (Class<?>) parameterizedType.getRawType(),
                type);
        toClass.replace(parameterizedType);

        List<B> result = ObjectUtil.to(data, toClass);
        ConvertDicAnnotation.cover(result);
        return result;
    }

    public static <A, B> B to(A data, Class<B> type) {
        if (data == null) {
            return null;
        }
        final TypeReference<B> typeReference = new TypeReference<>(type);
        B o = ObjectUtil.to(data, typeReference);
        if (o == null) {
            throw new DataException("数据类型转换失败" + data.getClass() + "无法与" + type + "类型之间完成互转");
        }
        ConvertDicAnnotation.cover(o);
        return o;
    }

    public BaseDao dao() {
        return dao;
    }

    public ISecurityService security() {
        return security;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public <E extends IBaseEntity> void saveDataWithNewTransaction(List<E> data) throws NoSuchFieldException, IllegalAccessException, DataException {
        saveData(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public <E extends IBaseEntity> void saveData(List<E> data) {
        for (E node : data) {
            Object v = dao().getId(node);
            if (StringUtils.isBlank(String.valueOf(v))) {
                dao().setId(node, null);
            }
            if (node.getCreateUser() == null) {
                //赋予创建用户
                node.setCreateUser(security().currentUser());
            }
        }

        dao().save(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public <E extends IBaseEntity> E saveData(E data) {
        Object v = dao().getId(data);
        if (StringUtils.isBlank(String.valueOf(v))) {
            dao().setId(data, null);
        }
        if (data.getCreateUser() == null) {
            //赋予创建用户
            data.setCreateUser(security().currentUser());
        }

        return dao().saveAndReturn(data);

    }

    @Transactional(rollbackFor = Exception.class)
    public <E extends IBaseEntity> boolean deleteById(Object id, Class<E> javaType) {
        return dao().deleteById(javaType, id);
    }

    @Transactional(rollbackFor = Exception.class)
    public <E extends IBaseEntity> void deleteByIds(List<String> id, Class<E> javaType) {
        dao().deleteInBatch(javaType, id);
    }

    @Transactional(rollbackFor = Exception.class)
    public <E extends IBaseEntity> E updateData(E data) {
        //赋予创建用户
        if (data != null && data.getUpdateUser() == null) {
            data.setUpdateUser(security().currentUser());
        }
        return dao().updateOfNotNull(data);
    }

    public <I extends BaseInParamVo, E extends IBaseEntity> List<E> list(Class<E> entityClass, I inParam) {
        E data = ObjectUtil.to(inParam, new TypeReference<>(entityClass));
        if (data == null) {
            return dao().findAllByClass(entityClass, inParam.sort());
        }
        return dao().findAll(data, inParam.sort());
    }

    public <I extends BaseInParamVo, V> List<V> list(Class<V> outVoClass, I inParam, String sql) {
        if (inParam == null) {
            return dao().findBySQL(sql, outVoClass, Maps.newHashMap());
        }
        return dao().findBySQL(sql, outVoClass, inParam);
    }

    public <I extends BaseInParamVo, J, T> List<T> list(Class<J> queryBy, Class<T> outBy, I inParam) {
        J data = ObjectUtil.to(inParam, new TypeReference<>(queryBy));
        List<J> list;
        if (data == null) {
            list = dao().findAllByClass(queryBy, inParam.sort());
        } else {
            list = dao().findAll(data, inParam.sort());
        }

        return toList(list, outBy);
    }

    public <I extends BaseInParamVo, J, T> List<T> list(Class<J> queryBy, Class<T> outBy, I inParam, String sql) {
        List<J> list;
        if (inParam == null) {
            list = dao().findBySQL(sql, queryBy, Maps.newHashMap());
        } else {
            list = dao().findBySQL(sql, queryBy, inParam);
        }
        return toList(list, outBy);
    }

    public <E extends IBaseEntity> E queryById(Class<E> data, Object id) {
        return dao().findOne(data, id);
    }

    public <V, E extends IBaseEntity> V queryOne(Class<V> outVoClass, Class<E> doClass, String id, String sql, String idPlaceholder) {
        HashMap<Object, Object> params = Maps.newHashMap();
        params.put(idPlaceholder, id);
        E data = dao().findOne(sql, doClass, params);
        return ObjectUtil.to(data, new TypeReference<>(outVoClass));
    }

    public <T> T queryOne(Class<T> outClass, String id, String sql, String idPlaceholder) {
        HashMap<Object, Object> params = Maps.newHashMap();
        params.put(idPlaceholder, id);
        return dao().findOne(sql, outClass, params);
    }

    public <I extends BaseInParamVo, V> Page<V> page(Class<V> outVoClass, I inParam, String sql) {
        PageRequest pageRequest = PageRequest.of(
                inParam.getPageNum() - 1,
                inParam.getPageSize());

        return dao().pageBySQL(inParam.parseOrder(sql), pageRequest, outVoClass, inParam);
    }

    public <I extends BaseInParamVo, J, T> Page<T> page(Class<J> queryBy, Class<T> outBy, I inParam, String sql) {
        PageRequest pageRequest = PageRequest.of(
                inParam.getPageNum() - 1,
                inParam.getPageSize());

        Page<J> page = dao().pageBySQL(inParam.parseOrder(sql), pageRequest, queryBy, inParam);
        return new PageImpl<>(toList(page.getContent(), outBy), page.getPageable(), page.getTotalElements());
    }

    public <I extends BaseInParamVo, E extends IBaseEntity> Page<E> page(Class<E> entityClass, I inParam) {
        E data = ObjectUtil.to(inParam, new TypeReference<>(entityClass));

        PageRequest pageRequest = PageRequest.of(
                inParam.getPageNum() - 1,
                inParam.getPageSize(),
                inParam.sort());

        if (data == null) {
            return dao().pageByClass(entityClass, pageRequest);
        }
        return dao().page(data, pageRequest);
    }

    public <I extends BaseInParamVo, J, T> Page<T> page(Class<J> queryBy, Class<T> outBy, I inParam) {
        J data = ObjectUtil.to(inParam, new TypeReference<>(queryBy));

        PageRequest pageRequest = PageRequest.of(
                inParam.getPageNum() - 1,
                inParam.getPageSize(),
                inParam.sort());

        Page<J> page;
        if (data == null) {
            page = dao().pageByClass(queryBy, pageRequest);
        } else {
            page = dao().page(data, pageRequest);
        }
        return new PageImpl<>(toList(page.getContent(), outBy), page.getPageable(), page.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public <E extends IBaseEntity> void clean(Class<E> data) {
        dao().deleteAllInBatch(data);
    }

    public <I extends Serializable, A extends TreeBase<I, A>> SortedSet<A> tree(List<A> all, I rootParentId) {
        return TreeUtil.createTree(all,
                rootParentId);

    }

    /**
     * 参数验证
     *
     * @param pojo   实体
     * @param groups 场景
     * @throws AgileArgumentException 验证失败
     */
    public void validate(Object pojo, Class<?>... groups) throws AgileArgumentException {
        List<ValidateMsg> list = ValidateUtil.validate(pojo, groups);
        if (!list.isEmpty()) {
            throw new AgileArgumentException(list);
        }
    }

    /**
     * 验证实体
     *
     * @param pojo   数据库数据
     * @param groups 场景
     * @throws AgileArgumentException 验证失败
     */
    public void validateEntity(Object pojo, Class<?>... groups) throws AgileArgumentException {
        List<ValidateMsg> list = BaseBusinessService.toValidateMessages(pojo, groups);
        if (!list.isEmpty()) {
            throw new AgileArgumentException(list);
        }
    }

    /**
     * 验证实体
     *
     * @param pojo 数据库数据
     * @throws EntityExistsException 数据不存在异常
     */
    public <E extends IBaseEntity> void validateEntityExists(E pojo) throws EntityExistsException, TranslateException {

        Dao dao = BeanUtil.getBean(Dao.class);
        Object id = null;
        if (pojo != null) {
            id = dao.getId(pojo);
        }
        if (id == null) {
            return;
        }
        List<E> old = dao.findAllByArrayId((Class<E>) pojo.getClass(), id);
        if (old == null || old.isEmpty()) {
            throw new EntityExistsException(id + "");
        }
    }
}
