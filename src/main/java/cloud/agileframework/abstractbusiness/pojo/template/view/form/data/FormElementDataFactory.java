package cloud.agileframework.abstractbusiness.pojo.template.view.form.data;

import cloud.agileframework.common.util.object.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.Map;

public class FormElementDataFactory {
    
    private static final Map<String,Class<? extends FormElementData>> cache = Maps.newHashMap();
    static {
        cache.put("dic",DicFormElementData.class);
        cache.put("sql",SqlFormElementData.class);
        cache.put("constant",ConstantFormElementData.class);
        cache.put("ref",RefFormElementData.class);
    }

    public static FormElementData create(JSONObject data){
        String dataType = data.getString("dataType");
        if(dataType==null){
            return null;
        }
        Class<? extends FormElementData> clazz = cache.get(dataType);
        return JSON.toJavaObject(data,clazz);
    }
}
