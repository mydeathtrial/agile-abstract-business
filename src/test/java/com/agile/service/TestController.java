package com.agile.service;

import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileReturn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 佟盟
 * 日期 2020/9/00004 13:40
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Controller
public class TestController {
    /**
     * 描述：
     * @author 佟盟
     * @date 2020/9/00004 13:40
    */
    @RequestMapping(path = "/test6")
    public Tudo test(String a) {
        AgileReturn.add("a",a);
        AgileReturn.setHead(RETURN.EXPRESSION);
        return new Tudo();
    }

    public class Tudo{

    }
}
