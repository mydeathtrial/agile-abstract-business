package cloud.agileframework.abstractbusiness.pojo.vo;

import cloud.agileframework.common.annotation.Alias;
import cloud.agileframework.common.util.collection.SortInfo;
import cloud.agileframework.validate.group.PageQuery;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLOrderingSpecification;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.parser.ParserException;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 佟盟
 * 日期 2021-01-26 11:43
 * 描述 入参基类
 * @version 1.0
 * @since 1.0
 */
@Data
@ToString
public class BaseInParamVo implements Serializable {
    /**
     * 排序字段
     * 表名称
     */
    private List<SortInfo> sortColumn;

    /**
     * 页数
     */
    @Alias("page")
    @NotNull(groups = PageQuery.class)
    private Integer pageNum;

    /**
     * 数量
     */
    @NotNull(groups = PageQuery.class)
    private Integer pageSize;

    /**
     * 处理排序
     *
     * @param sql SQL
     * @return 处理sql排序
     */
    public String parseOrder(String sql) {
        List<SortInfo> sortInfos = getSortColumn();
        if (sortInfos == null || sortInfos.isEmpty()) {
            return sql;
        }
        sql = sql.replaceAll("\\s*;\\s*$", " ");

        SQLExpr expr = SQLUtils.toSQLExpr(sql, DbType.mysql);
        if (expr instanceof SQLQueryExpr) {
            SQLOrderBy orderBy = ((SQLQueryExpr) expr).getSubQuery().getQueryBlock().getOrderBy();
            sortInfos.forEach(sortInfo -> {
                SQLSelectOrderByItem order = new SQLSelectOrderByItem(SQLUtils.toSQLExpr(sortInfo.getProperty()));
                if (!(order.getExpr() instanceof SQLPropertyExpr) && !(order.getExpr() instanceof SQLIdentifierExpr)) {
                    throw new ParserException();
                }
                order.setType(sortInfo.isSort() ? SQLOrderingSpecification.ASC : SQLOrderingSpecification.DESC);
                orderBy.addItem(order);
            });
        }
        return expr.toString();
    }
}
