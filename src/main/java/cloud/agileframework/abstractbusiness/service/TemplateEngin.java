package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.template.Function;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElement;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.param.AgileParam;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@AgileService("/api/template")
public class TemplateEngin {
    private TemplateService templateService;

    @Mapping(value = "/{apiId}",method = RequestMethod.GET)
    public Function getApi(String apiId) {
        return templateService.get(apiId);
    }

    @Mapping(value = "/resource/{apiId}")
    public Object parse(String apiId){
//        Function api = templateService.get(apiId);
//        ApiRequest request = api.getRequest();
//        Map<String, FormElement> params = request.getParam();
//        for (Map.Entry<String,FormElement> param:params.entrySet()){
//            FormElement formElement = param.getValue();
//            formElement.getType()
//            AgileParam.getInParam(param.getKey());
//        }
        return null;
    }
}
