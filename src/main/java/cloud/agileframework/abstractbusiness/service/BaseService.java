package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.collection.SortInfo;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.common.util.collection.TreeUtil;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.common.util.string.StringUtil;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.security.filter.login.CustomerUserDetails;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.spring.util.SecurityUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 佟盟
 * 日期 2020/8/00021 9:49
 * 描述 基础服务，提供增删改查
 * @version 1.0
 * @since 1.0
 */
@Service
public class BaseService {

    @Autowired
    private Dao dao;

    @Transactional(rollbackFor = Exception.class)
    public void save(String model) throws NoSuchRequestServiceException {
        dataAsParam(model, this::saveData);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveData(Object data) {
        if (data instanceof Class) {
            throw new RuntimeException("not extract data of " + data);
        }
        try {
            Object v = dao.getId(data);
            if (StringUtils.isBlank(String.valueOf(v))) {
                dao.setId(data, null);
            }
            if (data instanceof IBaseEntity && ((IBaseEntity) data).getCreateUserId() == null) {
                //赋予创建用户
                UserDetails user = SecurityUtil.currentUser();
                if (user instanceof CustomerUserDetails) {
                    ((IBaseEntity) data).setCreateUserId(((CustomerUserDetails) user).id());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.save(data);
        return true;
    }


    public static <T> T dataAsParam(String model, Function<Object, T> function) throws NoSuchRequestServiceException {
        return typeAsParam(model, javaType -> {
            Object data = AgileParam.getInParam(javaType);
            if (data == null) {
                return function.apply(javaType);
            }
            return function.apply(data);
        });
    }

    private static <T> T typeAsParam(String model, Function<Class<?>, T> function) throws NoSuchRequestServiceException {
        Optional<EntityType<?>> entityType = getEntityType(model);
        if (entityType.isPresent()) {
            Class<?> javaType = entityType.get().getJavaType();
            return function.apply(javaType);
        }
        throw new NoSuchRequestServiceException();
    }

    private static void typeAsParam2(String model, Consumer<Class<?>> consumer) throws NoSuchRequestServiceException {
        Optional<EntityType<?>> entityType = getEntityType(model);
        if (entityType.isPresent()) {
            Class<?> javaType = entityType.get().getJavaType();
            consumer.accept(javaType);
            return;
        }
        throw new NoSuchRequestServiceException();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String model, Object id) throws NoSuchRequestServiceException {
        typeAsParam(model, javaType -> deleteById(id, javaType));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Object id, Class<?> javaType) {
        return dao.deleteById(javaType, id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(@AgileInParam List<String> id, String model) throws NoSuchRequestServiceException {
        typeAsParam(model, javaType -> deleteByIds(id, javaType));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(List<String> id, Class<?> javaType) {
        dao.deleteInBatch(javaType, id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String model) throws NoSuchRequestServiceException {
        dataAsParam(model, this::updateData);
    }

    @Transactional(rollbackFor = Exception.class)
    public Object updateData(Object data) {
        if (data instanceof Class) {
            throw new RuntimeException("not extract data of " + data);
        }
        //赋予创建用户
        if (data instanceof IBaseEntity && ((IBaseEntity) data).getUpdateUserId() == null) {
            UserDetails user = SecurityUtil.currentUser();
            if (user instanceof CustomerUserDetails) {
                ((IBaseEntity) data).setUpdateUserId(((CustomerUserDetails) user).id());
            }
        }
        return dao.saveOrUpdate(data);
    }

    public <I extends BaseInParamVo> List<Object> list(String model, I inParam) throws NoSuchRequestServiceException {
        return dataAsParam(model, data -> {
            if (data instanceof Class) {
                return dao.findAllByClass((Class) data, toSort(inParam));
            }
            return dao.findAll(data, toSort(inParam));
        });
    }

    public <I extends BaseInParamVo> List<?> list(Class<?> entityClass, I inParam) {
        Object data = ObjectUtil.to(inParam, new TypeReference<>(entityClass));
        if (data == null) {
            return dao.findAllByClass(entityClass, toSort(inParam));
        }
        return dao.findAll(data, toSort(inParam));
    }

    <I extends BaseInParamVo> Sort toSort(I inParam) {
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

    public Object queryById(String model, Object id) throws NoSuchRequestServiceException {
        return typeAsParam(model, data -> queryById(data, id));
    }

    public Object queryById(Class<?> data, Object id) {
        return dao.findOne(data, id);
    }

    public <I extends BaseInParamVo> Page<Object> page(String model, I inParam) throws NoSuchRequestServiceException {
        return typeAsParam(model, data -> {
            PageRequest pageRequest = PageRequest.of(
                    inParam.getPageNum() - 1,
                    inParam.getPageSize(),
                    toSort(inParam));

            Object dto = AgileParam.getInParam(data);
            if (dto != null) {
                return dao.page(dto, pageRequest);
            }
            return dao.page(data, pageRequest);
        });
    }


    public <I extends BaseInParamVo> Page<?> page(Class<?> entityClass, I inParam) {
        Object data = ObjectUtil.to(inParam, new TypeReference<>(entityClass));

        PageRequest pageRequest = PageRequest.of(
                inParam.getPageNum() - 1,
                inParam.getPageSize(),
                toSort(inParam));

        if (data == null) {
            return dao.page(entityClass, pageRequest);
        }
        return dao.page(data, pageRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    public void clean(String model) throws NoSuchRequestServiceException {
        typeAsParam2(model, this::clean);
    }

    @Transactional(rollbackFor = Exception.class)
    public void clean(Class<?> data) {
        dao.deleteAllInBatch(data);
    }

    @Value("${agile.base-service.tree.root-id:-1}")
    private String root;

    public <A extends TreeBase<String, A>> Object tree(String model) throws NoSuchRequestServiceException {
        return dataAsParam(model, data -> {
            List<A> all;
            if (data instanceof Class && TreeBase.class.isAssignableFrom((Class<?>) data)) {
                all = new ArrayList(dao.findAllByClass((Class<?>) data));
            } else if (data != null && TreeBase.class.isAssignableFrom(data.getClass())) {
                all = new ArrayList(dao.findAll(data));
            } else {
                all = Lists.newArrayList();
            }

            return TreeUtil.createTree(all,
                    root,
                    null);
        });
    }

    public <A extends TreeBase<String, A>> SortedSet<A> tree(List<A> all) {
        return TreeUtil.createTree(all,
                root,
                null);

    }

    /**
     * 根据访问的模型，遍历查找对应的orm类，用于后续处理
     *
     * @param model 模型名字
     * @return orm类
     */
    private static Optional<EntityType<?>> getEntityType(String model) {
        Dao dao = BeanUtil.getBean(Dao.class);
        if (dao == null) {
            throw new RuntimeException("not found Dao bean");
        }
        return dao.getEntityManager().getEntityManagerFactory()
                .getMetamodel()
                .getEntities()
                .stream()
                .filter(n -> n.getName().equalsIgnoreCase(StringUtil.toUpperName(model)))
                .findFirst();
    }
}
