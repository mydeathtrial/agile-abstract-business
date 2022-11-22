package cloud.agileframework.abstractbusiness.pojo.template.curd;

import cloud.agileframework.common.DataException;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;

public class SQLUtil {
    public static String toInsert(Table table) {
        SQLInsertStatement insert = new SQLInsertStatement();
        insert.setTableSource(new SQLExprTableSource(table.getCode()));
        SQLInsertStatement.ValuesClause values = new SQLInsertStatement.ValuesClause();
        //item
        table.getColumn().values()
                .stream()
                .filter(Column::isInsert)
                .forEach(f -> {
                    insert.addColumn(SQLUtils.toSQLExpr(String.format("`%s`",f.getCode()), DbType.mysql));
                    if (ColumnKey.PRIMARY == f.getKey()) {
                        values.addValue(new SQLVariantRefExpr("#{id}"));
                    } else if (f.getDef() != null) {
                        values.addValue(new SQLVariantRefExpr(String.format("#{%s" + f.getDef() + "}", f.getCode())));
                    } else {
                        values.addValue(new SQLVariantRefExpr(String.format("#{%s}", f.getCode())));
                    }
                });
        insert.addValueCause(values);
        return insert.toString();
    }

    public static String toDelete(Table table) {
        SQLDeleteStatement delete = new SQLDeleteStatement();
        delete.setTableSource(new SQLExprTableSource(table.getCode()));

        Column primaryKey = table.getColumn().values().stream().filter(c -> ColumnKey.PRIMARY == c.getKey()).findFirst().orElse(null);
        if (primaryKey == null) {
            throw new DataException("必须存在主键");
        }
        delete.addWhere(new SQLBinaryOpExpr(SQLUtils.toSQLExpr(primaryKey.getCode()), SQLBinaryOperator.BooleanAnd, new SQLVariantRefExpr("#{id}")));
        return delete.toString();
    }

    public static String toUpdate(Table table) {
        SQLUpdateStatement update = new SQLUpdateStatement();
        //from
        update.setTableSource(new SQLExprTableSource(table.getCode()));

        //where
        table.getColumn().values().stream().filter(c -> ColumnKey.PRIMARY == c.getKey())
                .forEach(e -> update.addWhere(new SQLBinaryOpExpr(SQLUtils.toSQLExpr(String.format("`%s`",e.getCode()), DbType.mysql), SQLBinaryOperator.BooleanAnd, new SQLVariantRefExpr("#{id}"))));

        //item
        table.getColumn().values().stream().filter(e -> ColumnKey.PRIMARY != e.getKey() && e.isUpdate())
                .forEach(f -> {
                    SQLUpdateSetItem updateSetItem = new SQLUpdateSetItem();
                    updateSetItem.setColumn(SQLUtils.toSQLExpr(String.format("`%s`",f.getCode()), DbType.mysql));

                    if (f.getDef() != null) {
                        updateSetItem.setValue(new SQLVariantRefExpr(String.format("#{%s" + f.getDef() + "}", f.getCode())));
                    } else {
                        updateSetItem.setValue(new SQLVariantRefExpr(String.format("#{%s}", f.getCode())));
                    }
                    update.addItem(updateSetItem);
                });
        return update.toString();
    }

    public static String toSelect(Table table) {
        SQLSelectQueryBlock query = new SQLSelectQueryBlock();
        query.setFrom(new SQLExprTableSource(table.getCode()));

        table.getColumn().values().forEach(e -> {
            SQLSelectItem item = new SQLSelectItem(SQLUtils.toSQLExpr(String.format("`%s`",e.getCode())));
            if (ColumnKey.PRIMARY == e.getKey()) {
                item.setAlias("id");
            } else if (ColumnKey.PARENT == e.getKey()) {
                item.setAlias("parent_id");
            }

            query.addSelectItem(item);
        });
        table.getColumn().values().stream().filter(e -> e.getForm().isFilter())
                .forEach(e -> query.addWhere(new SQLBinaryOpExpr(SQLUtils.toSQLExpr(String.format("`%s`",e.getCode())), SQLBinaryOperator.Equality, new SQLVariantRefExpr(String.format("#{%s}", e.getCode())))));

        query.addOrderBy(new SQLOrderBy(new SQLIdentifierExpr("id")));
        return query.toString();
    }

    public static String toFindOne(Table table) {
        SQLSelectQueryBlock query = new SQLSelectQueryBlock();
        query.setFrom(new SQLExprTableSource(table.getCode()));

        table.getColumn().values().forEach(e -> {
            SQLSelectItem item = new SQLSelectItem(SQLUtils.toSQLExpr(String.format("`%s`",e.getCode())));
            if (ColumnKey.PRIMARY == e.getKey()) {
                item.setAlias("id");
            }
            query.addSelectItem(item);
        });
        Column primaryKey = table.getColumn().values().stream().filter(c -> ColumnKey.PRIMARY == c.getKey()).findFirst().orElse(null);
        if (primaryKey == null) {
            throw new DataException("必须存在主键");
        }
        query.setWhere(new SQLBinaryOpExpr(SQLUtils.toSQLExpr(String.format("`%s`",primaryKey.getCode())), SQLBinaryOperator.BooleanAnd, new SQLVariantRefExpr("#{id}")));
        return query.toString();
    }
}
