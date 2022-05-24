package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.collection.SortInfo;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.dictionary.util.DictionaryUtil;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.annotation.NotAPI;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.PageQuery;
import cloud.agileframework.validate.group.Query;
import com.google.common.collect.Lists;
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
    default RETURN list() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        validate(inParam, Query.class);
        String sql = IBaseQueryService.parseOrder(inParam, listSql());
        List<O> result;
        if (sql != null) {
            result = list(getOutVoClass(), inParam, sql);
        } else {
            List<?> list = list(getEntityClass(), inParam);
            result = toOutVo(list);
        }

        for (O vo : result) {
            handingListVo(vo);
        }
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    /**
     * 分页
     *
     * @return 分页
     */
    @Validate(beanClass = BaseInParamVo.class)
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.page:/{pageNum}/{pageSize}}"}, method = RequestMethod.POST)
    default RETURN page() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        validate(inParam, PageQuery.class);
        String sql = IBaseQueryService.parseOrder(inParam, listSql());
        Page<?> page;
        if (sql != null) {
            page = page(getOutVoClass(), inParam, sql);
        } else {
            page = page(getEntityClass(), inParam);
        }
        PageImpl<O> result = new PageImpl<>(toOutVo(page.getContent()), page.getPageable(), page.getTotalElements());
        for (O vo : result.getContent()) {
            handingListVo(vo);
        }
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
    default <L extends Serializable, P extends TreeBase<L, P>> RETURN tree() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        if (!TreeBase.class.isAssignableFrom(getEntityClass())) {
            throw new NoSuchRequestServiceException();
        }
        validate(inParam, Query.class);

        List<?> list = list(getEntityClass(), inParam);

        if (!TreeBase.class.isAssignableFrom(getOutVoClass())) {
            throw new AgileArgumentException("如果是树形结构查询，OutVo必须继承于TreeBase");
        }

        ArrayList<P> result = new ArrayList<>((Collection<? extends P>) toOutVo(list));
        L rootParentId = (L) getOutVoClass().getMethod("rootParentId").invoke(null);
        AgileReturn.add(Constant.ResponseAbout.RESULT, tree(result, rootParentId));
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
    default RETURN queryById(@AgileInParam("id") String id) throws Exception {
        O result;

        if (dataManager() != null) {
            result = toSingleOutVo(DictionaryUtil.findById(dataManager().dataSource(), id));
        } else if (detailSql() == null) {
            result = toSingleOutVo(queryById(getEntityClass(), id));
        } else {
            result = queryOne(getOutVoClass(), getEntityClass(), id, detailSql());
        }
        handingDetailVo(result);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    @NotAPI
    default String listSql() {
        return null;
    }

    @NotAPI
    default String detailSql() {
        return null;
    }

    @NotAPI
    default void handingListVo(O vo) throws Exception {
    }

    @NotAPI
    default void handingDetailVo(O vo) throws Exception {
    }


    static String parseOrder(BaseInParamVo inParam, String sql) {
        if (inParam == null) {
            return sql;
        }
        List<SortInfo> sortColumn = inParam.getSortColumn();
        if (sortColumn == null || sortColumn.isEmpty()) {
            return sql;
        }
        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(" order by ");
        for (int i = 0; i < sortColumn.size(); i++) {
            if (i > 0) {
                sqlBuilder.append(",");
            }
            SortInfo column = sortColumn.get(i);
            sqlBuilder.append(column.isSort() ? column.getProperty() : column.getProperty() + " DESC ");
        }
        return sqlBuilder.toString();
    }
}
