package cloud.agileframework.abstractbusiness.pojo.vo;

import cloud.agileframework.common.annotation.Alias;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.collection.SortInfo;
import cloud.agileframework.validate.group.PageQuery;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObjectImpl;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLOrderingSpecification;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumberExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.parser.ParserException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 佟盟
 * 日期 2021-01-26 11:43
 * 描述 入参基类
 * @version 1.0
 * @since 1.0
 */
@Data
@ToString
public class BaseInParamVo implements IBaseInParamVo {
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
            SQLSelect subQuery = ((SQLQueryExpr) expr).getSubQuery();
            SQLSelectQueryBlock queryBlock = subQuery.getQueryBlock();

            List<SQLSelectItem> select = queryBlock.getSelectList();
            Map<String, SQLSelectItem> aliasMap = Maps.newHashMap();
            Map<String, SQLSelectItem> nameMap = Maps.newHashMap();
            Map<String, SQLSelectItem> ownerMap = Maps.newHashMap();
            select.forEach(a -> {

                SQLExpr propertyExpr = a.getExpr();
                String alias = a.getAlias();
                if (alias != null) {
                    aliasMap.put(alias, a);
                }
                if (propertyExpr instanceof SQLPropertyExpr) {

                    String name = ((SQLPropertyExpr) propertyExpr).getName();
                    String ownerName = ((SQLPropertyExpr) propertyExpr).getOwnerName();

                    //解析有效名
                    if (alias != null) {
                        nameMap.put(name, a);
                    }
                    if (ownerName != null) {
                        nameMap.put(name, a);
                        ownerMap.put(ownerName + Constant.RegularAbout.SPOT + name, a);
                    } else {
                        nameMap.put(name, a);
                    }
                }

            });

            SQLOrderBy orderBy = queryBlock.getOrderBy();
            if (orderBy == null) {
                orderBy = new SQLOrderBy();
                queryBlock.setOrderBy(orderBy);
            }

            List<SQLSelectOrderByItem> items = orderBy.getItems();
            Collections.reverse(items);
            List<SQLSelectOrderByItem> cache = Lists.newArrayList(items);

            Set<String> currentItems = items.stream().map(SQLObjectImpl::toString).collect(Collectors.toSet());
            
            for (int i =  0; i <sortInfos.size(); i++) {
                SortInfo sortInfo = sortInfos.get(i);
                String orderColumn = sortInfo.getProperty();
                SQLSelectItem selectItem = aliasMap.get(orderColumn);
                if (selectItem == null) {
                    selectItem = ownerMap.get(orderColumn);
                }
                if (selectItem == null) {
                    selectItem = nameMap.get(orderColumn);
                }

                String selectItemName = selectItem == null ? orderColumn : selectItem.getExpr().toString();
              
                String agileOrderColumnAlias = "agile_order_" + i;
                if(currentItems.contains(agileOrderColumnAlias)){
                    continue;
                }
                SQLSelectOrderByItem order = new SQLSelectOrderByItem(SQLUtils.toSQLExpr(agileOrderColumnAlias));
                if (!(order.getExpr() instanceof SQLPropertyExpr) && !(order.getExpr() instanceof SQLIdentifierExpr)) {
                    throw new ParserException();
                }
                order.setType(sortInfo.isSort() ? SQLOrderingSpecification.ASC : SQLOrderingSpecification.DESC);

                if (!aliasMap.containsKey(agileOrderColumnAlias)) {
                    SQLMethodInvokeExpr ifNull = new SQLMethodInvokeExpr();
                    ifNull.setMethodName("ifNull");
                    ifNull.addArgument(SQLUtils.toSQLExpr(selectItemName));
                    ifNull.addArgument(new SQLNumberExpr(sortInfo.isSort() ? 0 : 1));
                    select.add(new SQLSelectItem(ifNull, agileOrderColumnAlias));
                    cache.add(order);
                }
            }
            Collections.reverse(cache);
            items.clear();
            items.addAll(cache);
            return subQuery.toString();
        }
        return expr.toString();
    }

    public Sort sort() {
        if (getSortColumn() == null || getSortColumn().isEmpty()) {
            return Sort.unsorted();
        }
        List<SortInfo> sorts = getSortColumn();

        List<Sort.Order> s = sorts.stream().map(sortInfo -> {
            if (sortInfo.isSort()) {
                return Sort.Order.desc(sortInfo.getProperty());
            }
            return Sort.Order.asc(sortInfo.getProperty());
        }).collect(Collectors.toList());
        return Sort.by(s);
    }
}
