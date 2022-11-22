package com.agile.service;

import cloud.agileframework.abstractbusiness.pojo.template.curd.ButtonType;
import cloud.agileframework.abstractbusiness.pojo.template.curd.Column;
import cloud.agileframework.abstractbusiness.pojo.template.curd.ColumnKey;
import cloud.agileframework.abstractbusiness.pojo.template.curd.Meta;
import cloud.agileframework.abstractbusiness.pojo.template.curd.SQLUtil;
import cloud.agileframework.abstractbusiness.pojo.template.curd.Table;
import cloud.agileframework.abstractbusiness.pojo.template.function.Function;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.CodeType;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElement;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormElementType;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormValidate;
import cloud.agileframework.abstractbusiness.pojo.template.view.visualization.ShowTableConfig;
import cloud.agileframework.abstractbusiness.service.TemplateService;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.common.util.string.StringUtil;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.spring.util.IdUtil;
import com.agile.entity.TableConfigDo;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class MysqlTemplateService implements TemplateService, InitializingBean {
    @Override
    public Function getFunction(String id) {
        return null;
    }

    @Override
    public Table getTable(String id) {

        String json = "{\n" +
                "    \"column\": {\n" +
                "        \"sys_user_id\": {\n" +
                "            \"name\": \"主键\",\n" +
                "            \"primary\": \"true\",\n" +
                "            \"query\": {\n" +
                "                \"order\": {\n" +
                "                    \"enable\": false,\n" +
                "                    \"edit\": true,\n" +
                "                    \"def\": \"NONE\",\n" +
                "                    \"sort\": 46\n" +
                "                },\n" +
                "                \"sort\": 9,\n" +
                "                \"defShow\": true,\n" +
                "                \"showType\": \"switch\"\n" +
                "            },\n" +
                "            \"code\": \"sys_user_id\",\n" +
                "            \"form\": {\n" +
                "                \"show\": true,\n" +
                "                \"filter\": true,\n" +
                "                \"sort\": 88,\n" +
                "                \"showType\": \"textarea\",\n" +
                "                \"update\": true,\n" +
                "                \"data\": {\n" +
                "                    \"dataType\": \"dic\",\n" +
                "                    \"parentId\": \"1\",\n" +
                "                    \"datasource\": null\n" +
                "                },\n" +
                "                \"insert\": true\n" +
                "            },\n" +
                "            \"enName\": \"per\",\n" +
                "            \"validate\": {\n" +
                "                \"min\": null,\n" +
                "                \"max\": null,\n" +
                "                \"minSize\": null,\n" +
                "                \"maxSize\": null,\n" +
                "                \"validateRegex\": \"\\\\d\",\n" +
                "                \"validateMsg\": \"错了吧\",\n" +
                "                \"nullable\": null,\n" +
                "                \"isBlank\": null,\n" +
                "                \"group\": null\n" +
                "            }\n" +
                "        },\n" +
                "        \"user_name\": {\n" +
                "            \"name\": \"姓名\",\n" +
                "            \"query\": {\n" +
                "                \"order\": {\n" +
                "                    \"enable\": false,\n" +
                "                    \"edit\": true,\n" +
                "                    \"def\": \"NONE\",\n" +
                "                    \"sort\": 46\n" +
                "                },\n" +
                "                \"sort\": 9,\n" +
                "                \"defShow\": true,\n" +
                "                \"showType\": \"switch\"\n" +
                "            },\n" +
                "            \"code\": \"sys_user_id\",\n" +
                "            \"form\": {\n" +
                "                \"show\": true,\n" +
                "                \"filter\": true,\n" +
                "                \"sort\": 88,\n" +
                "                \"showType\": \"textarea\",\n" +
                "                \"update\": true,\n" +
                "                \"data\": {\n" +
                "                    \"dataType\": \"dic\",\n" +
                "                    \"parentId\": \"1\",\n" +
                "                    \"datasource\": null\n" +
                "                },\n" +
                "                \"insert\": true\n" +
                "            },\n" +
                "            \"enName\": \"name\",\n" +
                "            \"validate\": {\n" +
                "                \"min\": null,\n" +
                "                \"max\": null,\n" +
                "                \"minSize\": null,\n" +
                "                \"maxSize\": null,\n" +
                "                \"validateRegex\": null,\n" +
                "                \"validateMsg\": null,\n" +
                "                \"nullable\": null,\n" +
                "                \"isBlank\": null,\n" +
                "                \"group\": null\n" +
                "            }\n" +
                "        },\n" +
                "        \"account\": {\n" +
                "            \"name\": \"账号\",\n" +
                "            \"query\": {\n" +
                "                \"order\": {\n" +
                "                    \"enable\": false,\n" +
                "                    \"edit\": true,\n" +
                "                    \"def\": \"NONE\",\n" +
                "                    \"sort\": 46\n" +
                "                },\n" +
                "                \"sort\": 9,\n" +
                "                \"defShow\": true,\n" +
                "                \"showType\": \"switch\"\n" +
                "            },\n" +
                "            \"code\": \"account\",\n" +
                "            \"form\": {\n" +
                "                \"show\": true,\n" +
                "                \"filter\": true,\n" +
                "                \"sort\": 88,\n" +
                "                \"showType\": \"textarea\",\n" +
                "                \"update\": true,\n" +
                "                \"data\": {\n" +
                "                    \"dataType\": \"dic\",\n" +
                "                    \"parentId\": \"1\",\n" +
                "                    \"datasource\": null\n" +
                "                },\n" +
                "                \"insert\": true\n" +
                "            },\n" +
                "            \"enName\": \"per\",\n" +
                "            \"validate\": {\n" +
                "                \"min\": null,\n" +
                "                \"max\": null,\n" +
                "                \"minSize\": null,\n" +
                "                \"maxSize\": null,\n" +
                "                \"validateRegex\": \"\\\\d+\",\n" +
                "                \"validateMsg\": \"错了吧\",\n" +
                "                \"nullable\": null,\n" +
                "                \"isBlank\": null,\n" +
                "                \"group\": null\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"button\": {\n" +
                "        \"common\": [\n" +
                "            \"download\",\n" +
                "            \"upload\",\n" +
                "            \"delete\",\n" +
                "            \"add\"\n" +
                "        ],\n" +
                "        \"row\": [\n" +
                "            \"rowupdate\",\n" +
                "            \"rowdetail\",\n" +
                "            \"detail\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"template\": \"page\",\n" +
                "    \"meta\": {\n" +
                "        \"insert\": \"INSERT INTO `sys_user` ( `sys_user_id`, `sys_dept_id`, `account`, `passcode`, `level`, `user_name`, `phone`, `email`, `expiration_time`, `create_user`, `create_time`, `update_user`, `update_time`, `del_flag`, `status`, `login_strategy`, `key_word`, `password_history`, `last_set_password` )VALUES ( #{sys_user_id}, #{sys_dept_id}, #{account}, #{passcode}, #{level}, #{user_name}, #{phone}, #{email}, #{expiration_time}, #{create_user}, #{create_time}, #{update_user}, #{update_time}, 0, #{status}, #{login_strategy}, #{key_word}, #{password_history}, #{last_set_password})\",\n" +
                "        \"update\": \"UPDATE `sys_user` SET `account` = #{account},`passcode` = #{passcode},`level` = #{level},`user_name` = #{user_name},`phone` = #{phone},`email` = #{email},`expiration_time` = #{expiration_time},`create_user` = #{create_user},`create_time` = #{create_time},`update_user` = #{update_user},`update_time` = #{update_time},`del_flag` = #{del_flag},`status` = #{status},`login_strategy` = #{login_strategy},`key_word` = #{key_word},`password_history` = #{password_history},`last_set_password` = #{last_set_password} WHERE`sys_user_id` = #{sys_user_id}\",\n" +
                "        \"delete\": \"UPDATE `sys_user` SET `sys_user`.del_flag = 1 where `sys_user_id` in #{id}\",\n" +
                "        \"page\": \"select * from `sys_user` where del_flag = 0\",\n" +
                "        \"detail\": \"select * from `sys_user` where del_flag = 0 and `sys_user_id` = #{id}\",\n" +
                "        \"tree\": null\n" +
                "    },\n" +
                "    \"id\": \"58\",\n" +
                "    \"url\": \"/api/v1/sysUser\",\n" +
                "    \"name\": \"账号\",\n" +
                "    \"enName\": \"account\",\n" +
                "    \"code\": \"sys_user\"\n" +
                "}\n";
        return ObjectUtil.to(json, new TypeReference<>(Table.class));
    }

    @Override
    public Table getTableByUrl(String url) {
        return getTable("1");
    }

    @Override
    public Map<String, String> generatorId(String id) {
        Map<String, String> map = Maps.newHashMap();
        map.put("sys_user_id", IdUtil.generatorIdToString());
        return map;
    }

    @Autowired
    private Dao dao;
    @Autowired
    private FileService fileService;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Map<String, Object>> columns = dao.findBySQL("SELECT\n" +
                "\tt.TABLE_NAME AS tableCode,\n" +
                "\tt.TABLE_COMMENT AS tableDesc,\n" +
                "\tc.COLUMN_NAME AS `code`,\n" +
                "\tc.data_type type,\n" +
                "\tc.CHARACTER_MAXIMUM_LENGTH AS length,\n" +
                "\tc.COLUMN_COMMENT `desc`,\n" +
                "\tc.COLUMN_DEFAULT def,\n" +
                "\tc.COLUMN_KEY keyType,\n" +
                "\tc.* \n" +
                "FROM\n" +
                "\tinformation_schema.`COLUMNS` c\n" +
                "\tLEFT JOIN information_schema.`TABLES` t ON t.TABLE_NAME = c.table_name \n" +
                "WHERE\n" +
                "\tt.table_schema = 'situation' \n" +
                "\tAND c.table_schema = 'situation' \n" +
                "\tAND t.TABLE_NAME NOT LIKE 'ACT_%' \n" +
                "\tAND t.TABLE_NAME NOT LIKE '%_test' \n");

        Map<String, List<Map<String, Object>>> map = columns.stream().collect(Collectors.groupingBy(a -> a.get("tableCode").toString()));

        int i = 0;
        for (Map.Entry<String, List<Map<String, Object>>> entry : map.entrySet()) {
            i++;
            try {
                Table table = new Table();
                String key = entry.getKey();
                table.setCode(key);
                table.setEnName(key);
                table.setName(entry.getValue().get(0).get("tableDesc").toString());
                table.setUrl("/api/v1/" + StringUtil.toLowerName(key));
                table.setTemplate("page");

                AtomicBoolean isTree = new AtomicBoolean(false);
                table.setColumn(entry.getValue().stream().map(a -> {
                    Column column = new Column();
                    String code = a.get("code").toString().replaceAll("\\s","");
                    column.setCode(code);
                    column.setName(a.get("desc").toString());
                    column.setEnName(code);
                    Object keyType = a.get("keyType");
                    if (keyType == null) {
                        column.setKey(ColumnKey.NONE);
                    } else {
                        switch (keyType.toString()) {
                            case "PRI":
                                column.setKey(ColumnKey.PRIMARY);
                                break;
                            case "UNI":
                                column.setKey(ColumnKey.UNIQUE);
                                break;
                            default:
                                column.setKey(ColumnKey.NONE);
                        }
                    }
                    if (code.equals("parent_id")) {
                        isTree.set(true);
                        column.setKey(ColumnKey.PARENT);
                    }
                    Object length = a.get("length");
                    String type = a.get("type").toString().toLowerCase(Locale.ROOT);
                    switch (type) {
                        case "varchar":
                        case "text":
                            column.setCodeType(CodeType.STRING);
                            break;
                        case "datetime":
                            column.setCodeType(CodeType.DATE);
                            break;
                        case "bigint":
                            column.setCodeType(CodeType.LONG);
                            break;
                        case "tinyint":
                            if(length!=null && Integer.parseInt(length.toString())==1){
                                column.setCodeType(CodeType.BOOLEAN);
                            }else {
                                column.setCodeType(CodeType.INTEGER);
                            }
                            break;
                        case "timestamp":
                            column.setCodeType(CodeType.TIMESTAMP);
                            break;
                    }
                    if (code.equals("del_flag")) {
                        column.setDef("0");
                    } else {
                        Object def = a.get("def");
                        column.setDef(def == null ? null : def.toString());
                    }


                    column.setForm(FormElement.builder()
                            .show(true)
                            .update(code.startsWith("update_") || code.equals("del_flag"))
                            .insert(!code.startsWith("update_"))
                            .show(true)
                            .showType(code.endsWith("time")?FormElementType.DATE:FormElementType.INPUT)
                            .filter(true)
                            .build());

                    column.setQuery(ShowTableConfig.QueryColumn.builder()
                            .type(FormElementType.TEXT)
                            .defShow(true)
                            .order(ShowTableConfig.QueryColumn.Order.builder()
                                    .edit(true)
                                    .enable(true)
                                    .build())
                            .build());

                   
                    column.setValidate(FormValidate.builder()
                            .groups(Lists.newArrayList(ButtonType.ADD, ButtonType.ROW_UPDATE))
                            .maxSize(length != null && column.getCodeType() == CodeType.STRING ? Integer.parseInt(length.toString()) : Integer.MAX_VALUE)
                            .max(length != null && (column.getCodeType() == CodeType.LONG || column.getCodeType() == CodeType.INTEGER) ? 10 ^ Integer.parseInt(length.toString()) : null)
                            .build());
                    return column;
                }).collect(Collectors.toMap(Column::getCode, c -> c)));

                Table.ButtonInfo buttonInfo = new Table.ButtonInfo();
                buttonInfo.setRow(Sets.newHashSet(ButtonType.ROW_DELETE, ButtonType.ROW_DETAIL, ButtonType.ROW_UPDATE));
                buttonInfo.setCommon(Sets.newHashSet(ButtonType.ADD, ButtonType.DELETE, ButtonType.UPLOAD, ButtonType.DOWNLOAD));
                table.setButton(buttonInfo);

                Meta meta = new Meta();
                meta.setInsert(SQLUtil.toInsert(table));
                meta.setDelete(SQLUtil.toDelete(table));
                meta.setUpdate(SQLUtil.toUpdate(table));
                meta.setPage(SQLUtil.toSelect(table));
                meta.setDetail(SQLUtil.toFindOne(table));

                if (isTree.get()) {
                    meta.setTree(SQLUtil.toSelect(table));
                }
                table.setMeta(meta);

                table.setId(i+"");

                fileService.extracted(table);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
