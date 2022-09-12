package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.EntityExistsException;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.dictionary.util.DictionaryUtil;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.PageQuery;
import cloud.agileframework.validate.group.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 基础查询控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseQueryService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseService<E, I, O> {
    /**
     * 列表查询
     *
     * @return 列表
     */
    @Mapping(value = {"${agile.base-service.query:/list}"}, method = RequestMethod.POST)
    default RETURN list() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        List<O> result = list(inParam);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default List<O> list(I inParam) throws Exception {
        genericService().validate(inParam, Query.class);
        String sql = parseOrder(inParam, listSql());
        List<E> list;
        if (sql != null) {
            list = genericService().list(getEntityClass(), inParam, sql);
        } else {
            list = genericService().list(getEntityClass(), inParam);
        }
        List<O> result = toOutVo(list);
        for (O vo : result) {
            handingListVo(vo);
        }
        return result;
    }

    /**
     * 分页
     *
     * @return 分页
     */
    @Validate(beanClass = BaseInParamVo.class)
    @Mapping(value = {"${agile.base-service.page:/{pageNum}/{pageSize}}"}, method = RequestMethod.POST)
    default RETURN page() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        PageImpl<O> result = page(inParam);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default PageImpl<O> page(I inParam) throws Exception {
        genericService().validate(inParam, PageQuery.class);
        String sql = parseOrder(inParam, listSql());
        Page<E> page;
        if (sql != null) {
            page = genericService().page(getEntityClass(), inParam, sql);
        } else {
            page = genericService().page(getEntityClass(), inParam);
        }
        PageImpl<O> result = new PageImpl<>(toOutVo(page.getContent()), page.getPageable(), page.getTotalElements());
        for (O vo : result.getContent()) {
            handingListVo(vo);
        }
        return result;
    }

    /**
     * 树
     *
     * @return 树
     */
    @Mapping(value = "${agile.base-service.tree:/tree}", method = {RequestMethod.GET, RequestMethod.POST})
    default <L extends Serializable, P extends TreeBase<L, P>> RETURN tree() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        SortedSet<P> result = tree(inParam);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default <L extends Serializable, P extends TreeBase<L, P>> SortedSet<P> tree(I inParam) throws Exception {
        if (!TreeBase.class.isAssignableFrom(getEntityClass())) {
            throw new NoSuchRequestServiceException();
        }
        genericService().validate(inParam, Query.class);

        List<E> list = genericService().list(getEntityClass(), inParam);

        if (!TreeBase.class.isAssignableFrom(getOutVoClass())) {
            throw new AgileArgumentException("如果是树形结构查询，OutVo必须继承于TreeBase");
        }

        ArrayList<P> result = new ArrayList<>((Collection<? extends P>) toOutVo(list));
        L rootParentId = (L) getOutVoClass().getMethod("rootParentId").invoke(null);
        return genericService().tree(result, rootParentId);
    }

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 数据
     */
    @Validate(value = "id", nullable = false)
    @Mapping(value = {"${agile.base-service.queryById:/{id}}"}, method = RequestMethod.GET)
    default RETURN queryById(@AgileInParam("id") String id) throws Exception {
        O result = queryOne(id);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default O queryOne(String id) throws Exception {
        O result;

        if (dataManager() != null) {
            result = toSingleOutVo((E) DictionaryUtil.findById(dataManager().dataSource(), id));
        } else if (detailSql() == null) {
            result = toSingleOutVo(genericService().queryById(getEntityClass(), id));
        } else {
            result = genericService().queryOne(getOutVoClass(), getEntityClass(), id, detailSql(), "id");
        }
        if (result == null) {
            throw new EntityExistsException(id);
        }
        handingDetailVo(result);
        return result;
    }

    default String listSql() {
        return null;
    }

    default String detailSql() {
        return null;
    }

    default void handingListVo(O vo) throws Exception {
    }

    default void handingDetailVo(O vo) throws Exception {
    }

    /**
     * 处理排序sql
     *
     * @param inParam 入参
     * @param sql     sql语句
     * @return 处理完排序的sql
     */
    default String parseOrder(BaseInParamVo inParam, String sql) {
        if (inParam == null) {
            return sql;
        }
        return inParam.parseOrder(sql);
    }
}
