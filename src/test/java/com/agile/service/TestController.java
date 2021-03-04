package com.agile.service;

import cloud.agileframework.abstractbusiness.controller.BaseController;
import cloud.agileframework.abstractbusiness.service.BaseService;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileReturn;
import com.agile.entity.InVo;
import com.agile.entity.OutVo;
import com.agile.entity.SysApiEntity;
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
@RequestMapping("/api/SysApiEntity")
public class TestController extends BaseController<SysApiEntity, InVo, OutVo>{

}
