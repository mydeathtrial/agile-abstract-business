package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.entity.BaseEntity;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseOutParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.PageQuery;
import cloud.agileframework.validate.group.Query;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 基础查询控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseQueryController<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseController<E,O> {
    /**
     * 列表查询
     *
     * @param inParam 入参
     * @return 列表
     */
    @SneakyThrows
    @PostMapping(value = {"${agile.base-service.query:/list}"})
    default RETURN list(@AgileInParam I inParam) {
        validate(inParam, Query.class);
        List<?> list = service().list(getEntityClass(), inParam);

        List<Object> result = toOutVo(list);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default List<Object> toOutVo(List<?> list) {
        return list.stream().map(this::toSingleOutVo).collect(Collectors.toList());
    }

    default Object toSingleOutVo(Object n) {
        final TypeReference<?> typeReference = new TypeReference<>(getOutVoClass());
        Object o = ObjectUtil.to(n, typeReference);
        if (o == null) {
            return ClassUtil.newInstance(getOutVoClass());
        }
        return o;
    }

    /**
     * 分页
     *
     * @param inParam 入参
     * @return 分页
     */
    @Validate(value = "pageNum", nullable = false)
    @Validate(value = "pageSize", nullable = false)
    @SneakyThrows
    @PostMapping(value = {"${agile.base-service.page:/{pageNum}/{pageSize}}"})
    default RETURN page(@AgileInParam I inParam) {
        validate(inParam, PageQuery.class);
        Page<?> page = service().page(getEntityClass(), inParam);
        PageImpl<?> result = new PageImpl<>(toOutVo(page.getContent()),page.getPageable(),page.getTotalElements());
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    /**
     * 树
     *
     * @param inParam 入参
     * @return 树
     */
    @SneakyThrows
    @RequestMapping(value = "${agile.base-service.tree:/tree}", method = {RequestMethod.GET, RequestMethod.POST})
    default RETURN tree(@AgileInParam I inParam) {
        if (!TreeBase.class.isAssignableFrom(getEntityClass())){
            throw new NoSuchRequestServiceException();
        }
        validate(inParam, Query.class);

        List<?> list = service().list(getEntityClass(), inParam);

        if(!TreeBase.class.isAssignableFrom(getOutVoClass())){
            throw new RuntimeException("your out vo class must is TreeBase subclass");
        }

        List<?> result = toOutVo(list);
        AgileReturn.add(Constant.ResponseAbout.RESULT, service().tree((List<? extends TreeBase>) result));
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
    default RETURN queryById(@AgileInParam("id") String id) {
        final Object result = service().queryById(getEntityClass(), id);
        AgileReturn.add(Constant.ResponseAbout.RESULT, toSingleOutVo(result));
        return RETURN.SUCCESS;
    }
}
