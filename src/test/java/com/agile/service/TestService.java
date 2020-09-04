package com.agile.service;


import cloud.agileframework.abstractbusiness.service.MainService;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileParam;
import org.springframework.stereotype.Service;

/**
 * @author 佟盟
 * 日期 2020/7/12 21:57
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Service
public class TestService extends MainService {
    /**
     * 描述：
     * @author 佟盟
     * @date 2020/7/12 21:57
    */
    @Mapping(path = "/testS")
    public RETURN test(Integer a,Integer b) {
        setOutParam(AgileParam.getInParam());
        return RETURN.SUCCESS;
    }

//    public static void main(String[] args) {
//        TestService.class.getAnnotations();
//    }
}
