package cloud.agileframework.abstractbusiness.controller;

import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.service.BaseService;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.NoSuchRequestServiceException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.validate.ValidateCustomBusiness;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;
import cloud.agileframework.validate.annotation.Validate;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Update;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-02-20 17:15
 * 描述 公共基础业务控制器
 * @version 1.0
 * @since 1.0
 */
@AgileService
@Mapping("${agile.base-service.rootPath:${agile.module-name:api}/{model}/default}")
public class BaseBusinessService {

    /**
     * 模型传参
     */
    public static final String MODEL = "model";

    @Autowired
    private BaseService baseService;

    /**
     * 新增接口
     *
     * @param model 要操作的实体名
     * @return 操作结果
     */
    @Validate(customBusiness = InsertValidateDo.class)
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.save:}"}, method = RequestMethod.POST)
    public RETURN save(@AgileInParam("model") String model) {
        baseService.save(model);
        return RETURN.SUCCESS;
    }

    /**
     * 根据主键删除
     *
     * @param model 模型
     * @param id    主键
     */
    @Validate(value = "id", nullable = false)
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.deleteById:/{id}}"}, method = RequestMethod.DELETE)
    public RETURN delete(@AgileInParam(MODEL) String model, @AgileInParam("id") String id) {
        baseService.delete(model, id);
        return RETURN.SUCCESS;
    }

    /**
     * 根据主键集删除
     *
     * @param model 模型
     * @param ids   逐渐集合
     */
    @Validate(nullable = false)
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.deleteByIds:}"}, method = RequestMethod.DELETE)
    public RETURN delete(@AgileInParam(MODEL) String model, @AgileInParam List<String> ids) {
        baseService.delete(ids, model);
        return RETURN.SUCCESS;
    }

    /**
     * 清空表
     *
     * @param model 模型
     */
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.clean:/all}"}, method = RequestMethod.DELETE)
    public RETURN clean(@AgileInParam(MODEL) String model) {
        baseService.clean(model);
        return RETURN.SUCCESS;
    }

    /**
     * 更新
     *
     * @param model 模型
     */
    @Validate(customBusiness = UpdateValidateDo.class)
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.update:}"}, method = RequestMethod.PUT)
    public RETURN update(@AgileInParam(MODEL) String model) {
        baseService.update(model);
        return RETURN.SUCCESS;
    }

    /**
     * 查询
     *
     * @param model   模型
     * @param inParam 入参
     * @return 查询结果
     */
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.query:/list}"}, method = RequestMethod.POST)
    public RETURN list(@AgileInParam(MODEL) String model, @AgileInParam BaseInParamVo inParam) {
        AgileReturn.add(Constant.ResponseAbout.RESULT, baseService.list(model, inParam));
        return RETURN.SUCCESS;
    }

    /**
     * 分页
     *
     * @param model   模型
     * @param inParam 入参
     * @return 分页
     */
    @Validate(value = "pageNum", nullable = false)
    @Validate(value = "pageSize", nullable = false)
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.page:/{pageNum}/{pageSize}}"}, method = RequestMethod.POST)
    public RETURN page(@AgileInParam(MODEL) String model, @AgileInParam BaseInParamVo inParam) {
        AgileReturn.add(Constant.ResponseAbout.RESULT, baseService.page(model, inParam));
        return RETURN.SUCCESS;
    }

    /**
     * 查询树形结构
     *
     * @param model 模型
     * @return 树形结果
     */
    @SneakyThrows
    @Mapping("${agile.base-service.tree:/tree}")
    public RETURN tree(@AgileInParam(MODEL) String model) {
        AgileReturn.add(Constant.ResponseAbout.RESULT, baseService.tree(model));
        return RETURN.SUCCESS;
    }

    /**
     * 根据主键查询
     *
     * @param model 模型
     * @param id    主键
     * @return 数据
     * @throws NoSuchRequestServiceException 异常
     */
    @Validate(value = "id", nullable = false)
    @Mapping(value = {"${agile.base-service.queryById:/{id}}"}, method = RequestMethod.GET)
    public Object queryById(@AgileInParam(MODEL) String model, Object id) throws NoSuchRequestServiceException {
        AgileReturn.add(Constant.ResponseAbout.RESULT, baseService.queryById(model, id));
        return RETURN.SUCCESS;
    }

    /**
     * 验证录入
     */
    public static class InsertValidateDo implements ValidateCustomBusiness {

        @Override
        public List<ValidateMsg> validate(Object params) {
            String model = AgileParam.getInParam(MODEL, String.class);
            try {
                return BaseService.dataAsParam(model, data -> {
                    if (data instanceof Class) {
                        return null;
                    }
                    return ValidateUtil.validate(data, Default.class,Insert.class);
                });
            } catch (NoSuchRequestServiceException e) {
                return new ArrayList<>(0);
            }
        }
    }

    /**
     * 验证录入
     */
    public static class UpdateValidateDo implements ValidateCustomBusiness {

        @Override
        public List<ValidateMsg> validate(Object params) {
            String model = AgileParam.getInParam(MODEL, String.class);
            try {
                return BaseService.dataAsParam(model, data -> {
                    if (data instanceof Class) {
                        return null;
                    }
                    return ValidateUtil.validate(data, Default.class,Update.class);
                });
            } catch (NoSuchRequestServiceException e) {
                return new ArrayList<>(0);
            }
        }
    }
}
