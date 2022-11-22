package cloud.agileframework.abstractbusiness.pojo.template.curd;

import cloud.agileframework.common.DataException;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
@Data
public class Table implements Serializable {
    //主键
    private String id;
    //中文业务名
    private String name;
    //英文业务名
    private String enName;
    //编码
    private String code;
    //约定的地址
    private String url;
    //约定的模板标识
    private String template;
    //字段信息
    private Map<String, Column> column;
    //按钮信息
    private ButtonInfo button;
    //元信息（sql相关内容）
    private Meta meta;

    @Data
    public static class ButtonInfo implements Serializable {
        //公共按钮
        private Set<ButtonType> common;
        //行按钮
        private Set<ButtonType> row;
    }
}
