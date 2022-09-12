package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.PageQuery;
import cloud.agileframework.validate.group.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 基础查询控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseQueryController<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseController<E, O> {
    /**
     * 列表查询
     *
     * @param inParam 入参
     * @return 列表
     */
    @PostMapping(value = {"${agile.base-service.query:/list}"})
    default RETURN list(@AgileInParam I inParam) throws Exception {
        genericService().validate(inParam, Query.class);
        String sql = listSql();
        List<E> list;
        if (sql != null) {
            list = genericService().list(getEntityClass(), inParam, sql);
        } else {
            list = genericService().list(getEntityClass(), inParam);
        }

        List<O> result = toOutVo(list);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    /**
     * 分页
     *
     * @param inParam 入参
     * @return 分页
     */
    @Validate(value = "pageNum", nullable = false)
    @Validate(value = "pageSize", nullable = false)
    @PostMapping(value = {"${agile.base-service.page:/{pageNum}/{pageSize}}"})
    default RETURN page(@AgileInParam I inParam) throws Exception {
        genericService().validate(inParam, PageQuery.class);
        String sql = listSql();
        Page<E> page;
        if (sql != null) {
            page = genericService().page(getEntityClass(), inParam, sql);
        } else {
            page = genericService().page(getEntityClass(), inParam);
        }
        PageImpl<?> result = new PageImpl<>(toOutVo(page.getContent()), page.getPageable(), page.getTotalElements());
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    /**
     * 树
     *
     * @param inParam 入参
     * @return 树
     */
    @RequestMapping(value = "${agile.base-service.tree:/tree}", method = {RequestMethod.GET, RequestMethod.POST})
    default <L extends Serializable, P extends TreeBase<L, P>> RETURN tree(@AgileInParam I inParam) throws Exception {
        if (!TreeBase.class.isAssignableFrom(getEntityClass())) {
            throw new NoSuchRequestServiceException();
        }
        genericService().validate(inParam, Query.class);

        List<E> list = genericService().list(getEntityClass(), inParam);

        if (!TreeBase.class.isAssignableFrom(getOutVoClass())) {
            throw new IllegalAccessException("your out vo class must is TreeBase subclass");
        }

        ArrayList<P> result = new ArrayList<>((Collection<? extends P>) toOutVo(list));
        L rootParentId = (L) getOutVoClass().getMethod("rootParentId").invoke(null);
        AgileReturn.add(Constant.ResponseAbout.RESULT, genericService().tree(result, rootParentId));

        return RETURN.SUCCESS;
    }

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 数据
     */
    @Validate(value = "id", nullable = false)
    @GetMapping(value = {"${agile.base-service.queryById:/{id}}"})
    default RETURN queryById(@AgileInParam("id") String id) throws Exception {
        O result;
        if (detailSql() == null) {
            E data = genericService().queryById(getEntityClass(), id);
            result = toSingleOutVo(data);
        } else {
            result = genericService().queryOne(getOutVoClass(), getEntityClass(), id, detailSql(), "id");
        }
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default String listSql() {
        return null;
    }

    default String detailSql() {
        return null;
    }

}
