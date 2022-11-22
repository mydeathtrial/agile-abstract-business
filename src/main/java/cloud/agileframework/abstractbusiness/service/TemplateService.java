package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.template.curd.Meta;
import cloud.agileframework.abstractbusiness.pojo.template.curd.SQLUtil;
import cloud.agileframework.abstractbusiness.pojo.template.curd.Table;
import cloud.agileframework.abstractbusiness.pojo.template.function.Function;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface TemplateService {
    Function getFunction(String id);

    Table getTable(String id);

    Table getTableByUrl(String url);

    Map<String, String> generatorId(String id);

    @Mapping(value = "/curd", method = RequestMethod.POST)
    default void createCurd(@AgileInParam Table table) {
        Meta meta = table.getMeta();
        if (meta == null) {
            meta = new Meta();
        }
        if (meta.getInsert() == null) {
            meta.setInsert(SQLUtil.toInsert(table));
        }
        if (meta.getDelete() == null) {
            meta.setDelete(SQLUtil.toDelete(table));
        }
        if (meta.getUpdate() == null) {
            meta.setUpdate(SQLUtil.toUpdate(table));
        }
        if (meta.getPage() == null) {
            meta.setPage(SQLUtil.toSelect(table));
        }
        if (meta.getDetail() == null) {
            meta.setDetail(SQLUtil.toFindOne(table));
        }
    }
}
