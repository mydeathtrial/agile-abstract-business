package cloud.agileframework.abstractbusiness.pojo.template.view.form.data;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementDataType;
import cloud.agileframework.dictionary.DictionaryDataBase;
import cloud.agileframework.dictionary.annotation.DirectionType;
import cloud.agileframework.dictionary.util.ConvertConf;
import cloud.agileframework.dictionary.util.DictionaryUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.compress.utils.Lists;

@Data
public class DicFormElementData implements FormElementData {
    private String parentId;
    private String dataSource;
    
    @Override
    public FormElementDataType type() {
        return FormElementDataType.DIC;
    }

    @Override
    public Object data() {
        DictionaryDataBase parentDic = DictionaryUtil.findById(dataSource, parentId);
        if(parentDic==null){
            return Lists.newArrayList();
        }
        return parentDic.getChildren();
    }
}
