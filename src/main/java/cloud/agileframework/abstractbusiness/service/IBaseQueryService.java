package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.PageQuery;
import cloud.agileframework.validate.group.Query;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public interface IBaseQueryService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseService<E, I, O> {
    /**
     * 列表查询
     *
     * @return 列表
     */
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.query:/list}"}, method = RequestMethod.POST)
    default RETURN list() {
        I inParam = AgileParam.getInParam(getInVoClass());
        validate(inParam, Query.class);
        List<?> list = service().list(getEntityClass(), inParam);

        List<O> result = toOutVo(list);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    /**
     * 分页
     *
     * @return 分页
     */
    @Validate(value = "pageNum", nullable = false)
    @Validate(value = "pageSize", nullable = false)
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.page:/{pageNum}/{pageSize}}"}, method = RequestMethod.POST)
    default RETURN page() {
        I inParam = AgileParam.getInParam(getInVoClass());
        validate(inParam, PageQuery.class);
        Page<?> page = service().page(getEntityClass(), inParam);
        PageImpl<?> result = new PageImpl<>(toOutVo(page.getContent()), page.getPageable(), page.getTotalElements());
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    /**
     * 树
     *
     * @return 树
     */
    @SneakyThrows
    @Mapping(value = "${agile.base-service.tree:/tree}", method = {RequestMethod.GET, RequestMethod.POST})
    default <L extends Serializable, P extends TreeBase<L, P>> RETURN tree() {
        I inParam = AgileParam.getInParam(getInVoClass());
        if (!TreeBase.class.isAssignableFrom(getEntityClass())) {
            throw new NoSuchRequestServiceException();
        }
        validate(inParam, Query.class);

        List<?> list = service().list(getEntityClass(), inParam);

        if (!TreeBase.class.isAssignableFrom(getOutVoClass())) {
            throw new RuntimeException("your out vo class must is TreeBase subclass");
        }

        ArrayList<P> result = new ArrayList<>((Collection<? extends P>) toOutVo(list));
        L rootParentId = (L) getOutVoClass().getMethod("rootParentId").invoke(null);
        AgileReturn.add(Constant.ResponseAbout.RESULT, service().tree(result, rootParentId));

        return RETURN.SUCCESS;
    }

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 数据
     */
    @Validate(value = "id", nullable = false)
    @Mapping(value = {"${agile.base-service.queryById:/{id}}"}, method = RequestMethod.GET)
    default RETURN queryById(@AgileInParam("id") String id) {
        final Object result = service().queryById(getEntityClass(), id);
        AgileReturn.add(Constant.ResponseAbout.RESULT, toSingleOutVo(result));
        return RETURN.SUCCESS;
    }
}
