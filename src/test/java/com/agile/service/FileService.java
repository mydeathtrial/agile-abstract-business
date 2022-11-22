package com.agile.service;

import cloud.agileframework.abstractbusiness.pojo.template.curd.Table;
import cloud.agileframework.common.util.file.ResponseFile;
import cloud.agileframework.common.util.file.poi.CellInfo;
import cloud.agileframework.common.util.file.poi.ExcelFile;
import cloud.agileframework.common.util.file.poi.POIUtil;
import cloud.agileframework.common.util.file.poi.SheetData;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.param.AgileParam;
import com.agile.entity.TableConfigDo;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@AgileService
public class FileService {

    @Autowired
    private Dao dao;
    @Transactional
    public void extracted(Table table) {
        dao.save(TableConfigDo.builder()
                .id(table.getId())
                .tableName(table.getCode())
                .config(JSON.toJSONString(table))
                .build());
    }

    @Mapping("/test0000/{id}")
    public void test0(MultipartFile file){
       throw new RuntimeException("111");
    }
    
    @Mapping("/test1111")
    public void test(MultipartFile file){
        System.out.println(file);
    }

    @Mapping("/test2222")
    public void test2(List<MultipartFile> file){
        System.out.println(file);
    }

    @Mapping("/test3333")
    public void test2(){
        System.out.println(AgileParam.getInParamOfFile("file"));
    }
    @Mapping("/test4444")
    public void test2(@RequestParam("file") MultipartFile file){
        System.out.println(file);
    }

    @Mapping("/test5555")
    public void test5(@AgileInParam("file") MultipartFile file2){
        System.out.println(file2);
    }

    @Mapping("/test6666")
    public ResponseFile test5(HttpServletRequest request, @RequestBody String text) throws IOException {
        return new ResponseFile(new File("E:\\workspace-agile\\agile-parent3\\agile-abstract-business\\src\\test\\resources\\application.yml"));
    }

    @Mapping("/test7777")
    public ResponseFile test7(HttpServletRequest request, @RequestBody String text) throws IOException {
        return new ExcelFile("test",POIUtil.creatExcel(POIUtil.VERSION.V2007, SheetData.builder().setName("1111").setCells(Lists.newArrayList(CellInfo.builder().name("2222").build())).build()));
    }
}
