package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.EntityExistsException;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.dictionary.DictionaryDataBase;
import cloud.agileframework.dictionary.util.DictionaryUtil;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.PageQuery;
import cloud.agileframework.validate.group.Query;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
        List<?> result = list(inParam);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default List<?> list(I inParam) throws Exception {
        List<O> list = list(inParam, getEntityClass(), getOutVoClass());
        for (O vo : list) {
            handingListVo(vo);
        }
        return list;
    }

    default <J, T> List<T> list(I inParam, Class<J> queryBy, Class<T> outBy) throws Exception {
        if (inParam != null) {
            inParam.validate(Query.class);
        }
        String sql = parseOrder(inParam, listSql());
        List<T> list;
        if (sql != null) {
            list = genericService().list(queryBy, outBy, inParam, sql);
        } else {
            list = genericService().list(queryBy, outBy, inParam);
        }

        return list;
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
        Page<?> result = page(inParam);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default Page<?> page(I inParam) throws Exception {
        Page<O> page = page(inParam, getEntityClass(), getOutVoClass());
        for (O vo : page.getContent()) {
            handingListVo(vo);
        }
        return page;
    }

    default <J, T> Page<T> page(I inParam, Class<J> queryBy, Class<T> outBy) throws Exception {
        if (inParam == null) throw new AgileArgumentException(
                Lists.newArrayList(
                        ValidateMsg.builder().item("pageNum").message("必填").build(),
                        ValidateMsg.builder().item("pageSize").message("必填").build()));
        inParam.validate(PageQuery.class);
        String sql = parseOrder(inParam, listSql());
        Page<T> page;
        if (sql != null) {
            page = genericService().page(queryBy, outBy, inParam, sql);
        } else {
            page = genericService().page(queryBy, outBy, inParam);
        }
        return page;
    }

    /**
     * 树
     *
     * @return 树
     */
    @Mapping(value = "${agile.base-service.tree:/tree}", method = {RequestMethod.GET, RequestMethod.POST})
    default RETURN tree() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        SortedSet<?> result = tree(inParam);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default SortedSet<?> tree(I inParam) throws Exception {
        return tree(inParam, getEntityClass(), getOutVoClass());
    }

    default <L extends Serializable, P extends TreeBase<L, P>, J, T> SortedSet<T> tree(I inParam, Class<J> queryBy, Class<T> outBy) throws Exception {
        if (!TreeBase.class.isAssignableFrom(queryBy)) {
            throw new NoSuchRequestServiceException();
        }
        if (inParam != null) {
            inParam.validate(Query.class);
        }

        List<T> list = genericService().list(queryBy, outBy, inParam);

        if (!TreeBase.class.isAssignableFrom(outBy)) {
            throw new AgileArgumentException("如果是树形结构查询，OutVo必须继承于TreeBase");
        }
        if (getOutVoClass().isAssignableFrom(outBy)) {
            for (T t : list) {
                handingListVo((O) t);
            }
        }
        ArrayList<P> result = new ArrayList<>((Collection<? extends P>) list);
        L rootParentId = (L) outBy.getMethod("rootParentId").invoke(null);
        return new TreeSet<>((Collection<? extends T>) (genericService().tree(result, rootParentId)));
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
        Object result = queryOne(id);
        AgileReturn.add(Constant.ResponseAbout.RESULT, result);
        return RETURN.SUCCESS;
    }

    default Object queryOne(String id) throws Exception {
        O data = queryOne(id, getEntityClass(), getOutVoClass());
        handingDetailVo(data);
        return data;
    }

    /**
     * 因为该方法制定了具体的数据返回类型，因此该方法不会调用handingDetailVo方法
     *
     * @param id      主键
     * @param queryBy 查询结果用什么java类型接
     * @param outBy   数据返回数据用什么类型
     * @param <T>     泛型
     * @return 数据
     * @throws Exception 异常
     */
    default <T, J> T queryOne(String id, Class<J> queryBy, Class<T> outBy) throws Exception {
        T result;

        if (dataManager() != null) {
            DictionaryDataBase dic = DictionaryUtil.findById(dataManager().dataSource(), id);
            result = GenericService.to(GenericService.to(dic, queryBy), outBy);
        } else if (detailSql() == null) {
            result = GenericService.to(dao().findOne(queryBy, id), outBy);
        } else {
            result = GenericService.to(genericService().queryOne(queryBy, id, detailSql(), "id"), outBy);
        }
        if (result == null) {
            throw new EntityExistsException(id);
        }
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
