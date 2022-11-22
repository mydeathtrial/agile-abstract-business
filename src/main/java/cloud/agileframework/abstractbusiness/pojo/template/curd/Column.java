package cloud.agileframework.abstractbusiness.pojo.template.curd;

import cloud.agileframework.abstractbusiness.pojo.template.view.form.CodeType;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElement;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormValidate;
import cloud.agileframework.abstractbusiness.pojo.template.view.visualization.ShowTableConfig;
import cloud.agileframework.common.util.file.poi.CellInfo;
import lombok.Data;

import java.io.Serializable;
@Data
public class Column implements Serializable {
    //显示名
    private String name;
    //英文名
    private String enName;
    //编码
    private String code;
    //作为表单项配置
    private FormElement form;
    //作为分页结果，展示方式
    private ShowTableConfig.QueryColumn query;
    //校验方式
    private FormValidate validate;
    //程序计算类型
    private CodeType codeType = CodeType.STRING;
    //默认值
    private String def;
    
    private ColumnKey key;
    
    private boolean update = true;
    
    private boolean insert = true;

    public CellInfo to(){
        return CellInfo.builder()
                .key(code)
                .name(name)
                .sort(query.getSort())
                .type(codeType.getReallyClass())
                .build();
    }
}
