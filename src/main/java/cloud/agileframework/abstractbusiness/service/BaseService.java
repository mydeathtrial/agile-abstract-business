package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.collection.SortInfo;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.common.util.collection.TreeUtil;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.data.common.dao.BaseDao;
import cloud.agileframework.mvc.annotation.NotAPI;
import cloud.agileframework.spring.util.BeanUtil;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import static cloud.agileframework.abstractbusiness.service.IBaseDeleteService.ID;

/**
 * @author 佟盟
 * 日期 2020/8/00021 9:49
 * 描述 基础服务，提供增删改查
 * @version 1.0
 * @since 1.0
 */
public interface BaseService {

    @NotAPI
    default BaseDao dao() {
        return BeanUtil.getBean(BaseDao.class);
    }

    @NotAPI
    default ISecurityService security() {
        return BeanUtil.getBean(ISecurityService.class);
    }

    @NotAPI
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    default <A> void saveDataWithNewTransaction(List<A> data) throws NoSuchFieldException, IllegalAccessException {
        saveData(data);
    }

    @NotAPI
    @Transactional(rollbackFor = Exception.class)
    default <A> void saveData(List<A> data) throws NoSuchFieldException, IllegalAccessException {
        for (A node : data) {
            Object v = dao().getId(node);
            if (StringUtils.isBlank(String.valueOf(v))) {
                dao().setId(node, null);
            }
            if (node instanceof IBaseEntity && ((IBaseEntity) node).getCreateUser() == null) {
                //赋予创建用户
                ((IBaseEntity) node).setCreateUser(security().currentUser());
            }
        }

        dao().save(data);
    }

    @NotAPI
    @Transactional(rollbackFor = Exception.class)
    default <A> A saveData(A data) {
        if (data instanceof Class) {
            throw new RuntimeException("not extract data of " + data);
        }

        try {
            Object v = dao().getId(data);
            if (StringUtils.isBlank(String.valueOf(v))) {
                dao().setId(data, null);
            }
            if (data instanceof IBaseEntity && ((IBaseEntity) data).getCreateUser() == null) {
                //赋予创建用户
                ((IBaseEntity) data).setCreateUser(security().currentUser());
            }

            return dao().saveAndReturn(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotAPI
    @Transactional(rollbackFor = Exception.class)
    default boolean deleteById(Object id, Class<?> javaType) {
        return dao().deleteById(javaType, id);
    }

    @NotAPI
    @Transactional(rollbackFor = Exception.class)
    default boolean deleteByIds(List<String> id, Class<?> javaType) {
        try {
            dao().deleteInBatch(javaType, id);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    @NotAPI
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    default <A> A updateData(A data) {
        if (data instanceof Class) {
            throw new RuntimeException("not extract data of " + data);
        }
        //赋予创建用户
        if (data instanceof IBaseEntity && ((IBaseEntity) data).getUpdateUser() == null) {
            ((IBaseEntity) data).setUpdateUser(security().currentUser());
        }
        return dao().updateOfNotNull(data);
    }

    @NotAPI
    default <I extends BaseInParamVo> List<?> list(Class<?> entityClass, I inParam) {
        Object data = ObjectUtil.to(inParam, new TypeReference<>(entityClass));
        if (data == null) {
            return dao().findAllByClass(entityClass, toSort(inParam));
        }
        return dao().findAll(data, toSort(inParam));
    }

    @NotAPI
    default <I extends BaseInParamVo, V> List<V> list(Class<V> outVoClass, I inParam, String sql) {
        if (inParam == null) {
            return dao().findBySQL(sql, outVoClass, Maps.newHashMap());
        }
        return dao().findBySQL(sql, outVoClass, inParam);
    }

    @NotAPI
    default <I extends BaseInParamVo> Sort toSort(I inParam) {
        if (inParam == null) {
            return Sort.unsorted();
        }
        List<SortInfo> sorts = inParam.getSortColumn();

        if (sorts == null) {
            return Sort.unsorted();
        }
        List<Sort.Order> s = sorts.stream().map(sortInfo -> {
            if (sortInfo.isSort()) {
                return Sort.Order.desc(sortInfo.getProperty());
            }
            return Sort.Order.asc(sortInfo.getProperty());
        }).collect(Collectors.toList());
        return Sort.by(s);
    }

    @NotAPI
    default Object queryById(Class<?> data, Object id) {
        return dao().findOne(data, id);
    }

    @NotAPI
    default <V, E> V queryOne(Class<V> outVoClass, Class<E> doClass, String id, String sql) throws NoSuchFieldException {
        HashMap<Object, Object> params = Maps.newHashMap();
        params.put(dao().getIdField(doClass).getName(), id);
        params.put(ID, id);
        return dao().findOne(sql, outVoClass, params);
    }

    @NotAPI
    default <I extends BaseInParamVo, V> Page<V> page(Class<V> outVoClass, I inParam, String sql) {
        PageRequest pageRequest = PageRequest.of(
                inParam.getPageNum() - 1,
                inParam.getPageSize(),
                toSort(inParam));

        return dao().pageBySQL(sql, pageRequest, outVoClass, inParam);
    }

    @NotAPI
    default <I extends BaseInParamVo, E> Page<E> page(Class<E> entityClass, I inParam) {
        E data = ObjectUtil.to(inParam, new TypeReference<>(entityClass));

        PageRequest pageRequest = PageRequest.of(
                inParam.getPageNum() - 1,
                inParam.getPageSize(),
                toSort(inParam));

        if (data == null) {
            return dao().pageByClass(entityClass, pageRequest);
        }
        return dao().page(data, pageRequest);
    }

    @NotAPI
    @Transactional(rollbackFor = Exception.class)
    default void clean(Class<?> data) {
        dao().deleteAllInBatch(data);
    }

    @NotAPI
    default <I extends Serializable, A extends TreeBase<I, A>> SortedSet<A> tree(List<A> all, I rootParentId) {
        return TreeUtil.createTree(all,
                rootParentId,
                null);

    }
}
