package com.agile.service;

import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.spring.exception.CreateFileException;

/**
 * @author 佟盟
 * 日期 2020/9/00004 12:04
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@AgileService
public class Test2Service {
    /**
     * 描述：
     * @author 佟盟
     * @date 2020/9/00004 12:04
    */
    @Mapping(path = "/test1")
    public RETURN test(String a) {

        return RETURN.EXPRESSION;
    }

    @Mapping(path = "/test2")
    public RETURN test2(String a) {

       throw new RuntimeException();
    }

    @Mapping(path = "/test3")
    public RETURN test3(String a) throws CreateFileException {

        throw new CreateFileException(a);
    }

    @Mapping(path = "/test4")
    public RETURN test4(String a) throws CreateFileException {

        return RETURN.byMessage("asd","com.alibaba.druid.sql.parser.ParserException");
    }
}
