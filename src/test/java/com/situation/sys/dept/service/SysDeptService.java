package com.situation.sys.dept.service;

import cloud.agileframework.abstractbusiness.service.AllBusinessService;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import com.situation.sys.dept.pojo.db.SysDeptDo;
import com.situation.sys.dept.pojo.vo.SysDeptInVo;
import com.situation.sys.dept.pojo.vo.SysDeptOutVo;

/**
 * 描述：组织机构表控制器
 *
 * @author agile generator
 */


@AgileService
@Mapping("/api/sys/sysDept")
public class SysDeptService implements AllBusinessService<SysDeptDo, SysDeptInVo, SysDeptOutVo> {

}
